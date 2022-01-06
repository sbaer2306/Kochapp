package Datastructures;

public class UsersRecipeInformation {

    String recipeId;
    String recipeTitle;
    String uploadDatetime;

    public UsersRecipeInformation(String recipeId, String recipeTitle, String uploadDatetime) {
        this.recipeId = recipeId;
        this.recipeTitle = recipeTitle;
        this.uploadDatetime = uploadDatetime;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public String getUploadDatetime() {
        return uploadDatetime;
    }
}
