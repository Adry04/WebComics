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

    public Order(int idUtente, int idOrdine, String data, double prezzoTotale, int quantita) {
        this.idUtente = idUtente;
        this.idOrdine = idOrdine;
        this.data = data;
        this.prezzoTotale = prezzoTotale;
        this.quantita = quantita;
        comics = new ArrayList<>();
    }

    public Order(int idUtente, int idOrdine, String data, double prezzoTotale, int quantita, List<Comic> comics) {
        this.idUtente = idUtente;
        this.idOrdine = idOrdine;
        this.data = data;
        this.prezzoTotale = prezzoTotale;
        this.quantita = quantita;
        this.comics = comics;
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
}