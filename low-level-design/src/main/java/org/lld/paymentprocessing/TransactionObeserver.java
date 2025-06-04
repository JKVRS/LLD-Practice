package org.lld.paymentprocessing;

public interface TransactionObeserver {
    void update(String transactioId, String status, Double amount);
}
