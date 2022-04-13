import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CupPongServer {

    public static void main(String[] args) {

        try {

            CPSS ss = new CPSS(4444);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}