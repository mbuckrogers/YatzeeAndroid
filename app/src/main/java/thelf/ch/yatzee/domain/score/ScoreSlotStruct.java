package thelf.ch.yatzee.domain.score;

import java.util.List;

public class ScoreSlotStruct {
	private String id;
	private int score;
	private boolean isSelected;

	public ScoreSlotStruct(ScoreSlot slot, int[] dieRoll) {
		this.id = slot.getId();
		this.score = slot.calcScore(dieRoll);
		this.isSelected = slot.isSelected();
	}

	public ScoreSlotStruct(ScoreSlot slot) {
		this.id = slot.getId();
		this.score = slot.getScore();
		this.isSelected = slot.isSelected();
	}

	public String getId() {
		return id;
	}

	public int getScore() {
		return score;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public static ScoreSlotStruct findMaxScoreSlot(List<ScoreSlotStruct> slots) {
		ScoreSlotStruct res = null;
		for (ScoreSlotStruct sls : slots) {
			if (!sls.isSelected()) {
				if (res == null) {
					res = sls;
				} else {
					res = sls.getScore() > res.getScore() ? sls : res;
				}
			}
		}
		return res;
	}
}