package DBController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DBConnectionController {
    private String url = "jdbc:mysql://phpmyadmin.kartoffelkoepfe.de:2225/Kochapp";
    private String user="dummy";
    private String pwd="Prais3TheH0rscht";

     Connection connection;
     Statement statement;

    public DBConnectionController(){
        try {
            this.connection = DriverManager.getConnection(url,user,pwd);
            this.statement =connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
