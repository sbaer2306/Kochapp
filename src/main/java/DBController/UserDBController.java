package DBController;

import Clerks.HashingClerk;
import Datastructures.UserModel;
import java.sql.*;

public class UserDBController extends DBConnectionController{

    //TODO: warum ist die connection null
    public UserDBController()  {
        super();
    }

    //Registrierung
    public boolean insertNewUser(UserModel user){
        try {
            String sql = "INSERT INTO users (username_uid,email,pwd) VALUES (?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,user.getUsername());
            pstmt.setString(2,user.getEmail());

            //Passwort hashen
            HashingClerk carl = new HashingClerk();
            String hashedPwd = carl.hash(user.getPwd());

            pstmt.setString(3, hashedPwd);
            pstmt.execute();

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

        return true;
    }

    //Login
    public boolean validateLogin(UserModel user) throws SQLException {

        //TODO: Aufbau so sinnvoll?
        if(userExists(user.getUsername())){ //--> user in DB vorhanden

            String sql= "select  pwd from users WHERE username_uid=?";

            PreparedStatement pstmt= connection.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());

            ResultSet resSet = pstmt.executeQuery();

            String DBhash=null;

            while (resSet.next()){
                DBhash= resSet.getString(1); //hashwert des pwds des users in der DB
            }

            HashingClerk carl = new HashingClerk();
            String enteredPwd =  carl.hash(user.getPwd()); //lokales pwd hashen

            if (DBhash.equals(enteredPwd)) return true; //passwörter vergleichen
            else return false; //passwort falsch

        }

        return false; //username oder passwort falsch

    }

    //Check ob User existiert
    private boolean userExists(String username) throws SQLException {
        String sql= "select  * from users WHERE username_uid=?";

        PreparedStatement pstmt= connection.prepareStatement(sql);
        pstmt.setString(1, username);

        ResultSet resSet = pstmt.executeQuery();

        return resSet != null;

    }
}
