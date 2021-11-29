package DBController;

import Datastructures.UserModel;
import java.sql.*;

public class UserDBController extends DBConnectionController{

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
            pstmt.setString(3, user.getPwdHash());

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

            String localPwdHash= user.getPwdHash();

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

    //User mit per Email Adresse finden
    private ResultSet getUserByEmail(String email) throws SQLException {
        String sql= "select  * from users WHERE email=?";

        PreparedStatement pstmt= connection.prepareStatement(sql);
        pstmt.setString(1, email);

        ResultSet resSet = pstmt.executeQuery();

        return resSet;
    }

    public boolean checkUserExists(String username){
        try {
            ResultSet res= getUserByUsername(username);
            return  res.isBeforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkEmailExists(String email){
        try {
            ResultSet res= getUserByEmail(email);
            return  res.isBeforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
