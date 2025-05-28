package paymentprocessing;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private final String id;
    private final Double amount;
    private String status;
    private final Payment payment;
    private final PricingStategy pricingStategy;
    private final List<TransactionObeserver> transactionObeserverList;
    private final TransactionLogger logger;

    public Transaction(String id, Double amount, String paymentType, PricingStategy pricingStategy){
        this.id=id;
        this.amount=amount;
        this.status="INITIATED";
        this.payment=PaymentFactory.createPayment(paymentType);
        this.pricingStategy=pricingStategy;
        this.transactionObeserverList=new ArrayList<>();
        this.logger = TransactionLogger.getInstance();
        attachDefaultObservers();
        notifyObservers();
        logger.logTransaction(id, status, amount);
    }

    public void attachDefaultObservers(){
        transactionObeserverList.add(new UserNotifier());
        transactionObeserverList.add(new MerchantNotifier());
        transactionObeserverList.add(new FraudDetection());
    }

    public void detachObserver(TransactionObeserver transactionObeserver){
        transactionObeserverList.remove(transactionObeserver);
    }

    public void notifyObservers(){
        transactionObeserverList.forEach(transactionObserver ->
                transactionObserver.update(id, status, amount));
    }

    public void process(){
        try {
            // Apply pricing strategy
            pricingStategy.applyPricing(amount);
            //Process Payment
            payment.processPayment(id, amount);
            //
        } catch(Exception e) {
            // if transaction have exceptio
            status = "FAILED";
            logger.logTransaction(id, status, amount);
            notifyObservers();
            System.err.println("Transaction failed "+e.getMessage());
        }
    }

}
