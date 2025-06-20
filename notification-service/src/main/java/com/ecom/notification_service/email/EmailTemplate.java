package com.ecom.notification_service.email;

import lombok.Getter;

public enum EmailTemplate {

    PAYMENT_CONFIRMATION("payment-confirmation", "Payment successfully processed"),
    ORDER_CONFIRMATION("order-confirmation", "Order confirmation");

    @Getter
    private final String template;
    @Getter
    private final String subject;

    EmailTemplate(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
