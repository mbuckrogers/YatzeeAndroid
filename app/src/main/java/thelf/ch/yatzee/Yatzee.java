package thelf.ch.yatzee;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.KeyPoint;

import java.util.ArrayList;
import java.util.List;

import thelf.ch.yatzee.algorithm.DiceBuilder;
import thelf.ch.yatzee.cv.BlobFeatureDetector;
import thelf.ch.yatzee.cv.CVUtils;
import thelf.ch.yatzee.cv.YatzeeCameraView;
import thelf.ch.yatzee.domain.Dice;
import thelf.ch.yatzee.domain.DiceEye;
import thelf.ch.yatzee.domain.DieRoll;

public class Yatzee extends Activity implements CvCameraViewListener2 {
    private static final String TAG = "Yatzee::Activity";

    private YatzeeCameraView mOpenCvCameraView;
    private MenuItem mItemSwitchCamera = null;
    long lastProcessed = 0;
    boolean allowSending = false;
    boolean torchmode = false;
    Mat mRgba;





    //    static {
//        if (!OpenCVLoader.initDebug()) {
//            Log.e("opencv", "init failed");
//        } else {
//            Log.i("opencv", "init success");
//        }
//    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    public Yatzee() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_yatze);

        mOpenCvCameraView = (YatzeeCameraView) findViewById(R.id.tutorial1_activity_native_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        boolean allowSending = false;
    }


    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();

        boolean allowSending = false;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
        boolean allowSending = false;
    }

    public void onDestroy() {
        super.onDestroy();
        mOpenCvCameraView.disableView();
        boolean allowSending = false;
    }

    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat();
        mOpenCvCameraView.setFocusMode(1);
    }

    public void onCameraViewStopped() {
        mRgba.release();
        boolean allowSending = false;
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat mRgba = inputFrame.rgba();
        mRgba = CVUtils.flip(mRgba);
        if (System.currentTimeMillis() - lastProcessed > 100) {
            process(mRgba);
            lastProcessed = System.currentTimeMillis();
        }

        return mRgba;
    }

    private void process(Mat m) {

        BlobFeatureDetector bf = new BlobFeatureDetector(getCacheDir());
        List<KeyPoint> keyPoints = bf.detect(m);

        List<DiceEye> eyes = toDiceEyes(keyPoints);


        DiceBuilder builder = new DiceBuilder(eyes);
        DieRoll roll = builder.calculateRoll();

        if (roll.isValidYatzeeRoll()) {
            CVUtils.drawRectangle(m, roll, new Scalar(255, 0, 0, 0));

            for (Dice dice : roll.getDices()) {
                for (DiceEye de : dice.getDiceEyes()) {
                    Core.circle(m, new Point(de.getX(), de.getY()), de.getRadius(),
                            new Scalar(255, 0, 0, 0), -1);
                }
            }

            Bitmap img = Bitmap.createBitmap(m.cols(), m.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(m, img);

            YatzeeApplication app = (YatzeeApplication) getApplicationContext();
            app.setRollImage(img);
            app.setRoll(roll);

            if(allowSending) {
                Intent i = new Intent(this, DieRollerActivity.class);
                startActivity(i);
            }

        } else {
            CVUtils.drawRectangle(m, roll, new Scalar(255, 0, 0, 0));
            for (DiceEye de : eyes) {
                Core.circle(m, new Point(de.getX(), de.getY()), de.getRadius(), new Scalar(255, 255, 0, 0), -1);
            }
        }
    }

    public void allowSending(View source) {
        this.allowSending = true;
    }


    private List<DiceEye> toDiceEyes(List<KeyPoint> keyPoints) {
        List<DiceEye> result = new ArrayList<>(keyPoints.size());
        for (KeyPoint kp : keyPoints) {
            result.add(new DiceEye((int) kp.pt.x, (int) kp.pt.y, (int) kp.size));
        }
        return result;
    }

    public void toggleTorch(View buttn) {
        if(torchmode) {
            // Disable
            mOpenCvCameraView.setFlashMode(1);
        } else {
            mOpenCvCameraView.setFlashMode(4);
        }
        torchmode  = !torchmode;
    }

}
