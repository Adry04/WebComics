package Model;

import jakarta.servlet.ServletException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComicDAO {

    public static boolean doSave(Comic comic) {
        try (Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO fumetto (ISBN, autore, prezzo, titolo, descrizione, categoria, sconto, immagine) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, comic.getISBN());
            ps.setString(2, comic.getAuthor());
            ps.setDouble(3, comic.getPrice());
            ps.setString(4, comic.getTitle());
            ps.setString(5, comic.getDesc());
            ps.setString(6, comic.getCategory());
            ps.setDouble(7, comic.getSale());
            ps.setString(8, comic.getImmagine());
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
            String immagine = rs.getString("immagine");
            int sconto = rs.getInt("sconto");
            comics.add(new Comic(ISBN, autore, prezzo, titolo, descrizione, categoria, sconto, immagine));
        }
        return comics;
    }

    public static Comic getComic(String isbn){
        try (Connection con = ConPool.getConnection()) {
            String query = "SELECT * FROM fumetto WHERE isbn = ?";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();
            String ISBN = rs.getString("ISBN");
            String autore = rs.getString("autore");
            double prezzo = rs.getDouble("prezzo");
            String titolo = rs.getString("titolo");
            String descrizione = rs.getString("descrizione");
            String categoria = rs.getString("categoria");
            int sconto = rs.getInt("sconto");
            String immagine = rs.getString("immagine");
            return new Comic(ISBN, autore, prezzo, titolo, descrizione, categoria, sconto, immagine);
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
}