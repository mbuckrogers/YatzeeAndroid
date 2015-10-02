package thelf.ch.yatzee.model;

import java.util.ArrayList;
import java.util.List;

import thelf.ch.yatzee.domain.score.ScoreSlotStruct;

/**
 * Created by Admin on 9/12/2015.
 */
public class PlayerSlots {
    private List<ScoreSlotStruct> slots = new ArrayList<>();

    public PlayerSlots(List<ScoreSlotStruct> slots) {
        this.slots = slots;
    }

    public ScoreSlotStruct getSlot(String id) {
        for(ScoreSlotStruct slot: slots) {
            if(slot.getId().equals(id)) {
                return slot;
            }
        }
        return null;
    }
}
