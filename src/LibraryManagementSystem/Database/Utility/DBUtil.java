package LibraryManagementSystem.Database.Utility;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final HikariDataSource dataSource;
     /* private static final String URL="jdbc:mysql://localhost:3306/LibraryDB";
     private static final String USER="root";
    private static final String PASSWORD="81180b35765";

    */
      static {
       HikariConfig hikariConfig= new HikariConfig();
       hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/LibraryDB");
       hikariConfig.setUsername("root");
       hikariConfig.setPassword("81180b35765");
       hikariConfig.setMinimumIdle(5);
       hikariConfig.setMaximumPoolSize(10);
       hikariConfig.setIdleTimeout(60000);
       hikariConfig.setConnectionTimeout(30000);
       hikariConfig.setLeakDetectionThreshold(2000);


       dataSource = new HikariDataSource(hikariConfig);
   }
    public static Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }

}
