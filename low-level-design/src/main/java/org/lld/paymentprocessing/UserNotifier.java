package org.lld.paymentprocessing;

public class UserNotifier implements TransactionObeserver {
    @Override
    public void update(String transactioId, String status, Double amount) {
        System.out.println("User notified: Transaction " + transactioId + " is " + status + ", Amount: $" + amount);
    }
}
