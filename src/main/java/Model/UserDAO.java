package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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

    //public User
}