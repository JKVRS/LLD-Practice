package org.lld.paymentprocessing;

public class TransactionLogger {
    private static volatile TransactionLogger instance;

    private TransactionLogger(){
        // Default constructor
    }

    public static TransactionLogger getInstance(){
        if(instance == null){
            synchronized (TransactionLogger.class){
                if(instance == null){
                    instance = new TransactionLogger();
                }
            }
        }
        return instance;
    }

    public void logTransaction(String transactionId, String status, Double amount){
        System.out.println("Log: Transaction " + transactionId + ", Status: " + status + ", Amount: " + amount);
    }
}
