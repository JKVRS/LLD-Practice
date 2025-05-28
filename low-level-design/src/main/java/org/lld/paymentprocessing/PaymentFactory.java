package paymentprocessing;

public class PaymentFactory {
    // Creation method
//    public abstract Payment createPayment(Formater messageFormater);
    public static Payment createPayment(String paymentType) {
        switch (paymentType){
            case "CREDIT_CARD": return new CreditCardPayment(new MessageFormatter());
            case "UPI": return new UPIPayment(new MessageFormatter());
            case "PAYPAL": return new PayPalPayment(new MessageFormatter());
            default: throw new IllegalArgumentException("Invalid payment Type " +paymentType);
        }
    }
}
