//Salvataggi dei carrelli nel db e query carrelli
package Model;

import jakarta.servlet.ServletException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CartDAO {
    public static boolean addCart(Cart cart, int idUtente) throws SQLException {
        try {
            List<Comic> comics = cart.getComics();
            Map<String, Integer> map = cart.getQuantities();
            Cart cartData = CartDAO.getCart(idUtente);
            List<Comic> comicsData = Objects.requireNonNull(cartData).getComics();
            for (Comic comic : comicsData) {
                if (comics.contains(comic)) {
                    if (!CartDAO.changeQuantity(comic.getISBN(), idUtente, map.get(comic.getISBN()))) {
                        throw new ServletException("errore di changeQuantity");
                    }
                } else {
                    if(!CartDAO.removeComic(comic.getISBN(), idUtente)){
                        throw new ServletException("errore di rimozione");
                    }
                }
            }
            for(Comic comic : comics) {
                if(!comicsData.contains(comic)) {
                    CartDAO.addComic(idUtente, comic, map.get(comic.getISBN()));
                }
            }
            return true;
        } catch (ServletException e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    public static boolean addComic(int idUtente, Comic comic, int quantita) throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "INSERT INTO carrello (idUtente, ISBN, quantita) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, idUtente);
        ps.setString(2, comic.getISBN());
        ps.setInt(3, quantita);
        ps.executeUpdate();
        con.close();
        return true;
    }

    public static Cart getCart(int utenteId) throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "SELECT carrello.quantita, fumetto.ISBN, fumetto.autore, fumetto.prezzo, fumetto.titolo, fumetto.descrizione, fumetto.categoria, fumetto.sconto, fumetto.immagine, fumetto.ddi FROM carrello JOIN fumetto ON carrello.isbn = fumetto.ISBN WHERE carrello.idUtente = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, utenteId);
        ResultSet rs = ps.executeQuery();
        List<Comic> comics = new ArrayList<>();
        Map<String, Integer> quantities = new HashMap<>();
        while (rs.next()) {
            String ISBN = rs.getString("ISBN");
            String autore = rs.getString("autore");
            double prezzo = rs.getDouble("prezzo");
            String titolo = rs.getString("titolo");
            String descrizione = rs.getString("descrizione");
            String categoria = rs.getString("categoria");
            int sconto = rs.getInt("sconto");
            String immagine = rs.getString("immagine");
            LocalDate data = rs.getDate("ddi").toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALIAN);
            String comicDate = data.format(formatter);
            int quantita = rs.getInt("quantita");

            Comic comic = new Comic(ISBN, autore, prezzo, titolo, descrizione, categoria, sconto, immagine, comicDate);
            comics.add(comic);
            quantities.put(ISBN, quantita);
        }
        Cart cart = new Cart(utenteId, comics);
        for (Map.Entry<String, Integer> entry : quantities.entrySet()) {
            cart.updateQuantity(entry.getKey(), entry.getValue());
        }
        con.close();
        return cart;
    }

    public static boolean changeQuantity(String ISBN, int idUtente, int quantity) throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "UPDATE carrello SET quantita=? WHERE idUtente = ? AND isbn = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, quantity);
        ps.setInt(2, idUtente);
        ps.setString(3, ISBN);
        ps.executeUpdate();
        con.close();
        return true;
    }

    public static boolean isIn(String ISBN, int idUtente) throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "SELECT * FROM carrello WHERE isbn = ? AND idUtente = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ISBN);
        ps.setInt(2, idUtente);
        ResultSet rs = ps.executeQuery();
        con.close();
        return rs.next();
    }

    public static boolean removeComic(String ISBN, int idUtente) throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "DELETE FROM carrello WHERE isbn = ? AND idUtente = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, ISBN);
        ps.setInt(2, idUtente);
        ps.executeUpdate();
        con.close();
        return true;
    }

    public static boolean removeQuantity(String ISBN, int idUtente, int newQuantity) throws SQLException {
        Connection con = ConPool.getConnection();
        String firstQuery = "SELECT quantita FROM carrello WHERE idUtente = ? AND isbn = ?";
        PreparedStatement ps1 = con.prepareStatement(firstQuery, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = ps1.executeQuery();
        int quantita = rs.getInt("quantita");
        String query = "UPDATE carrello SET quantita=? WHERE idUtente = ? AND isbn = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, quantita - newQuantity);
        ps.setInt(2, idUtente);
        ps.setString(3, ISBN);
        ps.executeUpdate();
        con.close();
        return true;
    }

    public static boolean addQuantity(String ISBN, int idUtente, int newQuantity) throws SQLException {
        Connection con = ConPool.getConnection();
        String firstQuery = "SELECT quantita FROM carrello WHERE idUtente = ? AND isbn = ?";
        PreparedStatement ps1 = con.prepareStatement(firstQuery, Statement.RETURN_GENERATED_KEYS);
        ps1.setInt(1, idUtente);
        ps1.setString(2, ISBN);
        ResultSet rs = ps1.executeQuery();
        if(rs.next()) {
            int quantita = rs.getInt("quantita");
            String query = "UPDATE carrello SET quantita = ? WHERE idUtente = ? AND isbn = ?";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int quantita2 = quantita + newQuantity;
            ps.setInt(1, quantita2);
            ps.setInt(2, idUtente);
            ps.setString(3, ISBN);
            ps.executeUpdate();
            con.close();
            return true;
        } else {
            System.out.println("Nessuna voce trovata nel carrello per l'utente e ISBN specificati.");
            con.close();
            return false;
        }
    }
}