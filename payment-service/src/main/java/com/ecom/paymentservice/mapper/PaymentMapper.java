package com.ecom.paymentservice.mapper;

import com.ecom.paymentservice.dto.PaymentRequest;
import com.ecom.paymentservice.model.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {

    public Payment toPayment(PaymentRequest request)
    {
        if(request==null)
        {
            return null;
        }
        return Payment.builder()
                .id(request.id())
                .paymentMethod(request.paymentMethod())
                .amount(request.amount())
                .orderId(request.orderId())
                .build();
    }
}
