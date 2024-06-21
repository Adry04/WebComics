package Model;

import java.sql.Connection;
import java.sql.SQLException;

public class ComicDAO {

    public boolean doSave(User user, String password) {
        try (Connection con = ConPool.getConnection()){
            //PreparedStatement ps = con.prepareStatement("INSERT INTO fumetto (email, isAdmin, nome, cognome, pass) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            //ps.
            //ps.executeUpdate();
            return true;
        } catch (SQLException e){
            System.out.println(e);
            return false;
        }
    }

}