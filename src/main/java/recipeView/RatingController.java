package recipeView;

import DBController.RatingDBController;
import Datastructures.Recipe;
import Datastructures.UserModel;
import Session.UserSession;

import java.sql.SQLException;

public class RatingController {

    // private Recipe recipe;
    private RatingDBController ratingDBController;

    public RatingController() { // Recipe recipe
        // this.recipe = recipe;
        this.ratingDBController = new RatingDBController();
    }

    public boolean like(Recipe recipe) {
        return ratingDBController.insertNewLike(new UserSession().getUserSession(), recipe);
    }

    public boolean dislike(Recipe recipe) {
        return ratingDBController.insertNewLike(new UserSession().getUserSession(), recipe);
    }

    public boolean revokeLike(Recipe recipe) {
        try {
            return ratingDBController.revokeLike(new UserSession().getUserSession(), recipe);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean revokeDislike(Recipe recipe) {
        try {
            return ratingDBController.revokeDislike(new UserSession().getUserSession(), recipe);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
