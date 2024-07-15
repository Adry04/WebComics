package Controller.Exception;

public class PaymentNotExists extends Exception{
    public PaymentNotExists() {
        super("Metodo di pagamento non esistente");
    }

    public PaymentNotExists(String message) {
        super(message);
    }
}
