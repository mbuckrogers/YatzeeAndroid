package thelf.ch.yatzee.domain.score;

public class DiceUtil {

	public static String rollToString(int[] roll) {
		return String.format("[%d, %d, %d, %d, %d]", roll[0], roll[1], roll[2], roll[3], roll[4]);
	}
}
