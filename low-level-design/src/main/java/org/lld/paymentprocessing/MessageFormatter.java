package paymentprocessing;

public class MessageFormatter implements Formater{
    @Override
    public void WriteMessage(String message, String paymentType, int transactionId) {
        System.out.println(message + " " + paymentType + " " + transactionId );
    }
}
