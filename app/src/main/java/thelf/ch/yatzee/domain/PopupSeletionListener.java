package thelf.ch.yatzee.domain;

import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;

import java.util.HashMap;
import java.util.Map;

import thelf.ch.yatzee.Globals;
import thelf.ch.yatzee.R;

/**
 * Created by Admin on 9/4/2015.
 */
public class PopupSeletionListener implements View.OnClickListener {

    private static final Map<Integer, Integer> rm;

    static {
        rm = new HashMap();
        rm.put(R.id.popUpImageButton1, 1);
        rm.put(R.id.popUpImageButton2, 2);
        rm.put(R.id.popUpImageButton3, 3);
        rm.put(R.id.popUpImageButton4, 4);
        rm.put(R.id.popUpImageButton5, 5);
        rm.put(R.id.popUpImageButton6, 6);
    }

    private View activity;
    private PopupWindow popupWindow;
    private DiceImageHolder tomanipulate;

    public PopupSeletionListener(View context, PopupWindow popupWindow, DiceImageHolder tomanipulate) {
        this.activity = context;

        this.popupWindow = popupWindow;
        this.tomanipulate = tomanipulate;
        initOnClickListener();
    }

    @Override
    public void onClick(View view) {
        int diceValue = rm.get(view.getId());
        Log.i(Globals.YATZEE, "Popup Button clicked " + diceValue);
        tomanipulate.override(diceValue);
        this.popupWindow.dismiss();
    }

    private void initOnClickListener() {

        this.activity.findViewById(R.id.popUpImageButton1).setOnClickListener(this);
        this.activity.findViewById(R.id.popUpImageButton2).setOnClickListener(this);
        this.activity.findViewById(R.id.popUpImageButton3).setOnClickListener(this);
        this.activity.findViewById(R.id.popUpImageButton4).setOnClickListener(this);
        this.activity.findViewById(R.id.popUpImageButton5).setOnClickListener(this);
        this.activity.findViewById(R.id.popUpImageButton6).setOnClickListener(this);
    }
}
