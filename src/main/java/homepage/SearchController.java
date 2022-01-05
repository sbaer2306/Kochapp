package homepage;

import DBController.DBSearchController;
import Datastructures.Recipe;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchController {

    private DBSearchController dbSearchController;
    private ArrayList<Recipe> recipes;

    public SearchController() {
        this.dbSearchController = new DBSearchController();
    }

    public ArrayList<Recipe> getKeywordSearchResult(String buzzword) {
        recipes = new ArrayList<Recipe>();
        try {
            recipes = dbSearchController.searchQuery(buzzword, 100);
        }catch(SQLException | IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public ArrayList<Recipe> getExtendedSearchResult(String buzzword, String price, int duration,String difficulty, ArrayList<String> categories) {
        recipes = new ArrayList<Recipe>();
        try {
            recipes = dbSearchController.extendedSearchQuery(buzzword, price, duration, difficulty, categories);
        }catch(SQLException | IOException | NullPointerException e) {
            e.printStackTrace();
        }

        return recipes;
    }
}