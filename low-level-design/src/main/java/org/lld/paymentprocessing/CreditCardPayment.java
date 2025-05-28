package paymentprocessing;

public class CreditCardPayment implements Payment{
    private final Formater messageFormater;

    public CreditCardPayment(Formater messageFormater) {
        this.messageFormater = messageFormater;
    }

    @Override
    public void processPayment(String transactionId, Double amount) throws Exception {
        if(amount <= 0){
            throw new Exception("Invalid amount");
        }
        messageFormater.WriteMessage("Processing", "Credit Card", 1);
    }
}
