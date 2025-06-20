package com.ecom.orderservice.service;

import com.ecom.orderservice.customer.CustomerClient;
import com.ecom.orderservice.dto.OrderRequest;
import com.ecom.orderservice.dto.OrderResponse;
import com.ecom.orderservice.dto.PurchaseRequest;
import com.ecom.orderservice.exception.BusinessException;
import com.ecom.orderservice.kafka.OrderConfirmation;
import com.ecom.orderservice.kafka.OrderProducer;
import com.ecom.orderservice.mapper.OrderMapper;
import com.ecom.orderservice.orderLine.OrderLineRequest;
import com.ecom.orderservice.orderLine.OrderLineService;
import com.ecom.orderservice.payment.PaymentClient;
import com.ecom.orderservice.payment.PaymentRequest;
import com.ecom.orderservice.product.ProductClient;
import com.ecom.orderservice.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Orderservice {

    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;


    @Transactional
    public Integer createOrder(OrderRequest request) {
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order :: No Customer exists with provided ID"));

        var purchaseProducts = productClient.purchaseProducts(request.products());

        var order = orderRepository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);


        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchaseProducts
                )
        );


        return order.getId();

    }


    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .toList();

    }

    public OrderResponse findById(Integer orderId) {

        return orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", orderId)));

    }
}
