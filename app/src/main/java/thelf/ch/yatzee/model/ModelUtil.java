package thelf.ch.yatzee.model;

import java.util.ArrayList;
import java.util.List;

import thelf.ch.yatzee.domain.score.ScoreSlotStruct;

/**
 * Created by Admin on 9/12/2015.
 */
public class ModelUtil {

    private static final String[] UPPER = { "1", "2", "3", "4", "5", "6"};
    private static final String[] LOWER = { "DP", "VP", "FH", "SS", "BS", "YA", "CH"};


    private static ScoreSlotStruct findSlot(String lineId, List<ScoreSlotStruct> slots) {
        for (ScoreSlotStruct slot : slots) {
            if(lineId.equals(slot.getId())) {
                return slot;
            }
        }
        return null;
    }


    public static ScoreSheetModel create(int activePosition, List<ScoreSlotStruct>...slots) {
        ScoreSheetModel aModel = new ScoreSheetModel();
        aModel.getLines().addAll(createLinesForIds(activePosition, UPPER, slots));
        aModel.getLines().addAll(createLinesForIds(activePosition, LOWER, slots));
        return aModel;
    }

    private static List<ScoreSheetModel.ScoreSheetLine> createLinesForIds(int activePosition, String[] ids, List<ScoreSlotStruct>...slots) {
        List<ScoreSheetModel.ScoreSheetLine> res = new ArrayList<>();
        for(String lineId: ids) {
            ScoreSheetModel.ScoreSheetLine line = new ScoreSheetModel.ScoreSheetLine(lineId);

            for(int i = 0; i < slots.length; i++) {
                boolean activePlayer = activePosition == i ? true : false;

                List<ScoreSlotStruct> playerSlot =  slots[i];
                ScoreSlotStruct slot = findSlot(lineId, playerSlot);
                line.addScoreCell(new ScoreSheetModel.ScoreCell(slot.getScore(), activePlayer, slot.isSelected()));
                if(activePlayer) {
                    line.setIsSelectable(!slot.isSelected());
                }
            }
            res.add(line);
        }
        return res;
    }
}
