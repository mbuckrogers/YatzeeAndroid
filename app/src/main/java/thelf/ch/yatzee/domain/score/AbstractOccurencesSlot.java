package thelf.ch.yatzee.domain.score;

/**
 * Created by Admin on 9/8/2015.
 */
public abstract class AbstractOccurencesSlot extends ScoreSlot {


    public AbstractOccurencesSlot(String id) {
		super(id);
	}

	protected int[] calcOccurences(int[] dieRoll) {
        int occurences[] = new int[6];
        for (int diceValue : dieRoll) {
            occurences[diceValue - 1]++;
        }
        return occurences;
    }
}
