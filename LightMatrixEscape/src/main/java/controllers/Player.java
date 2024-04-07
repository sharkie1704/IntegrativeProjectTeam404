package controllers;
import java.io.File;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {

    private String username;
    private int levelProgress;
    private int score;
    File progressFile = new File("/data/progress.txt");

    public Player(String username, int levelProgress, int score) {
        this.username = username;
        this.levelProgress = levelProgress;
        this.score = score;
    }

    //This method saves the progress of the player to access it when they log back into the game
    public void saveProgress() {
        String scoreData = "The player " + username + "'s score: " + score;
        String progressData = "The player " + username + " is at level: " + levelProgress;
        
        String playerData = scoreData + "\n" + progressData;
        Files.saveToFile(playerData, progressFile);
    }
}
