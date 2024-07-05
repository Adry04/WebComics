package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private int utenteId;
    private List<Comic> comics;
    private Map<String, Integer> quantities;

    public Cart(){}

    public Cart(int utenteId, List<Comic> comics) {
        this.utenteId = utenteId;
        this.comics = comics;
        this.quantities = new HashMap<>();
    }

    public Cart(List<Comic> comics) {
        this.comics = comics;
        this.quantities = new HashMap<>();
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

    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (int quantity : quantities.values()) {
            totalQuantity += quantity;
        }
        return totalQuantity;
    }

    public Comic getComic(String ISBN) {
        for (Comic comic : comics){
            if(comic.getISBN().equals(ISBN)) {
                return comic;
            }
        }
        return null;
    }

    public Map<String, Integer> getQuantities() {
        return quantities;
    }

    public void updateQuantity(String ISBN, int newQuantity) {
        quantities.put(ISBN, newQuantity);
    }

    public int getQuantity(String ISBN) {
        return quantities.getOrDefault(ISBN, 0);
    }
}