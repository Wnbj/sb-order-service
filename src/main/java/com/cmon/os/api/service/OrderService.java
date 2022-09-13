package com.cmon.os.api.service;

import com.cmon.os.api.common.Payment;
import com.cmon.os.api.common.TransactionRequest;
import com.cmon.os.api.common.TransactionResponse;
import com.cmon.os.api.entity.Order;
import com.cmon.os.api.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    private OrderRepository repository;
    private RestTemplate restTemplate;

    public OrderService(OrderRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public TransactionResponse saveOrder(TransactionRequest request) {
        String responseMsg;
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        Payment paymentResponse = restTemplate.postForObject("http://localhost:9191/v1/payments/create", payment, Payment.class);

        responseMsg = paymentResponse.getPaymentStatus().equals("success") ? "Order placed" : "Payment failed";

        repository.save(order);

        return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(), responseMsg);
    }


}
