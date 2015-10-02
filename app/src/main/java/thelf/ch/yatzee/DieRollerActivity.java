package thelf.ch.yatzee;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import thelf.ch.yatzee.domain.Dice;
import thelf.ch.yatzee.domain.DiceImageHolder;
import thelf.ch.yatzee.domain.DiceUtil;
import thelf.ch.yatzee.domain.DieRoll;
import thelf.ch.yatzee.domain.PopupSeletionListener;

public class DieRollerActivity extends AppCompatActivity {
    private PopupSeletionListener popupSeletionListener;

    private static final Map<Integer, Integer> rm;
    private List<DiceImageHolder> imageHolders = new ArrayList<>(5);


    static {
        rm = new HashMap();
        rm.put(1, R.drawable.d1);
        rm.put(2, R.drawable.d2);
        rm.put(3, R.drawable.d3);
        rm.put(4, R.drawable.d4);
        rm.put(5, R.drawable.d5);
        rm.put(6, R.drawable.d6);
    }

    private void setDiceImage(int id, Dice dice) {
        ImageButton imgBttn = (ImageButton) findViewById(id);
        BitmapDrawable bd = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), rm.get(dice.getDiceValue())));
        imgBttn.setBackground(bd);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_die_roller);


        TextView rollText = (TextView) findViewById(R.id.rollView);

        YatzeeApplication app = (YatzeeApplication) getApplicationContext();

        if (app.getRoll() != null) {

            final DieRoll roll = app.getRoll();
            rollText.setText(roll.toString());

            imageHolders.add(new DiceImageHolder(roll.getDice(1), findViewById(R.id.imageButton)));
            imageHolders.add(new DiceImageHolder(roll.getDice(2), findViewById(R.id.imageButton2)));
            imageHolders.add(new DiceImageHolder(roll.getDice(3), findViewById(R.id.imageButton3)));
            imageHolders.add(new DiceImageHolder(roll.getDice(4), findViewById(R.id.imageButton4)));
            imageHolders.add(new DiceImageHolder(roll.getDice(5), findViewById(R.id.imageButton5)));

            final ImageView rollImage = (ImageView) findViewById(R.id.rollImageView);
            Bitmap origBitmap = app.getRollImage();
            Log.i(Globals.YATZEE, String.format("BM orig %d %d", origBitmap.getWidth(), origBitmap.getHeight()));
            DiceUtil.Portion imagePortion = DiceUtil.minimalBoundingPortion(50, origBitmap.getWidth(), origBitmap.getHeight(), roll);

            Bitmap portion = Bitmap.createBitmap(origBitmap, imagePortion.x, imagePortion.y, imagePortion.width, imagePortion.height);
            Log.i(Globals.YATZEE, String.format("BM portion %d %d", portion.getWidth(), portion.getHeight()));

            ShapeDrawable rect = new ShapeDrawable(new RectShape());
            rect.setIntrinsicHeight(portion.getHeight());
            rect.setIntrinsicWidth(portion.getWidth());
            rect.getPaint().setColor(Color.RED);

            LayerDrawable ld = new LayerDrawable(new Drawable[] {rect, new BitmapDrawable(portion)});
            rollImage.setImageDrawable(ld);
        }

        Button nextCapture = (Button) findViewById(R.id.nextCapture);
        nextCapture.setText("Capture");

    }


    public void onClick(View a) {
        Intent i = new Intent(this, Yatzee.class);
        startActivity(i);
    }

    public void onScore(View a) {
        Intent i = new Intent(this, Score.class);
        startActivity(i);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dieroller_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showPopup(View clickedDice, DiceImageHolder dice) {
        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = layoutInflater.inflate(R.layout.selectdice, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupSeletionListener = new PopupSeletionListener(popupView, popupWindow, dice);;
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);

        popupWindow.showAsDropDown(clickedDice);
    }

    public void diceClicked1(View clickedDice) {
        showPopup(clickedDice, this.imageHolders.get(0));
    }

    public void diceClicked2(View clickedDice) {
        showPopup(clickedDice, this.imageHolders.get(1));
    }

    public void diceClicked3(View clickedDice) {
        showPopup(clickedDice, this.imageHolders.get(2));
    }

    public void diceClicked4(View clickedDice) {
        showPopup(clickedDice, this.imageHolders.get(3));
    }

    public void diceClicked5(View clickedDice) {
        showPopup(clickedDice, this.imageHolders.get(4));
    }
}
