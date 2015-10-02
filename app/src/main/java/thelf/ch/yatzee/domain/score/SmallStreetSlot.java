package thelf.ch.yatzee.domain.score;

/**
 * Created by Admin on 9/8/2015.
 */
public class SmallStreetSlot extends  StreetSlot {
    public SmallStreetSlot() {
		super("SS");
		// TODO Auto-generated constructor stub
	}

	@Override
    public int calcScore(int[] dieRoll) {
        if(calcLongestSequene(dieRoll) >= 4) {
            return 30;
        }
        return 0;
    }
}
