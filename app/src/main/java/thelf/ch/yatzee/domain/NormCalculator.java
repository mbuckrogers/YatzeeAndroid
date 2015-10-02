package thelf.ch.yatzee.domain;

/**
 * Created by Admin on 9/27/2015.
 */
public interface NormCalculator {

    public static final NormCalculator EUKLID = new EuklidNorm();
    public static final NormCalculator XDIST = new TschebyschewNorm();

    public int norm(DiceEye x, DiceEye y);

    public static class EuklidNorm implements NormCalculator {
        @Override
        public int norm(DiceEye x, DiceEye y) {
            return (int) Math.sqrt(Math.pow(x.getX() - y.getX(), 2) + (Math.pow(x.getY() - y.getY(), 2)));
        }
    }

    public static class TschebyschewNorm implements NormCalculator {
        @Override
        public int norm(DiceEye x, DiceEye y) {
            return Math.abs(Math.max(x.getX() - y.getX(), x.getY() - y.getY()));
        }
    }
}