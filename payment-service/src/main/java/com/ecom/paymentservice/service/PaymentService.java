package com.ecom.paymentservice.service;

import com.ecom.paymentservice.dto.PaymentRequest;
import com.ecom.paymentservice.mapper.PaymentMapper;
import com.ecom.paymentservice.notification.NotificationProducer;
import com.ecom.paymentservice.notification.PaymentNotificationRequest;
import com.ecom.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;


    public Integer createPayment(PaymentRequest request) {
        var payment = paymentRepository.save(mapper.toPayment(request));

        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstName(),
                        request.customer().lastName(),
                        request.customer().email()
                )
        );

        return payment.getId();

    }

}
