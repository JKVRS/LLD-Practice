package org.lld.SOLIDprinciples.OCP;

interface PaymentMethod {
    public abstract void pay(double amount);
}

// Lets stratergy to Payment
class CreditCartPayment implements PaymentMethod {
    @Override
    public void pay(double amount){
        System.out.println(amount+", Payed using Credit Card");
    }
}
class PayPalMethod implements PaymentMethod {
    @Override
    public void pay(double amount){
        System.out.println(amount+", Payed using PayPal");
    }
}

public class PaymentProcessor {
    public void process(PaymentMethod paymentMethod, double amount){
        paymentMethod.pay(amount);
    }
}

class CheckoutService {
    public void checkout(PaymentMethod paymentMethod, double amount) {
        PaymentProcessor processor = new PaymentProcessor();
        processor.process(paymentMethod, amount);
    }
}

class PaymentMethodExecutor {
    public static void main(String[] args){
        CheckoutService checkoutService = new CheckoutService();
        checkoutService.checkout(new CreditCartPayment(), 100.00);
        checkoutService.checkout(new PayPalMethod(), 20000.00);
    }
}
