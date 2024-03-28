
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class mainTest {

    @Test
    public static void main(String[] args) throws IOException {

        File progressFile = new File("C:\\Users\\2279307\\Desktop\\progress.txt");
        String username = null;
        int levelProgress = 0;
        int score = 0;
        String str = null;
        importProgress(progressFile, username, levelProgress, score, str);
        System.out.println("level: " + levelProgress);
    }

    @Test
    public static void importProgress(File progressFile, String username, int levelProgress, int score, String str) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(progressFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (line.startsWith("The player " + username + " has a score of ")) {
                        str = line.substring(line.indexOf(":"), line.length() - 1);
                        levelProgress = Integer.parseInt(str);
                        System.out.println(levelProgress);
                    }
                }
            }
        }
    }
}
