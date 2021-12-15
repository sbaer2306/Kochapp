package Datastructures;

public class RecipeComment {

    private UserModel author;
    private Recipe recipe;
    private String text;
    String datetime;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public RecipeComment() {
    }

    @Override
    public String toString() {
        return "RecipeComment: \n" +
                "author: " + author.getUsername() +
                "\n recipe: " + recipe.getTitle() +
                "\n text=" + text + '\'' +
                "\n "+datetime;
    }

    public RecipeComment(UserModel author, Recipe recipe, String text, String datetime) {
        this.author = author;
        this.recipe = recipe;
        this.text = text;
        this.datetime= datetime;
    }

    //für holen aus der DB (alle Relevanten Informationen um Kommentar zu löschen)
    public RecipeComment(String username_uid, String recipe_rid, String text, String datetime) {
        this.author= new UserModel();
        author.setUsername(username_uid);

        this.recipe= new Recipe();
        recipe.setId(recipe_rid);

        this.text= text;
        this.datetime= datetime;
    }

    public UserModel getAuthor() {
        return author;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
