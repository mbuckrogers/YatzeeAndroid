package thelf.ch.yatzee.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import thelf.ch.yatzee.domain.score.ScoreSheet;
import thelf.ch.yatzee.domain.score.ScoreSlotStruct;

/**
 * Created by Admin on 9/19/2015.
 */
public class GameModel {

    private int noOfPlayers = 3;
    private int actualPlayer = 0;
    private String selectedLineId;


    private List<ScoreSheet> sheets = new ArrayList<>();

    private Random random = new Random(System.currentTimeMillis());



    public GameModel() {
        sheets.add(new ScoreSheet("P1"));
    }

    public List<ScoreSlotStruct>[] toScoreSlot(int[] roll) {
        List<ScoreSlotStruct>[] res = new List[noOfPlayers];
        for (int i = 0; i < noOfPlayers; i++) {
            if (i != actualPlayer) {
                res[i] = sheets.get(i).actual();
            } else {
                res[i] = sheets.get(i).whatIf(roll);
            }
        }
        return res;
    }

    public int[] rollDice() {
        int roll[] = new int[5];
        for (int i = 0; i < 5; i++) {
            roll[i] = random.nextInt(5) + 1;
        }
        return roll;
    }

    public int getActualPlayer() {
        return this.actualPlayer;
    }

    public void bookRoll(int[] roll) {
        ScoreSheet sheet = sheets.get(actualPlayer);
        sheet.selectSlot(selectedLineId, roll);
        switchToNextPlayer();
    }

    public boolean canSwitch() {
        return selectedLineId != null;
    }

    private void switchToNextPlayer() {
        actualPlayer++;
        actualPlayer = actualPlayer % noOfPlayers;
        selectedLineId = null;
    }

    public String getSelectedLineId() {
        return selectedLineId;
    }

    public void setSelectedLineId(String selectedLineId) {
        this.selectedLineId = selectedLineId;
    }
}
