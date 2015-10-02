package thelf.ch.yatzee.domain.score;

import java.util.Arrays;

/**
 * Created by Admin on 9/8/2015.
 */
public abstract class ScoreSlot {

	private String id;
    private boolean selected;
    private int score;
    private int[] dices;

    public abstract int calcScore(int[] dieRoll);
    
    public ScoreSlot(String id) {
    	this.id = id;
    }

    
    public int sumOfAllDices(int[] dieRoll) {
        int res = 0;
        for(int diceValue: dieRoll) {
            res += diceValue;
        }
        return res;
    }
    
    public void select(int[] dieRoll) {
        assert !this.selected;
        this.score = calcScore(dieRoll);
        this.selected = true;
    }

    public boolean isSelected() {
        return selected;
    }

    public void deselect() {
        this.score = 0;
        this.dices = null;
        this.selected = false;
    }

    
    
	public String getId() {
		return id;
	}

	public int getScore() {
		return score;
	}

	@Override
	public String toString() {
		return "ScoreSlot [id=" + id + ", selected=" + selected + ", score=" + score + ", dices="
				+ Arrays.toString(dices) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScoreSlot other = (ScoreSlot) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
}
