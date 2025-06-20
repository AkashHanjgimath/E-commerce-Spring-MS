package com.ecom.orderservice.payment;

import com.ecom.orderservice.customer.CustomerResponse;
import com.ecom.orderservice.model.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
