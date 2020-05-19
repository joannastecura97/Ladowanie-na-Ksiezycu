package engine.classes;

import java.io.*;
import java.util.ArrayList;

/**
 * Class used for score management. It provides method for score save, read and update.
 */
public class ScoreIOManager {

    /**
     * Method used for reading the scores data. It creates an ArrayList object containing String arrays with nickname and score.
     * @return <code>ArrayList</code> object containing String array with nickname and score.
     */
    public ArrayList<String[]> readScore(){

        ArrayList<String[]> scores = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File("scores2.txt")))) {
            String input;
            while ((input = br.readLine()) != null) {
                scores.add(input.split(","));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;

    }

    /**
     * Method used for updating the score list with a new record. It puts the record in an appropriate place
     * and deletes the last record from the score list.
     * @param newRecord String array containing nickname and score.
     */
    public void updateScore(String[] newRecord){
        ArrayList<String[]> scores = readScore();

        double newScore = Double.parseDouble(newRecord[1]);
        int newScoreIndex = -1;
        for(int i = 0; i < scores.size(); i++){
            if(newScore > Double.parseDouble(scores.get(i)[1])){
                newScoreIndex = i;
                break;
            }
        }

        scores.add(newScoreIndex, newRecord);//dodaje nowy
        scores.remove(scores.size()-1);//usuwam ostatni

        saveScore(scores);

    }

    /**
     * Method used for saving the scores data. It saves an ArrayList object containing String arrays with nicknames and scores.
     * @param scores <code>ArrayList</code> object containign scores and nicknames.
     */
    private void saveScore(ArrayList<String[]> scores){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(new File("scores2.txt")))){
            for(String[] s : scores) {
                bw.write(s[0] + "," + s[1]);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
