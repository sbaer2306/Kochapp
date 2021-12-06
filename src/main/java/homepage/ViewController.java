package homepage;

import DBController.DBSearchController;
import Datastructures.Recipe;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewController {

    protected static ArrayList<Recipe> recipes;

    @FXML
    private BorderPane bp;


    public ViewController(){

    }

    public ViewController(BorderPane bp){
        this.bp = bp;
    }

    public void initialize(){
        if(bp != null){
            BorderPane searchBar = new BorderPane();

            try{
                searchBar = (BorderPane) FXMLLoader.load(getClass().getResource("/homepage/SearchBar.fxml"));
            }catch(IOException e){
                e.fillInStackTrace();
            }

            bp.setTop(searchBar);
            displayMostLikedRecipes();
        }
    }

    public void displayMostLikedRecipes(){
        recipes = getTopFiveRecipesFromDB();

        VBox container = getHomePageContainer(recipes.size());

        Label mostLikedRecipes = (Label) container.getChildren().get(0);

        mostLikedRecipes.setText("Unsere Top 5 Rezepte");
        mostLikedRecipes.getStyleClass().add("h1");

        for(int i = 0; i < recipes.size(); i++){
            container.getChildren().add(addElementsToHBoxContainer(i));
        }
    }

    public void assemblePreview(ArrayList<Recipe> recipeList){
        recipes = new ArrayList<Recipe>();
        recipes.addAll(recipeList);

        VBox container = getHomePageContainer(recipeList.size());

        container.getChildren().remove(1, container.getChildren().size());

        Label RecipeNumbers = (Label) container.getChildren().get(0);

        switch(recipeList.size()){
            case 0: RecipeNumbers.setText("Es wurden keine Rezepte gefunden");
                break;
            case 1: RecipeNumbers.setText("Es wurde " + recipeList.size() + " Rezept gefunden");
                break;
            default: RecipeNumbers.setText("Es wurden " + recipeList.size() + " Rezepte gefunden");
                break;
        }
        RecipeNumbers.getStyleClass().add("h2");

        for(int i = 0; i < recipeList.size(); i++){
            container.getChildren().add(addElementsToHBoxContainer(i));
        }

    }

    private ArrayList<Recipe> getTopFiveRecipesFromDB(){
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            DBSearchController dbSearchController = new DBSearchController();
            recipes =  dbSearchController.getTopFiveRecipes();
        } catch (SQLException | IOException | NullPointerException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    private HBox loadRecipeContainer(){
        HBox recipeContainer = new HBox();

        try{
            recipeContainer = (HBox) FXMLLoader.load(getClass().getResource("/homepage/previewElement.fxml"));
        }catch(IOException e){
            e.fillInStackTrace();
        }

        return recipeContainer;
    }

    private void setHBoxStyle(HBox hbox, int counter){

        if (counter % 2 == 0) {
            hbox.getStyleClass().add("colorBoxes1");
        } else {
            hbox.getStyleClass().add("colorBoxes2");
        }
    }

    private HBox addElementsToHBoxContainer(int count) {
        HBox recipeContainer = loadRecipeContainer();

        setHBoxStyle(recipeContainer, count);

        // Aufbaue des Images
        Button imageContainer = (Button) recipeContainer.getChildren().get(0);
        ImageView view = (ImageView) imageContainer.getGraphic();

        Rectangle clip = new Rectangle(
                view.getFitWidth(), view.getFitHeight()
        );
        view.setImage(recipes.get(count).getImage());

        clip.setArcWidth(30);
        clip.setArcHeight(30);
        view.setClip(clip);

        // Container für die Attribute des Rezepts
        AnchorPane attributeContainer = (AnchorPane) recipeContainer.getChildren().get(1);

        // Aufbau des Titels
        Label title = (Label) attributeContainer.getChildren().get(0);
        title.setText(recipes.get(count).getTitle());

        // Aufbau der Likes
        Label likes = (Label) attributeContainer.getChildren().get(1);
        likes.setText(recipes.get(count).getLikes());

        // Aufbau der Dauer
        Label duration = (Label) attributeContainer.getChildren().get(3);
        duration.setText(recipes.get(count ).getDuration());

        // Aufbau der Schwierigkeit
        Label difficulty = (Label) attributeContainer.getChildren().get(5);
        difficulty.setText(recipes.get(count).getDifficulty());

        // Aufbau der Kosten
        Label price = (Label) attributeContainer.getChildren().get(7);
        price.setText(recipes.get(count).getIngredientsCost());

        return recipeContainer;
    }

    private VBox getHomePageContainer(int recipesSize){
        ScrollPane sp = (ScrollPane) bp.getCenter();
        VBox container = (VBox) sp.getContent();

        if(recipesSize <= 1){
            container.setMinWidth(733);
        }

        return container;
    }

    @FXML
    public void loadRecipe(Event event){
        Node node = (Node) event.getSource();
        Recipe recipe;

        if(node.getClass().toString().equals("class javafx.scene.control.Button")){
            Button btn = (Button) node;
            recipe = checkRecipesImage((ImageView) btn.getGraphic());
        }else{
            Label title = (Label) node;
            recipe = checkRecipesTitle(title.getText());
        }

        Stage stage = new Stage();
        ScrollPane root = new ScrollPane();

        try{
            root = (ScrollPane) FXMLLoader.load(getClass().getResource("/recipeWindow/recipeView.fxml"));
        }catch(IOException e){
            e.fillInStackTrace();
        }

        Scene scene = new Scene(root, 1000, 600, Color.WHITE);

        stage.setTitle(recipe.getTitle());
        stage.setScene(scene);

        showRecipe((AnchorPane) root.getContent(),recipe);
        stage.show();
    }

    private Recipe checkRecipesImage(ImageView img){

        for(int i = 0 ; i < recipes.size(); i++){
            if(recipes.get(i).getImage().toString().equals(img.getImage().toString())){
                return recipes.get(i);
            }
        }
        return null;
    }

    private Recipe checkRecipesTitle(String title){

        for(int i = 0 ; i < recipes.size(); i++){
            if(recipes.get(i).getTitle().equals(title)){
                return recipes.get(i);
            }
        }
        return null;
    }

    private void showRecipe(AnchorPane root, Recipe recipe){
        ImageView img = (ImageView) root.getChildren().get(0);
        img.setImage(recipe.getImage());

        Label title = (Label) root.getChildren().get(1);
        title.setText(recipe.getTitle());

        TextArea area = (TextArea) root.getChildren().get(2);
        area.setText(recipe.getDescription());
    }
}