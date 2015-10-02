package thelf.ch.yatzee.domain.score;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 9/8/2015.
 */
public class ScoreSheet {
	private String player;
	private List<ScoreSlot> slots = new ArrayList<>(13);
	
	public ScoreSheet(String player) {
		this.player = player;
		slots.add(new FixedSideSlot(1));
		slots.add(new FixedSideSlot(2));
		slots.add(new FixedSideSlot(3));
		slots.add(new FixedSideSlot(4));
		slots.add(new FixedSideSlot(5));
		slots.add(new FixedSideSlot(6));
		
		slots.add(new DreierPaschSlot());
		slots.add(new ViererPaschSlot());
		slots.add(new FullHouseSlot());
		slots.add(new SmallStreetSlot());
		slots.add(new BigStreetSlot());
		slots.add(new YatzeeSlot());
		slots.add(new ChanceSlot());
	}
	
	public List<ScoreSlotStruct> whatIf(int[] dieRoll) {
		List<ScoreSlotStruct> res = new ArrayList<>(slots.size());
		for(ScoreSlot sl: slots) {
			ScoreSlotStruct slotStruct = new ScoreSlotStruct(sl);
			if(!sl.isSelected()) {
				slotStruct = new ScoreSlotStruct(sl, dieRoll);
			}
			res.add(slotStruct);
		}
		return res;
	}

	public List<ScoreSlotStruct> actual() {
		List<ScoreSlotStruct> res = new ArrayList<>(slots.size());
		for(ScoreSlot sl: slots) {
			res.add(new ScoreSlotStruct(sl));
		}
		return res;
	}
	
	public int selectSlot(ScoreSlotStruct slot, int[] dieRoll) {
		return selectSlot(slot.getId(), dieRoll);
	}
	
	public int selectSlot(String slotId, int[] dieRoll) {
		for(ScoreSlot sl: slots) {
			if(sl.getId().equals(slotId)) {
				sl.select(dieRoll);
			}
		}
		return calcScore();
	}
	
	public int calcScore() {
		return calcUpperSum() + calcBonus() + calcLowerSum();
	}
	
	public int calcUpperSum() {
		int upperSum = 0;
		for(int i = 0; i < 6; i++) {
			ScoreSlot sl = slots.get(i);
			if(sl.isSelected()) {
				upperSum += sl.getScore();
			}
		}
		return upperSum;
	}
	
	public int calcLowerSum() {
		int lowerSum = 0;
		for(int i = 6; i < slots.size(); i++) {
			ScoreSlot sl = slots.get(i);
			if(sl.isSelected()) {
				lowerSum += sl.getScore();
			}
		}
		return lowerSum;
	}
	
	public int calcBonus() {
		return calcUpperSum() >= 63 ? 50 : 0;
	}
	
}
