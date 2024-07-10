package Model;

public class Address {
    private String indirizzo;
    private String cap;
    private String provincia;

    public Address(String indirizzo, String cap, String provincia) {
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.provincia = provincia;
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
}