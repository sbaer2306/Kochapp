package Datastructures;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.sql.Blob;
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

    public void setImageFile(File imageFile) {
        ImageFile = imageFile;
        try {
            this.image =new Image(ImageFile.toURI().toString());
            double width=image.getWidth( );
            double height=image.getHeight( );
            if(width>=300.00){
                double faktor= 300/width;
                int newheight=(int)(faktor*height);
                BufferedImage originalImage = ImageIO.read(ImageFile);//change path to where file is located
                int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                BufferedImage resizeImageJpg = resizeImage(originalImage, type,300, newheight);
                File tmpFile = new File("temp.jpg");
                ImageIO.write(resizeImageJpg, "jpg", tmpFile);
                this.ImageFile = tmpFile;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
    }
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
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

    //Für die Rezepterstellung
    public void setImage(File file){
        setImageFile(file); //FileChooser liefert ein File zurück
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
