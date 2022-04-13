import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static volatile FileManager instance;
    private static Object lockobj = new Object();

    private final String FILE_USERS = "data/users.txt";

    private FileManager() {

    }

    /**
     * @param
     * @return
     */

    public static FileManager getInstance() {
        FileManager result = instance;
        if (result == null) {
            synchronized (lockobj) {
                result = instance;
                if (result == null) {
                    instance = result = new FileManager();
                }
            }
        }
        return result;
    }

    public synchronized boolean register(String user, String pass) {
        try {
            if (userexists(user)) {
                System.out.println("User with name '" + user + "' already exists.");
                return false;
            }

            try {
                FileWriter fw = new FileWriter(FILE_USERS, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw, true);
                out.println(user + "," + sha256(pass) + ",0,0,0,0,0");
                fw.close();
                bw.close();
                out.close();

            } catch (IOException e) {
                System.out.println("Error writing to file when registering '" + user + "'.");
                return false;
            }

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error with algorithm when registering '" + user + "'.");
            return false;
        }

        System.out.println("User '" + user + "' registered successfully.");
        return true;
    }

    public boolean login(String user, String pass) {
        boolean loggedin = false;
        try {
            List<String> fileContent = new ArrayList<>(
                    Files.readAllLines(file(FILE_USERS).toPath(), StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).startsWith(user)) {
                    String p = fileContent.get(i).split(",")[1];
                    if (sha256(pass).equals(p)) {
                        loggedin = true;
                    } else {
                        System.out.println("Wrong password.");
                    }
                    break;
                }
            }
            if (!loggedin) {
                System.out.println("User does not exist.");
            }
        } catch (Exception e) {
            System.out.println("There was an error when trying to login user '" + user + "'.");
        }
        return loggedin;
    }

    public synchronized void writestats(String user, String stats) {
        try {
            List<String> fileContent = new ArrayList<>(
                    Files.readAllLines(file(FILE_USERS).toPath(), StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).startsWith(user)) {
                    String line = fileContent.get(i);
                    fileContent.set(i, user + "," + line.split(",")[1] + "," + stats);
                    break;
                }
            }
            Files.write(file(FILE_USERS).toPath(), fileContent, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("There was an error writing stats for user '" + user + "'.");
        }
    }

    /**
     * wins
     * losses
     * cups made
     * cups missed
     * balls back
     */

    public String getstats(String user) {
        String stats = "";
        try {
            List<String> fileContent = new ArrayList<>(
                    Files.readAllLines(file(FILE_USERS).toPath(), StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).startsWith(user)) {
                    String line = fileContent.get(i);
                    int splice = line.indexOf(",", line.indexOf(",") + 1);
                    stats = line.substring(splice);
                }
            }
        } catch (Exception e) {
            System.out.println("There was an error reading stats for user '" + user + "'.");
        }
        return stats;
    }

    private boolean userexists(String user) {
        boolean exists = false;
        try {
            List<String> fileContent = new ArrayList<>(
                    Files.readAllLines(file(FILE_USERS).toPath(), StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).startsWith(user)) {
                    exists = true;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("There was an error seeing if user '" + user + "' exists.");
        }
        return exists;
    }

    private String sha256(String pass) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = md.digest(pass.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder(2 * bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    // need to create file if it doesnt exist
    public File file(String path) {
        return new File(path);
    }
}