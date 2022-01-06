module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires validatorfx;
    requires java.sql;
    requires javafx.swing;

    opens main to javafx.fxml;
    exports main;

    opens homepage to javafx.fxml;
    exports homepage;

    opens login to javafx.fxml;
    exports login;

    opens registration to javafx.fxml;
    exports registration;

    opens RecipeCreation to javafx.fxml;
    exports RecipeCreation;

    opens Datastructures to java.sql;
    exports Datastructures;

    opens recipeView to javafx.fxml;
    exports recipeView;

    opens CommentSection to javafx.fxml;
    exports CommentSection;

    exports FavoriteSection;
    opens FavoriteSection to javafx.fxml;

    exports UserRecipeView;
    opens UserRecipeView to javafx.fxml;
}