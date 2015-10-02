package thelf.ch.yatzee.cv;

import android.content.Context;
import android.util.AttributeSet;

import org.opencv.android.NativeCameraView;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;

import java.util.List;

/**
 * Created by Admin on 9/5/2015.
 */
public class YatzeeCameraView extends NativeCameraView {

    public YatzeeCameraView(Context context, int cameraId) {
        super(context, cameraId);
    }

    public YatzeeCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public List<Size> getResolutionList() {
        return mCamera.getSupportedPreviewSizes();
    }

    public void setResolution(Size resolution) {
        disconnectCamera();
        connectCamera((int)resolution.width, (int)resolution.height);
    }

    public void setFocusMode (int type){
        switch (type){
            case 0:
                mCamera.set(Highgui.CV_CAP_PROP_ANDROID_FOCUS_MODE, Highgui.CV_CAP_ANDROID_FOCUS_MODE_AUTO);
                break;
            case 1:
                mCamera.set(Highgui.CV_CAP_PROP_ANDROID_FOCUS_MODE, Highgui.CV_CAP_ANDROID_FOCUS_MODE_CONTINUOUS_VIDEO);
                break;
            case 2:
                mCamera.set(Highgui.CV_CAP_PROP_ANDROID_FOCUS_MODE, Highgui.CV_CAP_ANDROID_FOCUS_MODE_EDOF);
                break;
            case 3:
                mCamera.set(Highgui.CV_CAP_PROP_ANDROID_FOCUS_MODE, Highgui.CV_CAP_ANDROID_FOCUS_MODE_FIXED);
                break;
            case 4:
                mCamera.set(Highgui.CV_CAP_PROP_ANDROID_FOCUS_MODE, Highgui.CV_CAP_ANDROID_FOCUS_MODE_INFINITY);
                break;
            case 5:
                mCamera.set(Highgui.CV_CAP_PROP_ANDROID_FOCUS_MODE, Highgui.CV_CAP_ANDROID_FOCUS_MODE_MACRO);
                break;
        }
    }

    public void setFlashMode (int type){
        switch (type){
            case 0:
                mCamera.set(Highgui.CV_CAP_PROP_ANDROID_FLASH_MODE, Highgui.CV_CAP_ANDROID_FLASH_MODE_AUTO);
                break;
            case 1:
                mCamera.set(Highgui.CV_CAP_PROP_ANDROID_FLASH_MODE, Highgui.CV_CAP_ANDROID_FLASH_MODE_OFF);
                break;
            case 2:
                mCamera.set(Highgui.CV_CAP_PROP_ANDROID_FLASH_MODE, Highgui.CV_CAP_ANDROID_FLASH_MODE_ON);
                break;
            case 3:
                mCamera.set(Highgui.CV_CAP_PROP_ANDROID_FLASH_MODE, Highgui.CV_CAP_ANDROID_FLASH_MODE_RED_EYE);
                break;
            case 4:
                mCamera.set(Highgui.CV_CAP_PROP_ANDROID_FLASH_MODE, Highgui.CV_CAP_ANDROID_FLASH_MODE_TORCH);
                break;
        }
    }

    public Size getResolution() {
        Size s = new Size(mCamera.get(Highgui.CV_CAP_PROP_FRAME_WIDTH),mCamera.get(Highgui.CV_CAP_PROP_FRAME_HEIGHT));
        return s;
    }

}
