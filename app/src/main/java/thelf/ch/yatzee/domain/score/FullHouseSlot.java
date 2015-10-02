package thelf.ch.yatzee.domain.score;

/**
 * Created by Admin on 9/8/2015.
 */
public class FullHouseSlot extends AbstractOccurencesSlot {
	
    public FullHouseSlot() {
		super("FH");
	}

	@Override
    public int calcScore(int[] dieRoll) {
        int[] occurences = calcOccurences(dieRoll);
        boolean isFullHouse = false;
        if(hasOccurence(5, occurences)) {
            isFullHouse = true;
        }

        if(hasOccurence(2, occurences) && hasOccurence(3, occurences)) {
            isFullHouse = true;
        }

        return isFullHouse ? 25 : 0;
    }

    protected boolean hasOccurence(int valToCheck, int[] occurences) {
        for(int occ: occurences) {
            if(occ == valToCheck) {
                return true;
            }
        }
        return false;
    }
}
