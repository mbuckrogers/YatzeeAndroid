package thelf.ch.yatzee;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import thelf.ch.yatzee.algorithm.TransitiveClosureCalculator;
import thelf.ch.yatzee.domain.Dice;
import thelf.ch.yatzee.domain.DiceEye;
import thelf.ch.yatzee.domain.NormCalculator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DiceBuilderTest {

    public static DiceEye dummyEye(int xy) {
        return new DiceEye(xy,xy,xy);
    }

    @Test
    public void testTransClosure() {
        Set<DiceEye> eyes = new HashSet<>();
        DiceEye d1 = dummyEye(1);
        DiceEye d2 = dummyEye(2);
        DiceEye d3 = dummyEye(3);
        DiceEye d4 = dummyEye(4);
        DiceEye d6 = dummyEye(6);
        DiceEye d7 = dummyEye(7);

        eyes.add(d1);
        eyes.add(d2);
        eyes.add(d3);
        eyes.add(d4);
        eyes.add(d6);
        eyes.add(d7);

        TransitiveClosureCalculator calc = new TransitiveClosureCalculator();
        calc.setNormCalc(NormCalculator.XDIST);
        Set<DiceEye> trans = calc.transOf(d1, 1, eyes);

        assertThat(trans.size(), is(4));

        Set<Dice> die = calc.calcDie(eyes);
        assertThat(die.size(), is(2));


    }



}
