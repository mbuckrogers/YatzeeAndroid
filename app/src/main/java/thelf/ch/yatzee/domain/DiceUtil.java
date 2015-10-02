package thelf.ch.yatzee.domain;

/**
 * Created by Admin on 9/3/2015.
 */
public class DiceUtil {

    public static int[] minimalBoundingBox(int margin, Dice... dices) {
        int res[] = {10000, 0, 10000, 0};
        for (Dice dice : dices) {
            for (DiceEye de : dice.getDiceEyes()) {
                if (de.getX() < res[0]) {
                    res[0] = de.getX();
                }
                if (de.getX() > res[1]) {
                    res[1] = de.getX();
                }

                if (de.getY() < res[2]) {
                    res[2] = de.getY();
                }
                if (de.getY() > res[3]) {
                    res[3] = de.getY();
                }

            }
        }

        res = new int[]  {res[0] - margin, res[1] + margin, res[2] - margin, res[3] + margin};
        return res;
    }



    public static Portion minimalBoundingPortion(int margin, Dice...dices) {
        int[] bBox = minimalBoundingBox(margin, dices);
        return new Portion(bBox[0], bBox[2], bBox[1], bBox[3]);

    }

    public static Portion minimalBoundingPortion(int margin, int width, int height, DieRoll roll) {
        Portion portion = minimalBoundingPortion(margin, roll.getDices().toArray(new Dice[0]));
        int x  = Math.max(0, portion.x);
        int x1 = Math.min(width, portion.x1);

        int y = Math.max(0, portion.y);
        int y1 = Math.min(height, portion.y1);

        return new Portion(x, y, x1, y1);
    }


    public static class Portion {
        public Portion(int px, int py, int px1, int py1){
            this.x = px;
            this.y = py;
            this.x1 = px1;
            this.y1 = py1;
            this.width = px1 - px;
            this.height = py1 - py;
        }

        public int x,y, x1, y1, width, height;
    }


}
