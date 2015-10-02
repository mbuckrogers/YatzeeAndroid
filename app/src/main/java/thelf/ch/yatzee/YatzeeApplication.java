package thelf.ch.yatzee;

import android.app.Application;
import android.graphics.Bitmap;

import thelf.ch.yatzee.domain.DieRoll;
import thelf.ch.yatzee.model.GameModel;

/**
 * Created by Admin on 9/20/2015.
 */
public class YatzeeApplication extends Application {


    public Bitmap rollImage;
    public DieRoll roll;
    private GameModel gameModel = new GameModel();

    public GameModel getGameModel() {
        return gameModel;
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public Bitmap getRollImage() {
        return rollImage;
    }

    public void setRollImage(Bitmap rollImage) {
        this.rollImage = rollImage;
    }

    public DieRoll getRoll() {
        return roll;
    }

    public void setRoll(DieRoll roll) {
        this.roll = roll;
    }

    public int[] getRollValue() {
        return this.getRoll().getRollNumbers();
    }
}

