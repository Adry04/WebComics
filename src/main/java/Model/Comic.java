package Model;

public class Comic {

    private String ISBN;
    private String author;
    private double price;
    private String title;
    private String desc;
    private String category;
    private int sale;
    private String immagine;

    public Comic(String ISBN, String author, double price, String title, String desc, String category, int sale, String immagine) {
        this.ISBN = ISBN;
        this.author = author;
        this.price = price;
        this.title = title;
        this.desc = desc;
        this.category = category;
        this.sale = sale;
        this.immagine = immagine;
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
}