//Bean degli indirizzi
package Model;

public class Address {
    private String indirizzo;
    private String cap;
    private String provincia;
    private int id;

    public Address(String indirizzo, String cap, String provincia) {
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.provincia = provincia;
    }

    public Address(String indirizzo, String cap, String provincia, int id) {
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.provincia = provincia;
        this.id = id;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}