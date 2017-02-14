package org.usfirst.frc.team5895.robot;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DSVision {

	private static final int W = 640;
	private static final int H = 480;
	private static final double W_FOV = 70.42;
	private static final double H_FOV = 43.30;
	private static final double H_TO_TARGET = 5.3333;
	private static final double CAM_ANGLE = 0; //NEED TO SET THIS
	
	private UsbCamera camera;
	private CvSink cvSink;
	private CvSource outputStream;
	private Mat mat;

	public DSVision() {
		camera = CameraServer.getInstance().startAutomaticCapture();
		// Set the resolution
		camera.setResolution(W, H);
		camera.setExposureManual(0);
		
		cvSink = CameraServer.getInstance().getVideo();
		outputStream = CameraServer.getInstance().putVideo("VisionTarget", W, H);
	}
	
	public void update() {
		if (cvSink.grabFrame(mat) == 0) {
			// Send the output the error.
			outputStream.notifyError(cvSink.getError());
			// skip the rest of the current iteration
		} else {
			double time = camera.getLastFrameTime();
			DriverStation.reportError(""+time,false);
			SmartDashboard.putNumber("DB/Slider 0", time);
			outputStream.putFrame(mat);
		}
	}
}
