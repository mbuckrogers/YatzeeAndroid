package thelf.ch.yatzee;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import thelf.ch.yatzee.model.GameModel;
import thelf.ch.yatzee.model.ModelUtil;
import thelf.ch.yatzee.model.ScoreSheetModel;

public class Score extends AppCompatActivity {

    private GestureDetector gestureDetector = new GestureDetector(new SwipeGestureDetector());


    private GameModel getGameModel() {
        YatzeeApplication app = (YatzeeApplication) getApplicationContext();
        return app.getGameModel();
    }

    private int[] getRoll() {
        YatzeeApplication app = (YatzeeApplication) getApplicationContext();
        return app.getRollValue();
    }

    private List<View> createLines(ScoreSheetModel model) {
        List<View> lines = new ArrayList<>();

        for (ScoreSheetModel.ScoreSheetLine scoreLine : model.getLines()) {
            LinearLayout line = new LinearLayout(this);
            lines.add(line);
            line.setPadding(0, 10, 0, 10);
            line.setOrientation(LinearLayout.HORIZONTAL);
            line.setId(scoreLine.getLineId().hashCode());

            if (scoreLine.isSelectable()) {
                line.setOnClickListener(new ScoreSlotClickedListener(this, scoreLine.getLineId(), getGameModel()));
            }
            line.setClickable(scoreLine.isSelectable());
            TextView scoreLabel = new TextView(this);
            scoreLabel.setText(scoreLine.getLineId());
            scoreLabel.setWidth(250);
            line.addView(scoreLabel);

            for (ScoreSheetModel.ScoreCell cell : scoreLine.getScoreCells()) {
                boolean active = cell.isActivePlayer();

                TextView score = new TextView(this);

                score.setWidth(100);
                score.setText("" + cell.getScore());
                if (!active) {
                    score.setTextColor(Color.DKGRAY);
                    score.setTypeface(score.getTypeface(), Typeface.ITALIC);
                } else {
                    score.setTypeface(score.getTypeface(), Typeface.BOLD);
                    if (cell.isSelected()) {
                        score.setTextColor(Color.DKGRAY);
                    } else {
                        score.setTextColor(Color.BLUE);
                    }
                }
                line.addView(score);
            }
        }
        return lines;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        LinearLayout vL = (LinearLayout) findViewById(R.id.scoreLines);
        Log.i(Globals.YATZEE, "Got the LinearLayout " + vL);

        ScoreSheetModel sheetModel = ModelUtil.create(getGameModel().getActualPlayer(), getGameModel().toScoreSlot(getRoll()));


        for (View scoreLine : createLines(sheetModel)) {
            vL.addView(scoreLine);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score, menu);
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

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (gestureDetector.onTouchEvent(event)) {
//            return true;
//        }
//        return super.onTouchEvent(event);
//    }
//

    public void onLeftSwipe() {
        nextRoll(null);
    }

    public void nextRoll(View src) {
        Log.i(Globals.YATZEE, "nextRoll");
        // Check if a line is selected
        if (!getGameModel().canSwitch()) {
            // Ignore
            return;
        }


        getGameModel().bookRoll(getRoll());


        LinearLayout vL = (LinearLayout) findViewById(R.id.scoreLines);

        for (int i = 0; i < vL.getChildCount(); i++) {
            vL.getChildAt(i).setBackgroundColor(Color.WHITE);
            vL.setActivated(false);
        }


//        ScoreSheetModel sheetModel = ModelUtil.create(getGameModel().getActualPlayer(), getGameModel().toScoreSlot(actualRoll));
//        List<View> lines = createLines(sheetModel);
//
//
//        vL.removeAllViews();
//
//        for (View scoreLine : createLines(sheetModel)) {
//            vL.addView(scoreLine);
//        }

        Intent i = new Intent(this, Yatzee.class);
        startActivity(i);
    }

    public static class ScoreSlotClickedListener implements View.OnClickListener {

        private Score activity;
        private String slotId;
        private GameModel gameModel;

        public ScoreSlotClickedListener(Score activity, String id, GameModel gameModel) {
            this.activity = activity;
            this.slotId = id;
            this.gameModel = gameModel;
        }

        @Override
        public void onClick(View view) {
            LinearLayout vL = (LinearLayout) activity.findViewById(R.id.scoreLines);
            Log.i(Globals.YATZEE, "Children " + vL.getChildCount());

            for (int i = 0; i < vL.getChildCount(); i++) {
                vL.getChildAt(i).setBackgroundColor(Color.WHITE);
                vL.setActivated(false);
            }

            Log.i(Globals.YATZEE, "Clicked on " + slotId);
            if (!view.isActivated()) {
                view.setBackgroundColor(Color.LTGRAY);
                view.setActivated(true);
            }

            gameModel.setSelectedLineId(slotId);

        }
    }

    private class SwipeGestureDetector
            extends GestureDetector.SimpleOnGestureListener {
        // Swipe properties, you can change it to make the swipe
        // longer or shorter and speed
        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_MAX_OFF_PATH = 200;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {
            try {
                float diffAbs = Math.abs(e1.getY() - e2.getY());
                float diff = e1.getX() - e2.getX();

                if (diffAbs > SWIPE_MAX_OFF_PATH)
                    return false;

                // Left swipe
                if (diff > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Score.this.onLeftSwipe();

                    // Right swipe
                }
            } catch (Exception e) {
                Log.e("YourActivity", "Error on gestures");
            }
            return false;
        }
    }
}
