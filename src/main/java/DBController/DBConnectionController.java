package DBController;

import java.sql.*;

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

    //Zum testen der Datenbankverbindung
    public boolean connectionUp(){
        try {
            return statement.execute("SELECT * FROM difficulties");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
