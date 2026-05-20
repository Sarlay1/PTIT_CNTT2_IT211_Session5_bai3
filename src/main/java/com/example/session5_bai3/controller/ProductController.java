package com.example.session5_bai3.controller;

import com.example.session5_bai3.entity.Product;
import com.example.session5_bai3.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Product> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        Product saved = repository.save(product);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateFull(
            @PathVariable Long id,
            @Valid @RequestBody Product product) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        product.setId(id);

        Product updated = repository.save(product);

        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updatePartial(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        Product product = repository.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        if (updates.containsKey("name")) {
            String name = updates.get("name").toString();

            if (name.isBlank()) {
                return ResponseEntity.badRequest().build();
            }

            product.setName(name);
        }

        if (updates.containsKey("price")) {
            double price = Double.parseDouble(updates.get("price").toString());

            if (price <= 0) {
                return ResponseEntity.badRequest().build();
            }

            product.setPrice(price);
        }

        Product updated = repository.save(product);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}