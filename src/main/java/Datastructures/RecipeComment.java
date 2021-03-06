package Datastructures;

public class RecipeComment {

    private String commentID;
    private String author_uid;
    private String recipe_rid;
    private String text;
    private String datetime;

    public RecipeComment(){}

    public RecipeComment(UserModel user, Recipe recipe, String text) {
        this.author_uid = user.getUsername();
        this.recipe_rid = recipe.getId();
        this.text = text;
    }


    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getAuthor_uid() {
        return author_uid;
    }

    public void setAuthor_uid(String author_uid) {
        this.author_uid = author_uid;
    }

    public String getRecipe_rid() {
        return recipe_rid;
    }

    public void setRecipe_rid(String recipe_rid) {
        this.recipe_rid = recipe_rid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
