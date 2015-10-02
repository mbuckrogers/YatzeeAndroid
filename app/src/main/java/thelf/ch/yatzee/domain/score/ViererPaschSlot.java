package thelf.ch.yatzee.domain.score;

/**
 * Created by Admin on 9/8/2015.
 */
public class ViererPaschSlot extends  AbstractOccurencesSlot {
    public ViererPaschSlot() {
		super("VP");
	}

	@Override
    public int calcScore(int[] dieRoll) {
        int[] occurences = calcOccurences(dieRoll);
        int res = 0;
        for(int i = 0; i < 5; i++) {
            if(occurences[i] >= 4) {
            	return sumOfAllDices(dieRoll);
            }
        }
       return 0;
    }
}
