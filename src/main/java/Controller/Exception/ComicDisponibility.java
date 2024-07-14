package Controller.Exception;

public class ComicDisponibility extends Exception{
    public ComicDisponibility() {
        super("Fumetto non disponibile");
    }

    public ComicDisponibility(String message) {
        super(message);
    }
}
