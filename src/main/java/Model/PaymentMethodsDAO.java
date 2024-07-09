package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodsDAO {

    public boolean doCardSave(int idUtente, CreditCard c) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO cdc (numero, intestatario, CVC, scadenza) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, c.getNumero());
            ps.setString(2, c.getIntestatario());
            ps.setString(3, c.getCvc());
            ps.setDate(4, Date.valueOf(c.getDataScadenza()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            ps = con.prepareStatement("INSERT INTO cartautente (cdcid, idUtente) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, rs.getInt("id"));
            ps.setInt(2, idUtente);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    public boolean doBankAccountSave(int idUtente, BankAccount b) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO cc (intestatario, IBAN) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, b.getIntenstatario());
            ps.setString(2, b.getIBAN());
            ps.executeQuery();
            ResultSet rs = ps.getGeneratedKeys();
            ps = con.prepareStatement("INSERT INTO contoutente (ccid, idUtente) VALUES (?, ?)");
            ps.setInt(1, rs.getInt("id"));
            ps.setInt(2, idUtente);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return false;
        }
    }
}