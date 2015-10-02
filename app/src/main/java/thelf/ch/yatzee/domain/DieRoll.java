package thelf.ch.yatzee.domain;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import thelf.ch.yatzee.Globals;

public class DieRoll {

	private static final DiceComparator DICE_COMP = new DiceComparator();
	
	private List<Dice> dices = new ArrayList<>();

	public DieRoll(Collection<Dice> pDices) {
		dices.addAll(pDices);
		Collections.sort(dices, DICE_COMP);
	}

	public void addDice(Dice dice) {
		dices.add(dice);
		Collections.sort(dices, DICE_COMP);
	}
	
	public boolean isValidYatzeeRoll() {
		if(dices.size() != 5) {
			Log.d(Globals.YATZEE, "Not 5 dices found " + dices.size());
			return false; }
		for(Dice dice: dices) {
			if(!dice.isValidEyesNumber()) {
				Log.d(Globals.YATZEE, "Dice #not valid " + dice);
				return false;
			}
				
		}
		return true;
	}

	public Dice getDice(int no) {
		return this.dices.get(no - 1);
	}

	public int[] getRollNumbers() {
		int[] roll = new int[5];
		for(int i = 0; i < 5;i++) {
			roll[i] = dices.get(i).getDiceValue();
		}
		return roll;
	}

	public String toString() {
		return dices.toString();
	}

	public List<Dice> getDices() {
		return dices;
	}

	private static class DiceComparator implements Comparator<Dice> {

		@Override
		public int compare(Dice o1, Dice o2) {
			return o2.getDiceValue() - o1.getDiceValue();
		}

	}

}
