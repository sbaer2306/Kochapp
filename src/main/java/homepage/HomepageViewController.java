package homepage;

import DBController.RatingDBController;
import Datastructures.FavoriteInformation;
import Datastructures.Recipe;
import RecipeCreation.CreationController;
import Session.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import login.Login;
import registration.Registration;
import FavoriteSection.FavoriteViewController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomepageViewController {

    private SearchController searchController;
    private RatingDBController ratingDBController;
    private ArrayList<Recipe> displayedRecipe;
    private UserSession user;
    private ArrayList<FavoriteInformation> favorites;

    @FXML
    private TextField keywordField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField durationField;
    @FXML
    private MenuButton categoriesMenuButton;
    @FXML
    private HBox difficultyHBox;
    @FXML
    private VBox recipesContainer;
    @FXML
    private MenuButton loginButton;
    @FXML
    private MenuButton logoutButton;
    @FXML
    private Label recipeCounter;
    @FXML
    private AnchorPane favoritViewWithoutLogin;
    @FXML
    private ScrollPane favoritViewLogin;
    @FXML
    private VBox favoriteContainer;

    public HomepageViewController() {
        this.searchController = new SearchController();
        this.user = new UserSession();
        this.ratingDBController = new RatingDBController();
    }

    public void initialize() {
        displayMostLikedRecipes();
    }

    //Lädt die 5 Meistbewertesten Rezepte aus der Datenbank
    public void displayMostLikedRecipes(){
        TopFiveController topFiveController = new TopFiveController();
        displayedRecipe = topFiveController.getMostLikedRecipes();

        for(int i = 0; i < displayedRecipe.size(); i++) {
            displayPreview(i);
        }

        recipeCounter.setText("Unsere Top 5 Rezepte");
        recipeCounter.getStyleClass().add("h1");
    }

    //Erstellt eine Ansicht mit den Top 5 Rezepten in der Startseite
    private void displayPreview(int count){
        FXMLLoader previewElement = new FXMLLoader(getClass().getResource("/homepage/previewElement.fxml"));
        HBox  previewElementContainer = new HBox();

        try{
            previewElementContainer = (HBox) previewElement.load();
        }catch(IOException e){
            e.fillInStackTrace();
        }

        recipeCounter.getStyleClass().add("h2");

        PreviewViewController pvc = previewElement.getController();
        pvc.setRecipe(displayedRecipe.get(count));
        recipesContainer.getChildren().add(pvc.assemblePreview(previewElementContainer, count));
    }

    //Die Previewelemente in der Ansicht werden gelöscht
    private void clearPreview(){
        recipesContainer.getChildren().remove(1, recipesContainer.getChildren().size());

    }

    private void clearFavorite(){
        favoriteContainer.getChildren().remove(1, favoriteContainer.getChildren().size());
    }

    public void loadHomePage(){
        clearPreview();
        clearFavorite();
        displayMostLikedRecipes();
        favoriteView();
    }

    //Es wird nach den Rezepten gesucht, welche in der Suchleiste eingegeben wurden
    public void displayKeywordSearchResults() {
        String buzzword = keywordField.getText();
        displayedRecipe = searchController.getKeywordSearchResult(buzzword);

        clearPreview();

        switch(displayedRecipe.size()){
            case 0: recipeCounter.setText("Es wurden keine Rezepte gefunden");
                break;
            case 1: recipeCounter.setText("Es wurde " + displayedRecipe.size() + " Rezept gefunden");
                break;
            default: recipeCounter.setText("Es wurden " + displayedRecipe.size() + " Rezepte gefunden");
                break;
        }

        for(int i = 0; i < displayedRecipe.size(); i++) {
            displayPreview(i);
        }
    }

    //Es wird nach erweiternden Eigenschaften des Rezepts gesucht
    public void displayExtendedSearchResults() {
        String buzzword = keywordField.getText();

        String price = priceField.getText();
        // Set Default Value of Price Field..
        if(priceField.getText() == null || priceField.getText().trim().isEmpty()) {
            price = "0";
        }

        String duration = durationField.getText();
        // Set Default Value of Duration..
        if(durationField.getText() == null || durationField.getText().trim().isEmpty()) {
            duration = "0";
        }

        ArrayList<String> categories = new ArrayList<String>();
        for(int i = 0; i < categoriesMenuButton.getItems().size(); i++) {
            CustomMenuItem customMenuItem = (CustomMenuItem) categoriesMenuButton.getItems().get(i);
            CheckBox checkBox = (CheckBox) customMenuItem.getContent();
            if(checkBox.isSelected()) {
                categories.add(checkBox.getText());
            }
        }
        // Set Default Value of Categories
        if (categories.size() == 0) {
            categories.add("");
        }

        String difficulty = "";
        for(int i = 0; i < difficultyHBox.getChildren().size(); i++) {
            RadioButton radioButton = (RadioButton) difficultyHBox.getChildren().get(i);
            if (radioButton.isSelected()) {
                difficulty = radioButton.getText();
            }
        }

        displayedRecipe = searchController.getExtendedSearchResult(buzzword, price, Integer.parseInt(duration), difficulty, categories);

        switch(displayedRecipe.size()){
            case 0: recipeCounter.setText("Es wurden keine Rezepte gefunden");
                break;
            case 1: recipeCounter.setText("Es wurde " + displayedRecipe.size() + " Rezept gefunden");
                break;
            default: recipeCounter.setText("Es wurden " + displayedRecipe.size() + " Rezepte gefunden");
                break;
        }

        clearPreview();
        for(int i = 0; i < displayedRecipe.size(); i++) {
            displayPreview(i);
        }
    }

    //Verbindet einen User
    public void loginButtonPress() throws SQLException, IOException{
        new Login();

        if(user.sessionExists()) {
            loginButton.setVisible(false);
            logoutButton.setVisible(true);
            favoriteView();
        }
    }

    //Ein User wird registriert
    public void registrationButtonPress(){
        new Registration();
    }

    //Öffnet ein neues Fenster um ein Rezept zur Datenbank einzufügen
    public void createRecipe(){
        new CreationController();
    }

    //Verbindung mit dem User wird getrennt
    public void logoutButtonPress(){
        user.logoutSession();
        loginButton.setVisible(true);
        logoutButton.setVisible(false);
        changeFavoriteView();
    }

    //Wenn ein User eingeloggt ist, wird die Favoritenliste angezeigt
    public void favoriteView(){

        if(user.sessionExists()){
            clearFavorite();

            favoritViewWithoutLogin.setVisible(false);
            favoritViewLogin.setVisible(true);

            favorites = ratingDBController.getUsersFavorites(user.getUserSession().getUsername());

            if(favorites.size() == 0){
                Label lbl = new Label("Sie haben noch keine Liebilingsrezepte...");
                lbl.setPadding(new Insets(100,0,0,0));
                favoriteContainer.getChildren().add(lbl);
            }

            for(int i = 0; i < favorites.size(); i++){

                FXMLLoader fxmlElement = new FXMLLoader(getClass().getResource("/FavoriteSection/favoriteElement.fxml"));
                HBox  favoriteElement = new HBox();

                try{
                    favoriteElement = (HBox) fxmlElement.load();
                }catch(IOException e){
                    e.fillInStackTrace();
                }

                if(i > 6){
                    favoriteContainer.setMaxWidth(230);
                }

                FavoriteViewController con = fxmlElement.getController();
                con.setFavoriteInformation(favorites.get(i));
                con.assembleFavorite(favoriteElement);
                favoriteContainer.getChildren().add(favoriteElement);
        }

        }
    }

    //Überprüft die Anzahl der favoristen Rezepte, wenn ein User angemeldet ist. Falls diese nicht übereinstimmt wird die Favoriten-Liste neu bestimmt
    public void checkFavLength(){
        if(user.sessionExists()){
            if(favorites.size() != ratingDBController.getUsersFavorites(user.getUserSession().getUsername()).size()){
                favoriteView();
            }
        }

    }

    //Beim Logout eines Users, wird die Favoritenliste nicht mehr angezeigt.
    public void changeFavoriteView(){
        favoritViewWithoutLogin.setVisible(true);
        favoritViewLogin.setVisible(false);
        clearFavorite();
    }


}