package org.lld.paymentprocessing;

public class FraudDetection implements TransactionObeserver{
    @Override
    public void update(String transactioId, String status, Double amount) {
        System.out.println("Fraud Detection: Analyzing transaction " + transactioId + ", Status: " + status + ", Amount: $" + amount);
    }
}
