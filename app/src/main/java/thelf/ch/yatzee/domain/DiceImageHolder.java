package thelf.ch.yatzee.domain;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import thelf.ch.yatzee.R;

/**
 * Created by Admin on 9/4/2015.
 */
public class DiceImageHolder {

    private static final Map<Integer, Integer> DRAWABLES;

    static {
        DRAWABLES = new HashMap();
        DRAWABLES.put(1, R.drawable.d1);
        DRAWABLES.put(2, R.drawable.d2);
        DRAWABLES.put(3, R.drawable.d3);
        DRAWABLES.put(4, R.drawable.d4);
        DRAWABLES.put(5, R.drawable.d5);
        DRAWABLES.put(6, R.drawable.d6);
    }


    private Dice dice;
    private View imageButton;

    public DiceImageHolder(Dice dice, View imageButton) {
        this.dice = dice;
        this.imageButton = imageButton;
        setImageValue();
    }


    public void setImageValue() {
        BitmapDrawable bd = new BitmapDrawable(BitmapFactory.decodeResource(imageButton.getResources(), DRAWABLES.get(dice.getDiceValue())));
        imageButton.setBackground(bd);
    }

    public void override(int newValue) {
        this.dice.override(newValue);
        setImageValue();
    }

    public Dice getDice() {
        return dice;
    }

}
