
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

@Getter
@Setter

public class PlayerTest {

    private String username;
    private int levelProgress;
    private int score;
    String str = null;
    File progressFile = new File("player/progress.txt");

    public PlayerTest() {
    }

    @Test
    public void saveProgress() {
        String scoreData = "The player " + username + " has a score of " + score;
        String progressData = "The player " + username + " is at level " + levelProgress;

        Files.saveToFile(scoreData, progressFile);
        Files.saveToFile("/n" + progressData, progressFile);
    }

    public int importProgress() throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(progressFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (line.startsWith("The player " + username + " has a score of ")) {
                        str = line.substring(line.length() - 2);
                        levelProgress = Integer.parseInt(str);
                        System.out.println(levelProgress);
                    }
                }
            }

        }
        return levelProgress;
    }
}
