package thelf.ch.yatzee.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 9/12/2015.
 */
public class ScoreSheetModel {

    private List<ScoreSheetLine> lines = new ArrayList<>();

    public List<ScoreSheetLine> getLines() {
        return lines;
    }

    public static class ScoreSheetLine {
        boolean isSelectable = false;
        String lineId;
        boolean isSelected = false;
        List<ScoreCell> scoreCells = new ArrayList<>();

        public ScoreSheetLine(String lineId) {
            this.lineId = lineId;
        }


        public void setIsSelectable(boolean isSelectable) {
            this.isSelectable = isSelectable;
        }

        public void addScoreCell(ScoreCell cell) {
            this.scoreCells.add(cell);
        }

        public boolean isSelectable() {
            return isSelectable;
        }

        public String getLineId() {
            return lineId;
        }


        public List<ScoreCell> getScoreCells() {
            return scoreCells;
        }
    }

    public static class ScoreCell {
        int score;
        boolean activePlayer;
        boolean selected = false;

        public ScoreCell(int score, boolean activePlayer, boolean selected) {
            this.score = score;
            this.activePlayer = activePlayer;
            this.selected = selected;
        }

        public boolean isSelected() {
            return selected;
        }

        public int getScore() {
            return score;
        }

        public boolean isActivePlayer() {
            return activePlayer;
        }
    }
}
