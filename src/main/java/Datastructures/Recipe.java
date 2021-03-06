package Datastructures;

import Clerks.ImageResizeClerk;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

public class Recipe {
    private String id;
    private String title;
    private Image image;
    private String portions;
    private String ingredients;
    private String description;
    private String duration;
    private String ingredientsCost;
    private String likes;
    private String dislikes;
    private String creationTime;
    private String difficulty;
    private String author;
    private ArrayList<String> categories;

    private File ImageFile;

    public File getImageFile() {
        return ImageFile;
    }

    public void setImageFile(File imageFile) throws InterruptedException {
        ImageResizeClerk imageResizeClerk = new ImageResizeClerk();
        imageResizeClerk.start();
        this.ImageFile=imageResizeClerk.resizeImage(imageFile);
        imageResizeClerk.join();
    }

    @Override
    public String toString() {
        String listcategories = "";
        if (categories!=null){
            for (String s : categories)
            {
                listcategories += s + "\t";
            }
        }
        return "RecipeModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", portions='" + portions + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", description='" + description + '\'' +
                ", duration='" + duration + '\'' +
                ", ingredientsCost='" + ingredientsCost + '\'' +
                ", likes='" + likes + '\'' +
                ", dislikes='" + dislikes + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", author='" + author + '\'' +
                ", kategorien='" + listcategories + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Blob blob) throws IOException, SQLException {
        InputStream is = blob.getBinaryStream();
        BufferedImage bufferedImage= ImageIO.read(is);
        this.image = SwingFXUtils.toFXImage(bufferedImage, null);
    }

    //F??r die Rezepterstellung
    public void setImage(File file) throws InterruptedException {
        setImageFile(file); //FileChooser liefert ein File zur??ck
        this.image= new Image(file.toURI().toString());
    }

    public String getPortions() {
        return portions;
    }

    public void setPortions(String portions) {
        this.portions = portions;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIngredientsCost() {
        return ingredientsCost;
    }

    public void setIngredientsCost(String ingredientsCost) {
        this.ingredientsCost = ingredientsCost;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDislikes() {
        return dislikes;
    }

    public void setDislikes(String dislikes) {
        this.dislikes = dislikes;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }
}
