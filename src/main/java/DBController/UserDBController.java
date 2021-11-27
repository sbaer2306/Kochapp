package DBController;

import Clerks.HashingClerk;
import Datastructures.UserModel;
import java.sql.*;
import java.util.Objects;

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
    public boolean validateLogin(UserModel user) {

        try {
            ResultSet result = getUserByUsername(user.getUsername());

            if(result == null) return false;

            String dbPwd = new String();

            while (result.next()){
                dbPwd = result.getString("pwd");
            }

            HashingClerk charles = new HashingClerk();
            String localPwdHash= charles.hash(user.getPwd());

            return localPwdHash.equals(dbPwd);
        }catch (SQLException e){
            System.out.println(e);
            return false;
        }

    }

    //User in der Datenbank mit usernamen finden
    private ResultSet getUserByUsername(String username) throws SQLException {
        String sql= "select  * from users WHERE username_uid=?";

        PreparedStatement pstmt= connection.prepareStatement(sql);
        pstmt.setString(1, username);

        ResultSet resSet = pstmt.executeQuery();

        return resSet;

    }

    public boolean checkUserExists(String username){
        try {
            ResultSet res= getUserByUsername(username);
            return res != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }

}
