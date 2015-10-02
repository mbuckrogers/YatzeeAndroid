package thelf.ch.yatzee.domain.score;

import java.util.List;
import java.util.Random;

public class AutoGame {
	private Random random = new Random(System.currentTimeMillis());
	private ScoreSheet sheet = new ScoreSheet("AI");

	private int[] rollDice() {
		int roll[] = new int[5];
		for (int i = 0; i < 5; i++) {
			roll[i] = random.nextInt(5) + 1;
		}
		return roll;
	}

	void playRound() {
		int[] dieRoll = rollDice();
		List<ScoreSlotStruct> slots = sheet.whatIf(dieRoll);
		ScoreSlotStruct maxScore = ScoreSlotStruct.findMaxScoreSlot(slots);
		sheet.selectSlot(maxScore.getId(), dieRoll);
//		System.out.println(String.format("Selected %s %d score for %s", maxScore.getId(), maxScore.getScore(),
//				DiceUtil.rollToString(dieRoll)));
	}

	public void playWithYourself() {
		int max = 0;
		for (int j = 0; j < 1000000; j++) {
			for (int i = 0; i < 6 + 2 + 1 + 2 + 2; i++) {
				playRound();
			}
			//System.out.println("Total Score " + sheet.calcScore());
			if(sheet.calcBonus() > 0) {
				System.out.print(".");
			}
			max = Math.max(max, sheet.calcScore());
			this.sheet = new ScoreSheet("AI" + j);
		}
		System.out.println("Maximum " + max);
	}

	public static void main(String args[]) {
		AutoGame game = new AutoGame();
		game.playWithYourself();
	}
}
