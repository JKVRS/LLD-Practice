package org.lld.paymentprocessing;

public class MerchantNotifier implements TransactionObeserver{
    @Override
    public void update(String transactioId, String status, Double amount) {
        System.out.println("Merchant notified: Transaction " + transactioId + " is " + status + ", Amount: $" + amount);
    }
}
