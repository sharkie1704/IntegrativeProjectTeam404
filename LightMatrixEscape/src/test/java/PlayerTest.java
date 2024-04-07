
import controllers.Files;
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
    File progressFile = new File(getClass().getResource("data/progress.txt").getFile());

    public PlayerTest() {
    }

    @Test
    public void saveProgress() {
        String scoreData = "The player " + username + "'s score: " + score;
        String progressData = "The player " + username + " is at level: " + levelProgress;

        Files.saveToFile(scoreData, progressFile);
        Files.saveToFile("/n" + progressData, progressFile);
    }

    @Test
    //This method can access the saved data of the user based on username 
    //Still needs some work
    public void importProgress() throws IOException {
        String str = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(progressFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (line.startsWith("The player " + username + "'s score: ")) {
                        //Saving score information from the line
                        String scoreStr = line.substring(line.lastIndexOf(":") + 2);
                        score = Integer.parseInt(scoreStr);
                    } else if (line.startsWith("The player " + username + " is at level: ")) {
                        //Saving level progress from the line
                        String progressStr = line.substring(line.lastIndexOf(":") + 2);
                        levelProgress = Integer.parseInt(progressStr);
                    }
                }
            }
        }
    }
}
