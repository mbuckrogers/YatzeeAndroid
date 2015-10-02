package thelf.ch.yatzee.domain.score;

/**
 * Created by Admin on 9/8/2015.
 */
public class BigStreetSlot extends  StreetSlot { 
	
    public BigStreetSlot() {
		super("BS");
	}

    @Override
    public int calcScore(int[] dieRoll) {
        if(calcLongestSequene(dieRoll) == 5) {
            return 40;
        }
        return 0;
    }
}
