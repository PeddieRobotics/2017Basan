package org.usfirst.frc.team5895.robot;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class Vision {
	
	private UsbCamera camera;
	private ReflectiveTapePipeline p;
	private CvSink cvSink;
	private CvSource outputStream;
	private Mat mat;
	
	private static final int W = 640;
	private static final int H = 480;
	private static final double W_FOV = 70.42;
	private static final double H_FOV = 43.30;
	private static final double H_TO_TARGET = 5.3333;
	
	public Vision() {
		p = new ReflectiveTapePipeline();
		camera = CameraServer.getInstance().startAutomaticCapture();
		// Set the resolution
		camera.setResolution(W, H);
		camera.setExposureManual(0);
		
		cvSink = CameraServer.getInstance().getVideo();
		outputStream = CameraServer.getInstance().putVideo("VisionTarget", W, H);
		mat = new Mat();
	}
	
	
	public void update(){
		
		if (cvSink.grabFrame(mat) == 0) {
			// Send the output the error.
			outputStream.notifyError(cvSink.getError());
			// skip the rest of the current iteration
		} else {
			p.process(mat);
			
			ArrayList<MatOfPoint> mop = p.filterContoursOutput();
			Imgproc.fillPoly(mat, mop, new Scalar(177, 112, 9));
			Point c = p.centerPoint();
			if (c != null) {
				Imgproc.circle(mat, c, 4, new Scalar(104, 208, 232), 3);
				double xDegreeError = ((c.x-(W/2))/W)*W_FOV;
		        double yDegreeError = -((c.y-(H/2))/H)*H_FOV;;
		        double distanceToTarget=H_TO_TARGET/Math.tan(yDegreeError*3.14159/180);				
			}
		
			outputStream.putFrame(mat);
		}
	}

}
