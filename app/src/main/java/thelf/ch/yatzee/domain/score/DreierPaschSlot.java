package thelf.ch.yatzee.domain.score;

/**
 * Created by Admin on 9/8/2015.
 */
public class DreierPaschSlot extends  AbstractOccurencesSlot {
    public DreierPaschSlot() {
		super("DP");
		// TODO Auto-generated constructor stub
	}

	@Override
    public int calcScore(int[] dieRoll) {
        int[] occurences = calcOccurences(dieRoll);
        int res = 0;
        for(int i = 0; i < 5; i++) {
            if(occurences[i] >= 3) {
                return sumOfAllDices(dieRoll);
            }
        }
        return 0;
    }
}
