package com.ecom.customer.controller;

import com.ecom.customer.dto.CustomerRequest;
import com.ecom.customer.dto.CustomerResponse;
import com.ecom.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping
    public ResponseEntity<String>createCustomer(@RequestBody @Valid CustomerRequest request )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(request));
    }

    @PutMapping
    public ResponseEntity<?>updateCustomer(@RequestBody @Valid CustomerRequest request)
    {
        customerService.update(request);
        return ResponseEntity.accepted().build();

    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>>findAll(){
        return ResponseEntity.ok(customerService.findAllCustomers());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse>findById(@PathVariable("customerId")String customerID)
    {
        return ResponseEntity.ok(customerService.findById(customerID));
    }

    @GetMapping("/exists/{customerId}")
    public ResponseEntity<Boolean> customerExists(@PathVariable ("customerId")String customerId)
    {
        return ResponseEntity.ok(customerService.existById(customerId));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<?>deleteCustomer(@PathVariable("customerId")String customerId)
    {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.accepted().build();
    }

}
