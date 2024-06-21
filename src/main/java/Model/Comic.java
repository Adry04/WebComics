package Model;

public class Comic {

    private String ISBN;
    private String author;
    private double price;
    private String title;
    private String desc;
    private int categoryId;
    private double sale;

    public Comic(String ISBN, String author, double price, String title, String desc, int categoryId, double sale) {
        this.ISBN = ISBN;
        this.author = author;
        this.price = price;
        this.title = title;
        this.desc = desc;
        this.categoryId = categoryId;
        this.sale = sale;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }
}