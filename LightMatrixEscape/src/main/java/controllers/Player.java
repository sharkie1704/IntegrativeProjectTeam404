package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Ishrak Mellah
 */
@Getter
@Setter

public class Player {

    private String username;
    private int levelProgress;
    private int score;
    String str = null;
    String fileName = "player";
//    File playerFile = new File("C:\\Users\\Alaa Mellah\\Documents\\GitHub\\IntegrativeProjectTeam404\\LightMatrixEscape\\src\\main\\resources\\data\\player.txt");

     File playerFile = new File(findFilePath(fileName));
    public Player() {

    }

    public String findFilePath(String fileName) {
        File file = new File(fileName);
        String path = file.getAbsolutePath();
        return path;

    }

    public Player(String username, int levelProgress, int score) {
        this.username = username;
        this.levelProgress = levelProgress;
        this.score = score;
    }

    //This method saves the progress of the user in a txt file
    public void saveProgress() {
       

        String scoreData = "Username: " + username;
        //String progressData = "The player " + username + " is at level: " + levelProgress;

        Files.saveToFile(scoreData, playerFile);
        System.out.println(playerFile);
        //Files.saveToFile("/n" + progressData, playerFile);
    }

    //This method can access the saved data of the user based on username 
    //Still needs some work
    public String importProgress() throws IOException {
        //String str = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(playerFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (line.startsWith("Username:")) {
                        //Saving score information from the line
                        String username = line.substring(line.lastIndexOf(" "), line.length());
                        return username;
//                        score = Integer.parseInt(scoreStr);
//                    } else if (line.startsWith("The player " + username + " is at level: ")) {
//                        //Saving level progress from the line
//                        String progressStr = line.substring(line.lastIndexOf(":") + 2);
//                        levelProgress = Integer.parseInt(progressStr);
//                    }
                    }
                } else {
                    return null;
                }
            }
        }
        return null;
    }
}
