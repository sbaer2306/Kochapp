package Clerks;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageResizeClerk extends Thread{

    public void run(){
    }
    public File resizeImage(File Imagefile){
        try {
            Image image =new Image(Imagefile.toURI().toString());
            double width=image.getWidth( );
            double height=image.getHeight( );
            if(width>=300.00){
                double faktor= 300/width;
                int newheight=(int)(faktor*height);
                BufferedImage originalImage = ImageIO.read(Imagefile);//change path to where file is located
                int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                BufferedImage resizeImageJpg = resizeImage(originalImage,type,300,newheight);
                Path tempFile = Files.createTempFile("temp",".jpg");
                Imagefile =  new File(String.valueOf(tempFile));
                ImageIO.write(resizeImageJpg, "jpg", Imagefile);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return Imagefile;
    }
    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }
}
