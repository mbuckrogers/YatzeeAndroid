package thelf.ch.yatzee.algorithm;

import android.util.Log;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import thelf.ch.yatzee.Globals;
import thelf.ch.yatzee.domain.Dice;
import thelf.ch.yatzee.domain.DiceEye;
import thelf.ch.yatzee.domain.DieRoll;


public class DiceBuilder {

	private static final List<Integer> V_4_5_6 = Arrays.asList(4,5,6);
	private static final List<Integer> V_NO_ONE = Arrays.asList(2,3,4,5,6);
    private static final List<Integer> V_ALL = Arrays.asList(1,2,3,4,5,6);
	

	private Set<DiceEye> allEyes = new HashSet<>();
	private double average = -1.0;

	public DiceBuilder(Collection<DiceEye> eyes) {
        this.allEyes.addAll(eyes);
	}


	public DieRoll calculateRoll() {
		DieRoll roll =new DieRoll(calculateDices());
		return roll;
	}
	
	public Set<Dice> calculateDices() {
		if(allEyes.size() < 5) return Collections.EMPTY_SET;

        Set<Dice> found = new HashSet<>();
        Set<Dice> result = null;
        int i = 4;
        TransitiveClosureCalculator calc = new TransitiveClosureCalculator();
        while(i <= 7) {
            calc.setReach(calcAverage() * i);
            result = calc.calcDie(this.allEyes);
            Log.i(Globals.YATZEE, "Factor is " + i);
            found.addAll(removeWithValue(result, V_4_5_6));
            i++;
        }

        while(i < 12) {
            calc.setReach(calcAverage() * i);
            result = calc.calcDie(this.allEyes);
            Log.i(Globals.YATZEE, "Factor is " + i);
            found.addAll(removeWithValue(result, V_NO_ONE));
            i++;
        }

        calc.setReach(calcAverage() * 12);
        result = calc.calcDie(this.allEyes);
        Log.i(Globals.YATZEE, "Factor is " + 12);
        found.addAll(removeWithValue(result, V_ALL));

		return found;
	}

    private Set<Dice> removeWithValue(Set<Dice> allDices, Collection<Integer> diceValues) {
        Set<Dice> removed = new HashSet<>();
        for (Dice aDice : allDices) {
            if (diceValues.contains(aDice.getDiceValue())) {
                Log.i(Globals.YATZEE, "Removing " + aDice.getDiceValue());
                for (DiceEye eye : aDice.getDiceEyes()) {
                    allEyes.remove(eye);
                }
                removed.add(aDice);
            }
        }
        return removed;
    }



    public double calcAverage() {
		if (average < 0.0) {
			int sum = 0;
			for(DiceEye eye: allEyes) {
				sum += eye.getRadius();
			}
			average = sum / allEyes.size();
		}
		return average;
	}
}
