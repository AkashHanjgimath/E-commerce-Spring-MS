package com.ecom.productservice.controller;

import com.ecom.productservice.dto.ProductPurchaseRequest;
import com.ecom.productservice.dto.ProductPurchaseResponse;
import com.ecom.productservice.dto.ProductRequest;
import com.ecom.productservice.dto.ProductResponse;
import com.ecom.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequest request)
    {
        return ResponseEntity.ok(service.createProduct(request));

    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>>purchaseProducts(@RequestBody List<ProductPurchaseRequest> request)
    {
        return ResponseEntity.ok(service.purchaseProduct(request));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse>findById(@PathVariable("productId") Integer productId)
    {
        return ResponseEntity.ok(service.findById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>>findAll()
    {
        return ResponseEntity.ok(service.findAll());
    }

}
