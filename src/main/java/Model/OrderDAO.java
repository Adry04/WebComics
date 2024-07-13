//Salvataggio degli ordini del db e query degli ordini
package Model;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class OrderDAO {
    public static boolean doSave(int idUtente, Cart cart, Address a, CreditCard cdc, BankAccount cc) throws SQLException {
        Connection con = ConPool.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO Ordine (idUtente, dataordine, prezzoacquisto, quantita, indirizzo, CAP, idcdc, idcc) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idUtente);
        LocalDate currentDate = LocalDate.now();
        ps.setDate(2, java.sql.Date.valueOf(currentDate));
        ps.setDouble(3, cart.getTotalPrice());
        ps.setInt(4, cart.getTotalQuantity());
        ps.setString(5, a.getIndirizzo());
        ps.setString(6, a.getCap());
        if (cdc == null) {
            ps.setObject(7, null);
            ps.setInt(8, cc.getId());
        } else if (cc == null) {
            ps.setInt(7, cdc.getId());
            ps.setObject(8, null);
        }
        ps.executeUpdate();
        ResultSet generatedKeys = ps.getGeneratedKeys();
        int ordineId = 0;
        if (generatedKeys.next()) {
            ordineId = generatedKeys.getInt(1); //Recupera automaticamente la prima chiave generata
        }
        for (Comic comic : cart.getComics()) {
            String fumettoOrdinatoSQL = "INSERT INTO FumettoOrdinato (ordineid, ISBN, quantita, prezzo_fumetto) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(fumettoOrdinatoSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ordineId);
            ps.setString(2, comic.getISBN());
            ps.setInt(3, cart.getQuantity(comic.getISBN()));
            ps.setDouble(4, comic.getFinalPrice());
            ps.executeUpdate();
        }
        con.close();
        return true;
    }

    public static Order getOrder(int id, int idUtente) throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "SELECT * FROM ordine WHERE ordine.idUtente = ? AND ordine.id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, idUtente);
        ps.setInt(2, id);
        ResultSet rs = ps.executeQuery();
        List<Comic> comics = new ArrayList<>();
        Order order = null;
        while (rs.next()) {
            String dataOrdine = String.valueOf(rs.getDate("dataordine"));
            double prezzoOrdine = rs.getDouble("prezzoacquisto");
            int quantita = rs.getInt("quantita");
            String indirizzo = rs.getString("indirizzo");
            String CAP = rs.getString("CAP");
            int idcdc = rs.getInt("idcdc");
            int idcc = rs.getInt("idcc");
            order = new Order(idUtente, id, dataOrdine, prezzoOrdine, quantita, indirizzo, CAP, idcdc, idcc);
            query = "SELECT isbn FROM fumettoordinato WHERE ordineid = ?";
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            ResultSet rsComic = ps.executeQuery();
            while (rsComic.next()) {
                String queryComic = "SELECT * FROM fumetto WHERE isbn = ?";
                PreparedStatement psComic = con.prepareStatement(queryComic, Statement.RETURN_GENERATED_KEYS);
                psComic.setString(1, rsComic.getString("ISBN"));
                ResultSet rsComics = psComic.executeQuery();
                while (rsComics.next()) {
                    String queryPriceComic = "SELECT prezzo_fumetto FROM fumettoordinato WHERE ordineid = ? AND ISBN = ?";
                    PreparedStatement psPriceComic = con.prepareStatement(queryPriceComic, Statement.RETURN_GENERATED_KEYS);
                    psPriceComic.setInt(1, id);
                    psPriceComic.setString(2, rsComic.getString("ISBN"));
                    ResultSet rsPriceComics = psPriceComic.executeQuery();
                    while (rsPriceComics.next()) {
                        String ISBN = rsComics.getString("ISBN");
                        String autore = rsComics.getString("autore");
                        double prezzo = rsPriceComics.getDouble("prezzo_fumetto");
                        String titolo = rsComics.getString("titolo");
                        String descrizione = rsComics.getString("descrizione");
                        String categoria = rsComics.getString("categoria");
                        String immagine = rsComics.getString("immagine");
                        LocalDate data = rsComics.getDate("ddi").toLocalDate();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALIAN);
                        String comicDate = data.format(formatter);
                        comics.add(new Comic(ISBN, autore, prezzo, titolo, descrizione, categoria, 0, immagine, comicDate));
                    }
                }
            }
        }
        Objects.requireNonNull(order).setComics(comics);
        con.close();
        return order;
    }

    public static List<Order> getOrders(int idUtente) throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "SELECT * FROM ordine WHERE idUtente = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idUtente);
        ResultSet rs = ps.executeQuery();
        List<Order> orders = new ArrayList<>();
        while (rs.next()) {
            int idOrdine = rs.getInt("id");
            String dataOrder = String.valueOf(rs.getDate("dataordine"));
            double prezzoOrder = rs.getDouble("prezzoacquisto");
            int quantita = rs.getInt("quantita");
            String indirizzo = rs.getString("indirizzo");
            String cap = rs.getString("CAP");
            int cdc = rs.getInt("idcdc");
            int cc = rs.getInt("idcc");
            query = "SELECT ISBN FROM fumettoordinato WHERE ordineid = ?";
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, rs.getInt("id"));
            ResultSet rsComic = ps.executeQuery();
            List<Comic> comics = new ArrayList<>();
            while (rsComic.next()) {
                String queryComic = "SELECT * FROM fumetto WHERE ISBN = ?";
                PreparedStatement psComic = con.prepareStatement(queryComic, Statement.RETURN_GENERATED_KEYS);
                psComic.setString(1, rsComic.getString("ISBN"));
                ResultSet rsComics = psComic.executeQuery();
                while (rsComics.next()) {
                    String ISBN = rsComics.getString("ISBN");
                    String autore = rsComics.getString("autore");
                    double prezzo = rsComics.getDouble("prezzo");
                    String titolo = rsComics.getString("titolo");
                    String descrizione = rsComics.getString("descrizione");
                    String categoria = rsComics.getString("categoria");
                    int sconto = rsComics.getInt("sconto");
                    String immagine = rsComics.getString("immagine");
                    LocalDate data = rsComics.getDate("ddi").toLocalDate();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALIAN);
                    String comicDate = data.format(formatter);
                    comics.add(new Comic(ISBN, autore, prezzo, titolo, descrizione, categoria, sconto, immagine, comicDate));
                }
            }
            orders.add(new Order(idUtente, idOrdine, dataOrder, prezzoOrder, quantita, comics, indirizzo, cap, cdc, cc));
        }
        con.close();
        return orders;
    }

    public static List<Order> getTotalOrders() throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "SELECT * FROM ordine";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = ps.executeQuery();
        List<Order> orders = new ArrayList<>();
        while (rs.next()) {
            int idUtente = rs.getInt("idUtente");
            int idOrdine = rs.getInt("id");
            String dataOrder = String.valueOf(rs.getDate("dataordine"));
            double prezzoOrder = rs.getDouble("prezzoacquisto");
            int quantita = rs.getInt("quantita");
            String indirizzo = rs.getString("indirizzo");
            String cap = rs.getString("CAP");
            int cdc = rs.getInt("idcdc");
            int cc = rs.getInt("idcc");
            orders.add(new Order(idUtente, idOrdine, dataOrder, prezzoOrder, quantita, indirizzo, cap, cdc, cc));
        }
        con.close();
        return orders;
    }
}