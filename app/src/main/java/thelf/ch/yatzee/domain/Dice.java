package thelf.ch.yatzee.domain;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class Dice {

    private int override  = -1;

	private Set<DiceEye> diceEyes = new TreeSet<DiceEye>();

	public void addAllDiceEyes(Collection<DiceEye> eyes) {
		for(DiceEye eye: eyes) {
			addDiceEye(eye);
		}
	}

	public void addDiceEye(DiceEye anEye) {
		for (DiceEye eye : diceEyes) {
			if (eye.isContained(anEye)) {
				return;
			}
		}
		this.diceEyes.add(anEye);
	}

	public int getDiceValue() {
		return override == -1 ? diceEyes.size() : override;
	}
	
	public int[] minimalBoundingBox() {
		return DiceUtil.minimalBoundingBox(10, this);
	}

	public Set<DiceEye> getDiceEyes() {
		return diceEyes;
	}

	public String toString() {
		return "Dice: " + diceEyes.size();
	}

    public void override(int eyes) {
        this.override = eyes;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((diceEyes == null) ? 0 : diceEyes.hashCode());
		return result;
	}
	
	public boolean isValidEyesNumber() {
		return 1 <= getDiceValue() && getDiceValue() <= 6;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dice other = (Dice) obj;
		if (diceEyes == null) {
			if (other.diceEyes != null)
				return false;
		} else if (!diceEyes.equals(other.diceEyes))
			return false;
		return true;
	}

}
