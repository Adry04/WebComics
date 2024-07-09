//Salvataggi di utenti nel db e query utenti
package Model;

import java.sql.*;
import java.time.LocalDate;
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
            e.printStackTrace(System.out);
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

    public static boolean existsUser(String email) throws SQLException{
        try (Connection connection = ConPool.getConnection()){
            String query = "SELECT email FROM utente WHERE email = ?";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getUserId(String email) throws SQLException{
        try (Connection connection = ConPool.getConnection()){
            String query = "SELECT id FROM utente WHERE email = ?";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt("id");
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PaymentMethods GetPaymentsMethods(int idUtente) {
        try (Connection con = ConPool.getConnection()) {
            String query = "SELECT * FROM cdc JOIN cartautente ON cdc.id = cartautente.cdcid WHERE idUtente = ?";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUtente);
            ResultSet rs = ps.executeQuery();
            List<CreditCard> creditCards = new ArrayList<>();
            while(rs.next()) {
                String numero = rs.getString("numero");
                String intestatario = rs.getString("intestatario");
                String cvc = rs.getString("cvc");
                Date scadenza = rs.getDate("scadenza");
                LocalDate dataOdierna = LocalDate.now();
                LocalDate dataScadenza = scadenza.toLocalDate();  // Converti java.sql.Date a LocalDate
                boolean isExpired = !dataOdierna.isBefore(dataScadenza);
                creditCards.add(new CreditCard(numero, intestatario, cvc, scadenza.toString(), isExpired));
            }
            query = "SELECT * FROM cc JOIN contoutente ON contoutente.ccid = cc.id WHERE idUtente = ?";
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUtente);
            rs = ps.executeQuery();
            List<BankAccount> bankAccounts = new ArrayList<>();
            while (rs.next()) {
                String intestatario = rs.getString("intestatario");
                String IBAN = rs.getString("IBAN");
                bankAccounts.add(new BankAccount(intestatario, IBAN));
            }
            return new PaymentMethods(creditCards, bankAccounts);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
}