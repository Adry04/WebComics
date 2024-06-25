//Connessione con DB
package Model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.TimeZone;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class ConPool {
    private static DataSource datasource;

    public static Connection getConnection() throws SQLException{
        if(datasource == null){
            PoolProperties p = new PoolProperties();
            p.setUrl("jdbc:mysql://localhost:3306/webcomics?serverTimezone=" + TimeZone.getDefault().getID());
            p.setDriverClassName("com.mysql.cj.jdbc.Driver");
            p.setUsername("root");
            p.setPassword("Pellegrino!22"); //dipende dalla password del tuo MySql Workbench
            p.setMaxActive(10);
            p.setInitialSize(10);
            p.setMinIdle(10);
            p.setRemoveAbandonedTimeout(60);
            p.setRemoveAbandoned(true);
            datasource = new DataSource();
            datasource.setPoolProperties(p);
        }
        return datasource.getConnection();
    }
}