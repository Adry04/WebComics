//Salvataggi di utenti nel db e query utenti
package Model;

import Controller.Exception.PaymentNotExists;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserDAO {

    public boolean doSave(User user, String password) throws SQLException {
        Connection con = ConPool.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO utente (email, isAdmin, nome, cognome, pass) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, user.getEmail());
        ps.setBoolean(2, user.getIsAdmin());
        ps.setString(3, user.getFirstName());
        ps.setString(4, user.getLastName());
        // criptare password prima di inserirla nel database
        String hashedPassword = Hash.hashPassword(password);
        ps.setString(5, hashedPassword);
        ps.executeUpdate();
        con.close();
        return true;
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
        con.close();
        return users;
    }

    public static boolean existsUser(String email) throws SQLException{
        Connection connection = ConPool.getConnection();
        String query = "SELECT email FROM utente WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        connection.close();
        return rs.next();
    }

    public static int getUserId(String email) throws SQLException{
        Connection connection = ConPool.getConnection();
        String query = "SELECT id FROM utente WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            connection.close();
            return rs.getInt("id");
        }
        connection.close();
        return -1;
    }

    public static PaymentMethods getPaymentsMethods(int idUtente) throws SQLException {
        Connection con = ConPool.getConnection();
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
            int id = rs.getInt("id");
            creditCards.add(new CreditCard(numero, intestatario, cvc, scadenza.toString(), isExpired, id));
        }
        query = "SELECT * FROM cc JOIN contoutente ON contoutente.ccid = cc.id WHERE idUtente = ?";
        ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idUtente);
        rs = ps.executeQuery();
        List<BankAccount> bankAccounts = new ArrayList<>();
        while (rs.next()) {
            String intestatario = rs.getString("intestatario");
            String IBAN = rs.getString("IBAN");
            String bic = rs.getString("bic");
            int id = rs.getInt("id");
            bankAccounts.add(new BankAccount(intestatario, IBAN, bic, id));
        }
        con.close();
        return new PaymentMethods(creditCards, bankAccounts);
    }

    public static boolean doCardSave(int idUtente, CreditCard c) throws SQLException, IOException {
        Connection con = ConPool.getConnection();
        PreparedStatement psVerify = con.prepareStatement("SELECT * FROM cdc WHERE numero = ? OR cvc = ?");
        psVerify.setString(1, c.getNumero());
        psVerify.setString(2, c.getCvc());
        ResultSet rsVerify = psVerify.executeQuery();
        if(rsVerify.next()) {
            con.close();
            throw new IOException("La carta esiste già");
        }
        PreparedStatement ps = con.prepareStatement("INSERT INTO cdc (numero, intestatario, CVC, scadenza) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, c.getNumero());
        ps.setString(2, c.getIntestatario());
        ps.setString(3, c.getCvc());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALIAN);
        ps.setDate(4,  Date.valueOf(LocalDate.parse(c.getDataScadenza(), formatter)));
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        while(rs.next()) {
            ps = con.prepareStatement("INSERT INTO cartautente (cdcid, idUtente) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, rs.getInt(1));
            ps.setInt(2, idUtente);
            ps.executeUpdate();
        }
        con.close();
        return true;
    }

    public static boolean doBankAccountSave(int idUtente, BankAccount b) throws SQLException, IOException {
        Connection con = ConPool.getConnection();
        PreparedStatement psVerify = con.prepareStatement("SELECT * FROM cc WHERE IBAN = ?");
        psVerify.setString(1, b.getIBAN());
        ResultSet rsVerify = psVerify.executeQuery();
        if(rsVerify.next()) {
            con.close();
            throw new IOException("La carta esiste già");
        }
        PreparedStatement ps = con.prepareStatement("INSERT INTO cc (intestatario, IBAN, bic) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, b.getIntenstatario());
        ps.setString(2, b.getIBAN());
        ps.setString(3, b.getBic());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        while(rs.next()) {
            ps = con.prepareStatement("INSERT INTO contoutente (ccid, idUtente) VALUES (?, ?)");
            ps.setInt(1, rs.getInt(1));
            ps.setInt(2, idUtente);
            ps.executeUpdate();
        }
        con.close();
        return true;
    }

    public static boolean doAddressSave(int idUtente, Address a) throws SQLException, IOException {
        Connection con = ConPool.getConnection();
        PreparedStatement psVerify = con.prepareStatement("SELECT * FROM indirizzo WHERE indirizzo = ? AND CAP = ? AND provincia = ? AND idUtente = ?");
        psVerify.setString(1, a.getIndirizzo());
        psVerify.setString(2, a.getCap());
        psVerify.setString(3, a.getProvincia());
        psVerify.setInt(4, idUtente);
        ResultSet rsVerify = psVerify.executeQuery();
        if(rsVerify.next()) {
            con.close();
            throw new IOException("La carta esiste già");
        }
        PreparedStatement ps = con.prepareStatement("INSERT INTO indirizzo (indirizzo, CAP, provincia, idUtente) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, a.getIndirizzo());
        ps.setString(2, a.getCap());
        ps.setString(3, a.getProvincia());
        ps.setInt(4, idUtente);
        ps.executeUpdate();
        con.close();
        return true;
    }

    public static List<Address> getAllAddresses(int idUtente) throws SQLException {
        Connection con = ConPool.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM indirizzo WHERE idUtente = ?", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idUtente);
        ResultSet rs = ps.executeQuery();
        List<Address> addresses = new ArrayList<>();
        while (rs.next()) {
            String indirizzo = rs.getString("indirizzo");
            String CAP = rs.getString("CAP");
            String provincia = rs.getString("provincia");
            int id = rs.getInt("id");
            addresses.add(new Address(indirizzo, CAP, provincia, id));
        }
        con.close();
        return addresses;
    }

    public static boolean doDeleteAddress(int idUtente, int id) throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "DELETE FROM indirizzo WHERE id = ? AND idUtente = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, id);
        ps.setInt(2, idUtente);
        ps.executeUpdate();
        con.close();
        return true;
    }

    public static Address getAddress(int idIndirizzo) throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "SELECT * FROM indirizzo WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idIndirizzo);
        ResultSet rs = ps.executeQuery();
        Address a = null;
        while (rs.next()) {
            int id = rs.getInt("id");
            String indirizzo = rs.getString("indirizzo");
            String CAP = rs.getString("CAP");
            String provincia = rs.getString("provincia");
            a = new Address(indirizzo, CAP, provincia, id);
        }
        con.close();
        return a;
    }

    public static CreditCard getCard(int idCarta) throws SQLException, PaymentNotExists {
        Connection con = ConPool.getConnection();
        String query = "SELECT * FROM cdc where id = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idCarta);
        ResultSet rs = ps.executeQuery();
        CreditCard c = null;
        if(!rs.next()) {
            throw new PaymentNotExists();
        } else {
            String numero = rs.getString("numero");
            String intestatario = rs.getString("intestatario");
            String cvc  = rs.getString("cvc");
            String dataScadenza = rs.getString("scadenza");
            c = new CreditCard(numero, intestatario, cvc, dataScadenza, idCarta);
        }
        con.close();
        return c;
    }

    public static List<CreditCard> getAllCards(int idUtente) throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "SELECT * FROM cartautente JOIN cdc ON cdc.id = cartautente.cdcid WHERE idUtente = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idUtente);
        ResultSet rs = ps.executeQuery();
        List<CreditCard> cards = new ArrayList<>();
        while(rs.next()){
            int id = rs.getInt("id");
            String numero = rs.getString("numero");
            String intestatario = rs.getString("intestatario");
            String cvc  = rs.getString("cvc");
            String dataScadenza = rs.getString("scadenza");
            cards.add(new CreditCard(numero, intestatario, cvc, dataScadenza, id));
        }
        con.close();
        return cards;
    }

    public static BankAccount getBankAccount(int idBankAccount) throws SQLException, PaymentNotExists {
        Connection con = ConPool.getConnection();
        String query = "SELECT * FROM cc where id = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idBankAccount);
        ResultSet rs = ps.executeQuery();
        BankAccount b = null;
        if(!rs.next()) {
            throw new PaymentNotExists();
        } else {
            String intestatario = rs.getString("intestatario");
            String iban = rs.getString("IBAN");
            String bic = rs.getString("bic");
            b = new BankAccount(intestatario, iban, bic, idBankAccount);
        }
        con.close();
        return b;
    }

    public static List<BankAccount> getAllBankAccounts(int idUtente) throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "SELECT * FROM contoutente JOIN cc ON cc.id = contoutente.ccid WHERE idUtente = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idUtente);
        ResultSet rs = ps.executeQuery();
        List<BankAccount> accounts = new ArrayList<>();
        while (rs.next()) {
            String intestatario = rs.getString("intestatario");
            String iban = rs.getString("IBAN");
            String bic = rs.getString("bic");
            int id = rs.getInt("id");
            accounts.add(new BankAccount(intestatario, iban, bic, id));
        }
        con.close();
        return accounts;
    }

    public static User getUserFromEmail(String email) throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "SELECT id, nome, cognome, isAdmin, email FROM utente WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        User user = null;
        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String cognome = rs.getString("cognome");
            boolean isAdmin = rs.getBoolean("isAdmin");
            user = new User(nome, cognome, email, isAdmin, id);
        }
        con.close();
        return user;
    }

    public static String getPassword(int idUtente) throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "SELECT pass FROM utente WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idUtente);
        ResultSet rs = ps.executeQuery();
        String pass = null;
        while (rs.next()) {
            pass = rs.getString("pass");
        }
        con.close();
        return pass;
    }

    public static boolean doDeleteCreditCard(int id) throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "DELETE FROM cdc WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, id);
        ps.executeUpdate();
        con.close();
        return true;
    }

    public static boolean doDeleteBankAccount(int id) throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "DELETE FROM cc WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, id);
        ps.executeUpdate();
        con.close();
        return true;
    }

    public static boolean doUpdateUser(String email, String nome, String cognome, int idUtente) throws SQLException {
        Connection con = ConPool.getConnection();
        String query = "UPDATE utente SET email = ?, nome = ?, cognome = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, email);
        ps.setString(2, nome);
        ps.setString(3, cognome);
        ps.setInt(4, idUtente);
        ps.executeUpdate();
        con.close();
        return true;
    }

    public static boolean doUpdateUserPassword(String password, int idUtente) throws SQLException{
        Connection con = ConPool.getConnection();
        String query = "UPDATE utente SET password = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, password);
        ps.setInt(2, idUtente);
        ps.executeUpdate();
        con.close();
        return true;
    }

    public static boolean isExistsEmail(String email) throws SQLException{
        Connection con = ConPool.getConnection();
        String query = "SELECT * FROM utente WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        con.close();
        return rs.next();
    }
}