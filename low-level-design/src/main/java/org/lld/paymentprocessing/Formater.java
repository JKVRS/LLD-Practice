package paymentprocessing;

public interface Formater {
    void WriteMessage(String message, String paymentType, int transactionId);
}
