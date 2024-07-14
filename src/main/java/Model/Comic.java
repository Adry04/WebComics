//Bean del fumetto
package Model;

import java.util.Objects;

public class Comic {

    private String ISBN;
    private String author;
    private double price;
    private String title;
    private String desc;
    private String category;
    private int sale;
    private String immagine;
    private double finalPrice;
    private String data;

    public Comic(String ISBN, String author, double price, String title, String desc, String category, int sale, String immagine, String data) {
        this.ISBN = ISBN;
        this.author = author;
        this.price = price;
        this.title = title;
        this.desc = desc;
        this.category = category;
        this.sale = sale;
        this.immagine = immagine;
        this.data = data;

        if(sale >= 0){
            double finalPrice = (price - ((price * sale)/100));
            finalPrice = (double) Math.round(finalPrice * 100)/100;
            setFinalPrice(finalPrice);
        } else {
            setFinalPrice(price);
        }
    }

    public Comic(String ISBN, String author, double price, String title, String desc, String category, int sale, String immagine) {
        this.ISBN = ISBN;
        this.author = author;
        this.price = price;
        this.title = title;
        this.desc = desc;
        this.category = category;
        this.sale = sale;
        this.immagine = immagine;

        if(sale >= 0){
            double finalPrice = (price - ((price * sale)/100));
            finalPrice = (double) Math.round(finalPrice * 100)/100;
            setFinalPrice(finalPrice);
        } else {
            setFinalPrice(price);
        }
    }


    public Comic(String ISBN, String author, double price, String title, String desc, String category, int sale, String immagine, double finalPrice, String data) {
        this.ISBN = ISBN;
        this.author = author;
        this.price = price;
        this.title = title;
        this.desc = desc;
        this.category = category;
        this.sale = sale;
        this.immagine = immagine;
        this.finalPrice = finalPrice;
        this.data = data;
    }

    public Comic(String isbn, double prezzo, String titolo, String immagine) {
        this.ISBN = isbn;
        this.price = prezzo;
        this.title = titolo;
        this.immagine = immagine;
        this.finalPrice = price;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comic comic = (Comic) o;
        if(ISBN != null) {
            return ISBN.equals(comic.ISBN);
        } else {
            return title.equals(comic.title);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }
}