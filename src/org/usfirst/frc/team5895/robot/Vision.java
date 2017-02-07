package org.usfirst.frc.team5895.robot;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5895.robot.lib.ReflectiveTapePipeline;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class Vision {
	
	ReflectiveTapePipeline p;
	CvSink cvSink;
	CvSource outputStream;
	Mat mat;
	
	public Vision() {
		p = new ReflectiveTapePipeline();
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		// Set the resolution
		camera.setResolution(640, 480);
		camera.setExposureManual(0);
		
		cvSink = CameraServer.getInstance().getVideo();
		outputStream = CameraServer.getInstance().putVideo("Rectangle", 640, 480);
		mat = new Mat();
	}
	
	public void update(){
		
		if (cvSink.grabFrame(mat) == 0) {
			// Send the output the error.
			outputStream.notifyError(cvSink.getError());
			// skip the rest of the current iteration
		} else {
			//double time = Timer.getFPGATimestamp();
			p.process(mat);
			//DriverStation.reportError(""+(Timer.getFPGATimestamp()-time),false);
					
			ArrayList<MatOfPoint> mop = p.filterContoursOutput();
			Imgproc.fillPoly(mat, mop, new Scalar(0, 0, 255));
			
			outputStream.putFrame(mat);
		}
	}

}
