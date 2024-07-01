package Model;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartDAO {
    public static boolean addComic(String ISBN, int idUtente, int quantity) {
        try (Connection con = ConPool.getConnection()){
            String query = "INSERT INTO carrello (idUtente, isbn, quantita) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUtente);
            ps.setString(2, ISBN);
            ps.setInt(3, quantity);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    public static List<Comic> getCart() {
        try (Connection con = ConPool.getConnection()){
            String query = "SELECT * FROM carrello JOIN fumetto ON carrello.isbn=fumetto.ISBN";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();
            List<Comic> comics = new ArrayList<>();
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
                comics.add(new Comic(ISBN, autore, prezzo, titolo, descrizione, categoria, sconto, immagine, comicDate));
            }
            return comics;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    public static boolean addQuantity(String ISBN, int idUtente, int newQuantity) {
        try (Connection con = ConPool.getConnection()){
            String firstQuery = "SELECT quantita FROM carrello WHERE idUtente = ? AND isbn = ?";
            PreparedStatement ps1 = con.prepareStatement(firstQuery, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps1.executeQuery();
            int quantita = rs.getInt("quantita");
            String query = "UPDATE carrello SET quantita=? WHERE idUtente = ? AND isbn = ?";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, quantita + newQuantity);
            ps.setInt(2, idUtente);
            ps.setString(3, ISBN);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    public static boolean removeQuantity(String ISBN, int idUtente, int newQuantity) {
        try (Connection con = ConPool.getConnection()){
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
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    public static boolean changeQuantity(String ISBN, int idUtente, int quantity) {
        try (Connection con = ConPool.getConnection()){
            String query = "UPDATE carrello SET quantita=? WHERE idUtente = ? AND isbn = ?";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, quantity);
            ps.setInt(2, idUtente);
            ps.setString(3, ISBN);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    public static boolean isIn(String ISBN, int idUtente) {
        try (Connection con = ConPool.getConnection()){
            String query = "SELECT * FROM carrello WHERE isbn = ? AND idUtente = ?";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, ISBN);
            ps.setInt(2, idUtente);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    public static boolean removeComic(String ISBN, int idUtente) {
        try (Connection con = ConPool.getConnection()){
            String query = "DELETE FROM carrello WHERE isbn = ? AND idUtente = ?";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, ISBN);
            ps.setInt(2, idUtente);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
}