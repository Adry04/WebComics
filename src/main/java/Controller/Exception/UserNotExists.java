package Controller.Exception;

public class UserNotExists extends Exception{
    public UserNotExists() {
        super("Utente non trovato");
    }

    public UserNotExists(String message) {
        super(message);
    }
}
