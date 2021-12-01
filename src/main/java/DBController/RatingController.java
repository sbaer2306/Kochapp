package DBController;

import Datastructures.Recipe;
import Datastructures.UserModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class RatingController extends DBConnectionController{

    public RatingController(){
        super();
    }

    //like == true --> user hat geliked
    //like == false --> dislike
    public boolean rateRecipe(UserModel user, Recipe recipe, boolean like){

        String username=user.getUsername();
        String recipeid= recipe.getId();

        String sql = "INSERT INTO user_recipes_ratings (user_uid, recipe_rid, liked) VALUES (?,?,?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1,username);
            pstmt.setString(2, recipeid);

            int liked=0;
            if(like) liked=1;

            pstmt.setString(3, String.valueOf(liked));

            pstmt.execute();
            return true;

        }
        //PK bereits vorhanden --> SQLIntegrityConstraintViolationException
        //--> d.h.: User hat bereits bewertet
        //TODO: woanders catchen und entsprechende Fehlermelndung ausgeben
        catch(SQLIntegrityConstraintViolationException e){
            e.printStackTrace();
            return false;
        }
        catch (SQLException e ) {
            e.printStackTrace();
            return false;
        }
    }

    //Falls Strings als Übergabeparameter
    //Wenn möglich obere Funktion nehmen
    public boolean rateRecipe(String username, String recipeid, boolean like){
        UserModel user= new UserModel();
        user.setUsername(username);

        Recipe recipe= new Recipe();
        recipe.setId(recipeid);

        return rateRecipe(user, recipe, like);
    }
}
