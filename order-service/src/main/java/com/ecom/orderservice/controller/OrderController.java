package com.ecom.orderservice.controller;

import com.ecom.orderservice.dto.OrderRequest;
import com.ecom.orderservice.dto.OrderResponse;
import com.ecom.orderservice.service.Orderservice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final Orderservice orderService;


    @PostMapping
    public ResponseEntity<Integer> createOrder(
            @RequestBody @Valid OrderRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        return ResponseEntity.ok(orderService.createOrder(request, authHeader));
    }


    @GetMapping
    public ResponseEntity<List<OrderResponse>>findAll()
    {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse>findById(@PathVariable ("orderId") Integer orderId)
    {
        return ResponseEntity.ok(orderService.findById(orderId));
    }

}
