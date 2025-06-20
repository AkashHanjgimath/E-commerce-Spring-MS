package com.ecom.orderservice.kafka;

import com.ecom.orderservice.customer.CustomerResponse;
import com.ecom.orderservice.model.PaymentMethod;
import com.ecom.orderservice.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
