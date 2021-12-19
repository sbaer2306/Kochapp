package Datastructures;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FavoriteInformation {

    private String ownerId;
    private String recipeId;
    private String recipeTitle;
    private String addedDatetime;

    @Override
    public String toString() {
        return "FavoriteInformation{" +
                "ownerId='" + ownerId + '\'' +
                ", recipeId='" + recipeId + '\'' +
                ", recipeTitle='" + recipeTitle + '\'' +
                ", addedDatetime='" + addedDatetime + '\'' +
                '}';
    }

    public FavoriteInformation(){}

    public FavoriteInformation(UserModel owner, Recipe recipe) {
        this.ownerId = owner.getUsername();
        this.recipeId = recipe.getId();
        this.recipeTitle = recipe.getTitle();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        this.addedDatetime= formatter.format(date);
    }

    public FavoriteInformation(String ownerId, String recipeId, String recipeTitle) {
        this.ownerId = ownerId;
        this.recipeId = recipeId;
        this.recipeTitle = recipeTitle;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        this.addedDatetime= formatter.format(date);
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
