//Connessione con DB, query e salvataggi dei fumetti
package Model;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ComicDAO {

    public static boolean doSave(Comic comic) {
        try (Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO fumetto (ISBN, autore, prezzo, titolo, descrizione, categoria, sconto, immagine, ddi) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, comic.getISBN());
            ps.setString(2, comic.getAuthor());
            ps.setDouble(3, comic.getPrice());
            ps.setString(4, comic.getTitle());
            ps.setString(5, comic.getDesc());
            ps.setString(6, comic.getCategory());
            ps.setDouble(7, comic.getSale());
            ps.setString(8, comic.getImmagine());
            LocalDate currentDate = LocalDate.now();
            ps.setDate(9, java.sql.Date.valueOf(currentDate));
            ps.executeUpdate();
            return true;
        } catch (SQLException e){
            System.out.println(e);
            return false;
        }
    }

    public static List<Comic> getComics() throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "SELECT * FROM fumetto";
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
    }

    public static Comic getComic(String isbn){
        try (Connection con = ConPool.getConnection()) {
            String query = "SELECT * FROM fumetto WHERE isbn = ?";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
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
                return new Comic(ISBN, autore, prezzo, titolo, descrizione, categoria, sconto, immagine, comicDate);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean existsISBN(String isbn, String titolo) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            String query = "SELECT * FROM fumetto WHERE isbn = ? OR titolo = ?";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, isbn);
            ps.setString(2, titolo);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Comic> getNews(String category, int limit) {
        try (Connection con = ConPool.getConnection()) {
            List<Comic> comics = new ArrayList<>();
            ResultSet rs = null;
            if (limit == 0 && category.isEmpty()) {
                String query = "SELECT * FROM fumetto ORDER BY ddi";
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                rs = ps.executeQuery();
            } else if (limit == 0 && !category.isEmpty()) {
                String query = "SELECT * FROM fumetto WHERE categoria = ? ORDER BY ddi";
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, category);
                rs = ps.executeQuery();
            } else if (category.isEmpty()) {
                String query = "SELECT * FROM fumetto ORDER BY ddi LIMIT ?";
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, limit);
                rs = ps.executeQuery();
            } else {
                String query = "SELECT * FROM fumetto WHERE categoria = ? ORDER BY ddi LIMIT ?";
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, category);
                ps.setInt(2, limit);
                rs = ps.executeQuery();
            }
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
        }
        return null;
    }

    public static boolean addWish(String isbn, int idUtente) {
        try (Connection con = ConPool.getConnection()) {
            String query = "INSERT INTO wishlist (idUtente, isbn) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUtente);
            ps.setString(2, isbn);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static List<Comic> getWishes(int idUtente) {
        try (Connection con = ConPool.getConnection()) {
            List<Comic> wishComics = new ArrayList<>();
            String query = "SELECT * FROM wishlist JOIN fumetto ON fumetto.ISBN = wishlist.isbn WHERE wishlist.idUtente = ?";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUtente);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
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
                wishComics.add(new Comic(ISBN, autore, prezzo, titolo, descrizione, categoria, sconto, immagine, comicDate));
            }
            return wishComics;
        } catch (SQLException e){
            System.err.println(e);
            return null;
        }
    }

    public static boolean removeWish(String isbn, int idUtente) {
        try (Connection con = ConPool.getConnection()) {
            String query = "DELETE FROM wishlist WHERE idUtente = ? AND isbn = ?";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUtente);
            ps.setString(2, isbn);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean isWished(String isbn, int idUtente) {
        try (Connection con = ConPool.getConnection()) {
            String query = "SELECT * FROM wishlist WHERE idUtente = ? AND isbn = ?";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUtente);
            ps.setString(2, isbn);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }
}