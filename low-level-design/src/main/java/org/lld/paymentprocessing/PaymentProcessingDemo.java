package org.lld.paymentprocessing;

// Building Payment Processing System
// 1. Support multiple Payment Method (CreditCardPayment, PayPalPayment, UPIPayment) - What type of Payment Method ?
// 2. Each PaymentMethod have its own processing
// 3. Apply Dynamic pricing Rules (algorithms) - (Discounts) -> is the price fixed or vary as per the userType, Region
// 4. Notify Stack holders (User, Merchant, Fraud Detection System) -> transaction Status changes - Who are the stack holders to notify when transaction is initiated?
// 5. Single Instance of Logging mechanism for logging purpose -> is the transaction logger should be thread safe for concurrent transactions?
// 6. Should the System handles the invalid payment methods for invalid inputs and failed transactions

// Step 2. To determine the entities
// 1. who interact with the system? - Customers, Merchants (External), (FraudDetection system, Payment Gateway) - (Internal)
// 2. What are the component of the systems (Entities) - (Nouns, main object) - Thier responsibilities (verbs)
// 3. What behaviours need to be extracted (Class vs interface vs abstract class)
// 4. Interface -> behaviour shared across multiple components
// 5. Class -> Concrete implementation, Stateful Objects

// Core class / Entities
// 1. Transaction -> Singleton Pattern (one transaction)
// 2. Payment -  (Different Payment Methods) -> Factory Pattern
// 3. Pricing Strategy Discount - (Rules/Algorithms) -> different pricing strategy (Regular. Discount, Region Charge)
// 4. Observer - list of transactionObservers (list of entities notify when the transaction initiated) -> observer pattern
// 5. Logger -> Logging (TransactionLogger) - Singleton Pattern

public class PaymentProcessingDemo {
    public static void main(String[] args) {
        // Regular user with Credit card
        Transaction transaction1 = new Transaction("TX001", 100.00, "CREDIT_CARD", new RegularPricing());
        transaction1.process();

        // Premium user with UPI in Japan
        PricingStategy japanPremium = (amount) -> new JapanSurcharge().applyPricing(new PremiumPricing().applyPricing(amount));
        Transaction transaction2 = new Transaction("TX002", 200.0, "UPI", japanPremium);
        transaction2.process();

        // invalid amount with PayPal
        Transaction transaction3 = new Transaction("TX003", -50.0, "PAYPAL", new RegularPricing());
        transaction3.process();
    }
}
