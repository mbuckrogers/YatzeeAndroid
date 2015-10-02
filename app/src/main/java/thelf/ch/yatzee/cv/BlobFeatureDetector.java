package thelf.ch.yatzee.cv;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Size;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.KeyPoint;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class BlobFeatureDetector {
    private static final String PARAMS =
            "%YAML:1.0 \n" +
                    " \nthresholdStep: 10. \nminThreshold: 50. \nmaxThreshold: 220. " +
                    " \nminRepeatability: 2 \nminDistBetweenBlobs: 10. \nfilterByColor: 1 \nblobColor: 0 \nfilterByArea: 1 \nminArea: 50. \nmaxArea: 5000. \nfilterByCircularity: 1 " +
                    " \nminCircularity: 8.000001192092896e-1 \nmaxCircularity: 3.4028234663852886e+038 \nfilterByInertia: 0 \nminInertiaRatio: 9.5000000149011612e-1 " +
                    " \nmaxInertiaRatio: 3.4028234663852886e+038 \nfilterByConvexity: 0 \nminConvexity: 9.4999998807907104e-1 \nmaxConvexity: 3.4028234663852886e+03";

	private FeatureDetector blobDetector = FeatureDetector.create(FeatureDetector.SIMPLEBLOB);
    private String blobParamFile;

    public BlobFeatureDetector(File paramDir) {
        try {
            File outputFile = File.createTempFile("blobDetectorParams", ".YAML", paramDir);
            writeToFile(outputFile, PARAMS);
            blobParamFile = outputFile.getPath();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }



	public List<KeyPoint> detect(final Mat srcImgMat) {
		Mat mGray = new Mat();
		//Imgproc.pyrDown(srcImgMat, srcImgMat, new Size(srcImgMat.cols()/4,  srcImgMat.rows()/4));
		Imgproc.cvtColor(srcImgMat, mGray, Imgproc.COLOR_RGBA2GRAY);
        // doing a gaussian blur prevents getting a lot of false hits
	    Imgproc.GaussianBlur(mGray, mGray, new Size (5,5), 2.2, 2);
        // Values 3 and 4are the LowerThreshold and UpperThreshold.
		
		MatOfKeyPoint matOfKeyPoints = new MatOfKeyPoint();
		blobDetector.read(blobParamFile);

		blobDetector.detect(mGray, matOfKeyPoints);

		return matOfKeyPoints.toList();
    }


    private void writeToFile(File file, String data) throws IOException {
        FileOutputStream stream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(stream);
        outputStreamWriter.write(data);
        outputStreamWriter.close();
        stream.close();
    }

}
