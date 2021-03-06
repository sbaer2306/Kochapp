package homepage;

import DBController.RatingDBController;
import Datastructures.FavoriteInformation;
import Datastructures.Recipe;
import RecipeCreation.CreationController;
import Session.UserSession;
import UserRecipeView.UserRecipeListController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
    private Label recipeCounter;

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
    private AnchorPane favoriteViewWithoutLogin;
    @FXML
    private ScrollPane favoriteViewLogin;
    @FXML
    private VBox favoriteContainer;
    @FXML
    private Label titleFavorite;

    public HomepageViewController() {
        this.searchController = new SearchController();
        this.user = new UserSession();
        this.ratingDBController = new RatingDBController();
        this.recipeCounter = new Label();
        recipeCounter.setPrefWidth(733);
    }

    public void initialize() {
        displayRecipeOfWeek();
        displayMostLikedRecipes();
        priceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            priceField.setText(newValue.replaceAll("[^\\d]", ""));
        });
        durationField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            durationField.setText(newValue.replaceAll("[^\\d]", ""));
        });
    }

    public void clearExtendedSearch() {
        keywordField.clear();
        priceField.clear();
        durationField.clear();
        for(int i = 0; i < categoriesMenuButton.getItems().size(); i++) {
            CustomMenuItem custom = (CustomMenuItem) categoriesMenuButton.getItems().get(i);
            CheckBox checkBox = (CheckBox) custom.getContent();
            if(checkBox.isSelected()) checkBox.setSelected(false);
        }
        for(int i = 0; i < difficultyHBox.getChildren().size(); i++) {
            RadioButton r = (RadioButton) difficultyHBox.getChildren().get(i);
            if(r.isSelected()) r.setSelected(false);
        }
    }

    //Zeigt das Rezept der Woche an
    public void displayRecipeOfWeek(){
        FXMLLoader recipeOfWeekFXML = new FXMLLoader(getClass().getResource("/homepage/recipeOfWeek.fxml"));
        AnchorPane  recipeOfWeek = new AnchorPane();

        try{
            recipeOfWeek = (AnchorPane) recipeOfWeekFXML.load();
        }catch(IOException e){
            e.fillInStackTrace();
        }

        RecipeOfWeekViewController rowCon = recipeOfWeekFXML.getController();
        recipesContainer.getChildren().add(rowCon.assembleRecipe(recipeOfWeek));
    }

    //L??dt die 5 Meistbewertesten Rezepte aus der Datenbank
    public void displayMostLikedRecipes(){
        TopFiveController topFiveController = new TopFiveController();
        displayedRecipe = topFiveController.getMostLikedRecipes();

        recipeCounter.setText("Unsere Top 5 Rezepte");
        recipeCounter.getStyleClass().add("h1");
        recipesContainer.getChildren().add(recipeCounter);

        for(int i = 0; i < displayedRecipe.size(); i++) {
            displayPreview(i);
        }
    }

    //Erstellt ein Preview Element in der Ansicht
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

    //Die Previewelemente in der Ansicht werden gel??scht
    private void clearPreview(){
        recipesContainer.getChildren().clear();
    }

    private void clearFavorite(){
        favoriteContainer.getChildren().remove(2, favoriteContainer.getChildren().size());
    }

    public void loadHomePage(){
        clearPreview();
        clearFavorite();
        displayRecipeOfWeek();
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
        recipesContainer.getChildren().add(recipeCounter);

        for(int i = 0; i < displayedRecipe.size(); i++) {
            displayPreview(i);
        }
        keywordField.clear();
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
        recipesContainer.getChildren().add(recipeCounter);
        for(int i = 0; i < displayedRecipe.size(); i++) {
            displayPreview(i);
        }
        clearExtendedSearch();
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

    //??ffnet ein neues Fenster um ein Rezept zur Datenbank einzuf??gen
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

            favoriteViewWithoutLogin.setVisible(false);
            favoriteViewLogin.setVisible(true);

            favorites = ratingDBController.getUsersFavorites(user.getUserSession().getUsername());

            if(favorites.size() == 0){
                favoriteContainer.getChildren().get(1).setVisible(false);
                Label lbl = new Label("Sie haben noch keine Liebilingsrezepte...");
                lbl.setPadding(new Insets(100,0,0,0));
                favoriteContainer.getChildren().add(lbl);
            }else{
                favoriteContainer.getChildren().get(1).setVisible(true);
            }

            for(int i = 0; i < favorites.size(); i++){

                FXMLLoader fxmlElement = new FXMLLoader(getClass().getResource("/FavoriteSection/favoriteElement.fxml"));
                HBox  favoriteElement = new HBox();

                try{
                    favoriteElement = (HBox) fxmlElement.load();
                }catch(IOException e){
                    e.fillInStackTrace();
                }

                if(i > 9){
                    favoriteContainer.setMaxWidth(230);
                    titleFavorite.setPadding(new Insets(0, 55,0,0));
                }else{
                    favoriteContainer.setMaxWidth(245);
                    titleFavorite.setPadding(new Insets(0, 70,0,0));
                }

                FavoriteViewController con = fxmlElement.getController();
                con.setFavoriteInformation(favorites.get(i));
                con.assembleFavorite(favoriteElement);
                favoriteContainer.getChildren().add(favoriteElement);
            }

        }
    }

    //??berpr??ft die Anzahl der favoristen Rezepte, wenn ein User angemeldet ist. Falls diese nicht ??bereinstimmt wird die Favoriten-Liste neu bestimmt
    public void checkFavLength(){
        if(user.sessionExists()){
            if(favorites.size() != ratingDBController.getUsersFavorites(user.getUserSession().getUsername()).size()){
                favoriteView();
            }
        }

    }

    //Beim Logout eines Users, wird die Favoritenliste nicht mehr angezeigt.
    public void changeFavoriteView(){
        favoriteViewWithoutLogin.setVisible(true);
        favoriteViewLogin.setVisible(false);
        clearFavorite();
    }

    //Zeigt die eigenen Rezepte des eingeloggten Nutzers an
    public void showUserRecipes(ActionEvent actionEvent) {
        new UserRecipeListController();
    }
}