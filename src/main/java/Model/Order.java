//Bean degli ordini
package Model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int idUtente;
    private String data;
    private double prezzoTotale;
    private int quantita;
    private int idOrdine;
    private List<Comic> comics;
    private String indirizzo;
    private String CAP;
    private int idcdc;
    private int idcc;

    public Order(int idUtente, int idOrdine, String data, double prezzoTotale, int quantita, String indirizzo, String CAP, int idcdc, int idcc) {
        this.idUtente = idUtente;
        this.idOrdine = idOrdine;
        this.data = data;
        this.prezzoTotale = prezzoTotale;
        this.quantita = quantita;
        this.comics = new ArrayList<>();
        this.indirizzo = indirizzo;
        this.CAP = CAP;
        this.idcdc = idcdc;
        this.idcc = idcc;
    }

    public Order(int idUtente, int idOrdine, String data, double prezzoTotale, int quantita, List<Comic> comics, String indirizzo, String CAP, int cdc, int cc) {
        this.idUtente = idUtente;
        this.idOrdine = idOrdine;
        this.data = data;
        this.prezzoTotale = prezzoTotale;
        this.quantita = quantita;
        this.comics = comics;
        this.indirizzo = indirizzo;
        this.CAP = CAP;
        this.idcdc = idcdc;
        this.idcc = idcc;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getPrezzoTotale() {
        return prezzoTotale;
    }

    public void setPrezzoTotale(double prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public List<Comic> getComics() {
        return comics;
    }

    public void setComics(List<Comic> comics) {
        this.comics = comics;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCAP() {
        return CAP;
    }

    public void setCAP(String CAP) {
        this.CAP = CAP;
    }

    public int getIdcdc() {
        return idcdc;
    }

    public void setIdcdc(int idcdc) {
        this.idcdc = idcdc;
    }

    public int getIdcc() {
        return idcc;
    }

    public void setIdcc(int idcc) {
        this.idcc = idcc;
    }
}