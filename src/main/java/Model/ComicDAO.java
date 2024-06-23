package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComicDAO {

    public boolean doSave(Comic comic) {
        try (Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO fumetto (ISBN, autore, prezzo, titolo, descrizione, categoriaid, sconto) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, comic.getISBN());
            ps.setString(2, comic.getAuthor());
            ps.setDouble(3, comic.getPrice());
            ps.setString(4, comic.getTitle());
            ps.setString(5, comic.getDesc());
            ps.setString(6, comic.getCategory());
            ps.setDouble(7, comic.getSale());
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
            double sconto = rs.getDouble("sconto");
            comics.add(new Comic(ISBN, autore, prezzo, titolo, descrizione, categoria, sconto));
        }
        return comics;
    }

    public static Comic getComic(int isbn){
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
            double sconto = rs.getDouble("sconto");
            return new Comic(ISBN, autore, prezzo, titolo, descrizione, categoria, sconto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}