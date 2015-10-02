package thelf.ch.yatzee.domain.score;

/**
 * Created by Admin on 9/8/2015.
 */
public class FixedSideSlot extends ScoreSlot {

    private int fixedSide;

    public FixedSideSlot(int fixedSide) {
    	super(new Integer(fixedSide).toString());
        this.fixedSide = fixedSide;
    }

    @Override
    public int calcScore(int[] dieRoll) {
        int multiply =0;
        for(int diceValue: dieRoll) {
            if(fixedSide == diceValue) {
                multiply++;
            }
        }
        return multiply * fixedSide;
    }
}
