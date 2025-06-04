package org.lld.paymentprocessing;

public interface Payment {
    void processPayment(String transactionId, Double amount) throws Exception;
}
