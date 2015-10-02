package thelf.ch.yatzee.domain.score;

/**
 * Created by Admin on 9/8/2015.
 */
public class ChanceSlot extends ScoreSlot {
    public ChanceSlot() {
		super("CH");
	}

	@Override
    public int calcScore(int[] dieRoll) {
       return sumOfAllDices(dieRoll);
    }
}
