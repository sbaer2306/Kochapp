package DBController;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBInsertController extends DBConnectionController{
    public DBInsertController(){
        super();
    }
    public void InsertRecipe(int id, Image image, int portions) throws SQLException, FileNotFoundException {
        //TODO Anpassung mit übergebenen Parametern
        String sql = "INSERT INTO recipes (title,image,portions,ingredients,description,duration,ingredients_cost,author_uid,difficulty_did,recipe_rid) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1,Integer.toString(id));
        //TODO Image in Binarystream konvertieren
        FileInputStream fin = new FileInputStream("lachs-quiche.jpg");
        pstmt.setBinaryStream(2, fin);
        pstmt.setString(3,"1");
        pstmt.setString(4,
                "200 g Weizenmehl " +
                        "100 g Butter " +
                        "1 Ei(er) " +
                        "2 EL Milch " +
                        "1 Prise(n) Salz " +
                        "300 g Räucherlachs, in dünnen Scheiben " +
                        "750 g Porree " +
                        "2 EL Butter " +
                        "4 Ei(er) " +
                        "6 EL Crème fraîche " +
                        "½ TL Salz und Pfeffer, weißer " +
                        "1 Prise(n) Muskat " +
                        "1 TL Zitronensaft " +
                        "Butter, für die Form ");
        pstmt.setString(5, "Für eine 26er Springform oder Tarteform. " +
                "Aus Mehl, Butter, dem Ei, der Milch und dem Salz einen geschmeidigen Teig kneten. Den Teig zu einer Kugel formen, in Klarsichtfolie wickeln und ca. 30 min im Kühlschrank ruhen lassen." +
                "Den Lauch putzen und in breite Ringe schneiden, dann ca. 6 Minuten in der erhitzten Butter dünsten, abkühlen lassen. " +
                "Den Lachs in feine Streifen schneiden. " +
                "Die Eier mit der Crème fraîche verquirlen, Lauch und Lachs untermengen, mit Salz, Pfeffer, Muskat und Zitronensaft würzen. " +
                "Die Form mit dem Teig auskleiden und die Gemüsemischung einfüllen. Den Backofen auf 200° vorheizen und die Quiche 30 - 35 Minuten backen.");
        pstmt.setString(6,"30");
        pstmt.setString(7,"10.99");
        pstmt.setString(8,"1");
        pstmt.setString(9,"Leicht");
        pstmt.setString(10,"2");
        pstmt.execute();
        //TODO Einträge in Kategorien
    }
}
