package thelf.ch.yatzee.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import thelf.ch.yatzee.domain.Dice;
import thelf.ch.yatzee.domain.DiceEye;
import thelf.ch.yatzee.domain.NormCalculator;

/**
 * Created by Admin on 9/27/2015.
 */
public class TransitiveClosureCalculator {
    private NormCalculator normCalc = NormCalculator.EUKLID;
    private double reach  = 1.0;


    public Set<Dice> calcDie(Set<DiceEye> eyes) {
        Set<DiceEye> done = new HashSet<>();
        Set<Dice> result = new HashSet<>();
        for(DiceEye eye: eyes) {
            if (!done.contains(eye)) {
                Set<DiceEye> trans = transOf(eye, reach, eyes);
                done.addAll(trans);
                Dice dice = new Dice();
                dice.addAllDiceEyes(trans);
                result.add(dice);
            }
        }
        return result;
    }


    public Set<DiceEye>  transOf(DiceEye eye, double reach, Set<DiceEye> eyes) {
        Set<DiceEye> result = new HashSet<>();
        Stack<DiceEye> stack = new Stack<>();
        stack.push(eye);
        while(!stack.isEmpty()) {
            DiceEye current = stack.pop();
            result.add(current);
            List<DiceEye> directNeighbours = calcNeighbours(current, reach, eyes);
            for(DiceEye neighbour: directNeighbours) {
                if(!result.contains(neighbour)) {
                    stack.push(neighbour);
                }
            }
        }
        return result;
    }

    private List<DiceEye> calcNeighbours(DiceEye current, double reach, Set<DiceEye> eyes) {
        List<DiceEye> res = new ArrayList<>();
        for(DiceEye tmp: eyes) {
            if(normCalc.norm(current, tmp) <= reach) {
                res.add(tmp);
            }
        }
        return res;
    }

    public void setNormCalc(NormCalculator normCalc) {
        this.normCalc = normCalc;
    }

    public void setReach(double reach) {
        this.reach = reach;
    }
}
