package FavoriteSection;

import DBController.RatingDBController;
import Datastructures.FavoriteInformation;
import Datastructures.Recipe;
import Datastructures.UserModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class FavoriteController {
    private RatingDBController ratingDBController;
    private UserModel userModel;
    private Recipe recipe;


    public FavoriteController(UserModel user, Recipe recipe) {
        this.ratingDBController = new RatingDBController();
        this.userModel = user;
        this.recipe = recipe;
    }

    public void insertFavoriteRecipe() throws SQLException {
        FavoriteInformation fav = new FavoriteInformation(userModel, recipe);
        ratingDBController.insertFavorite(fav);
    }

    public void deleteFavoriteRecipe() throws SQLException{
        ratingDBController.deleteFavorite(getFavorite());
    }

    public FavoriteInformation getFavorite(){
        ArrayList<FavoriteInformation> fav = ratingDBController.getUsersFavorites(userModel.getUsername());

        for(FavoriteInformation favorite : fav){
            if( favorite.getRecipeId().equals(recipe.getId())){
                return favorite;
            }
        }
        return null;
    }
}
