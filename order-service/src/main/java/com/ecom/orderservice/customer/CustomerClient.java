package com.ecom.orderservice.customer;

import com.ecom.orderservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "customer-service",
        url = "${application.config.customer-url}",
        configuration = FeignClientConfig.class
)
public interface CustomerClient {

    @GetMapping("/{customerId}")
    Optional<CustomerResponse> findCustomerById(@PathVariable("customerId") String customerId);
}

