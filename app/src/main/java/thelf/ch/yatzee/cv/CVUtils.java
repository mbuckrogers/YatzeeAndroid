package thelf.ch.yatzee.cv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import thelf.ch.yatzee.domain.Dice;
import thelf.ch.yatzee.domain.DieRoll;

/**
 * Created by Admin on 9/2/2015.
 */

public class CVUtils {

    public static Mat flip(Mat origImage) {
        Mat flipped = origImage.t();
        Core.flip(origImage.t(), flipped, 1);
        Imgproc.resize(flipped, flipped, origImage.size());
        return flipped;
    }

    public static void drawRectangle(Mat drawOnMe, DieRoll roll, Scalar color) {
        for (Dice dice : roll.getDices()) {
            int bBox[] = dice.minimalBoundingBox();
            Core.rectangle(drawOnMe, new Point(bBox[0], bBox[2]), new Point(bBox[1], bBox[3]),
                    color);
        }
    }
}
