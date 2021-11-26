package Clerks;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingClerk {
    //Ohne salt... aber fürs erste besser als nichts
    public String hash(String input)  {

        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);

        }
        byte[] messageDigest = md.digest(input.getBytes()); //hash erstellen

        BigInteger bi = new BigInteger(1,messageDigest); //signum = 1 --> postitive Nummer

        return bi.toString(16); //to String in Hexcode
    }
}
