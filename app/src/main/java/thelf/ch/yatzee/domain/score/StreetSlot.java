package thelf.ch.yatzee.domain.score;

import java.util.Arrays;

/**
 * Created by Admin on 9/8/2015.
 */
public abstract class StreetSlot extends  ScoreSlot {


    public StreetSlot(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	protected int calcLongestSequene(int[] dieRoll) {
        int longest = 1, tmpLongest = 1;
        Arrays.sort(dieRoll);
        for(int i = 1; i < 5; i++) {
            if(dieRoll[i-1] == dieRoll[i] - 1) {
                tmpLongest++;
                longest = Math.max(longest, tmpLongest);
            }
        }
        return longest;
    }
}
