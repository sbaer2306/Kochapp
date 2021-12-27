package DBController;

import Clerks.ErrorClerk;

import java.sql.*;

public abstract class DBConnectionController {
    private String url = "jdbc:mysql://database.kartoffelkoepfe.de:2226/Kochapp";
    private String user="dummy";
    private String pwd="Prais3TheH0rscht";

     Connection connection;
     Statement statement;

    public DBConnectionController(){
        try {
            this.connection = DriverManager.getConnection(url,user,pwd);
            this.statement =connection.createStatement();
        } catch (SQLException e) {
            ErrorClerk.getInstance().showErrorMessage(e.toString());
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
