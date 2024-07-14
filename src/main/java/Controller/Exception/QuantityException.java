package Controller.Exception;

public class QuantityException extends Exception{
    public QuantityException() {
        super("Errore di quantità");
    }

    public QuantityException(String message) {
        super(message);
    }
}
