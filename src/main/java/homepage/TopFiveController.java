package homepage;

import DBController.DBSearchController;
import Datastructures.Recipe;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class TopFiveController {

    private DBSearchController dbSearchController;

    public TopFiveController() {
        this.dbSearchController = new DBSearchController();
    }

    public ArrayList<Recipe> getMostLikedRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            recipes =  dbSearchController.getTopFiveRecipes();
        } catch (SQLException | IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return recipes;
    }
}
