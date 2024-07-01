package Model;

import java.util.List;

public class Cart {
    private int utenteId;
    private List<Comic> comics;

    public Cart(int utenteId, List<Comic> comics) {
        this.utenteId = utenteId;
        this.comics = comics;
    }

    public int getUtenteId() {
        return utenteId;
    }

    public void setUtenteId(int utenteId) {
        this.utenteId = utenteId;
    }

    public List<Comic> getComics() {
        return comics;
    }

    public void setComics(List<Comic> comics) {
        this.comics = comics;
    }
}