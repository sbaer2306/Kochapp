package Datastructures;

public class FavoriteInformation {

    private String ownerId;
    private String recipeId;
    private String recipeTitle;
    private String addedDatetime;

    public FavoriteInformation(){}

    public FavoriteInformation(UserModel owner, Recipe recipe) {
        this.ownerId = owner.getUsername();
        this.recipeId = recipe.getId();
        this.recipeTitle = recipe.getTitle();
    }


    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public String getAddedDatetime() {
        return addedDatetime;
    }

    public void setAddedDatetime(String addedDatetime) {
        this.addedDatetime = addedDatetime;
    }
}
