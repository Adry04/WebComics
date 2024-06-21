package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean doSave(User user, String password) {
        try (Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO utente (email, isAdmin, nome, cognome, pass) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setBoolean(2, user.getIsAdmin());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            // criptare password prima di inserirla nel database
            String hashedPassword = Hash.hashPassword(password);
            ps.setString(5, hashedPassword);
            ps.executeUpdate();
            return true;
        } catch (SQLException e){
            System.out.println(e);
            return false;
        }
    }

    public static List<User> getUsers() throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "SELECT * FROM utente";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            String nome = rs.getString("nome");
            String cognome = rs.getString("cognome");
            String email = rs.getString("email");
            users.add(new User(nome, cognome, email));
        }
        return users;
    }
}