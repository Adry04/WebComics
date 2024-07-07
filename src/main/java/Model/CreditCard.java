package Model;

public class CreditCard {
    private String numero;
    private String intestatario;
    private int cvc;
    private String dataScadenza;
    private boolean isExpired;

    public CreditCard(String numero, String intestatario, int cvc, String dataScadenza, boolean isExpired) {
        this.numero = numero;
        this.intestatario = intestatario;
        this.cvc = cvc;
        this.dataScadenza = dataScadenza;
        this.isExpired = isExpired;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIntestatario() {
        return intestatario;
    }

    public void setIntestatario(String intestatario) {
        this.intestatario = intestatario;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public String getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(String dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }
}