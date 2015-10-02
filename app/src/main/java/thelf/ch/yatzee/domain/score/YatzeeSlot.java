package thelf.ch.yatzee.domain.score;

/**
 * Created by Admin on 9/8/2015.
 */
public class YatzeeSlot extends AbstractOccurencesSlot {

    public YatzeeSlot() {
		super("YA");
		// TODO Auto-generated constructor stub
	}


	@Override
    public int calcScore(int[] dieRoll) {
        int[] occurences = calcOccurences(dieRoll);
        if (isYatzee(occurences)) {
            return 50;
        }
        return 0;
    }


    private boolean isYatzee(int[] occurences) {
        for (int occ : occurences) {
            if (occ == 5) {
                return true;
            }
        }
        return false;
    }
}
