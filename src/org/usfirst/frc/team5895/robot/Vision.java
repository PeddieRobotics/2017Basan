package org.usfirst.frc.team5895.robot;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5895.robot.lib.ReflectiveTapePipeline;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class Vision {
	
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	
	private VisionThread visionThread;
	private ReflectiveTapePipeline GRIP;
	private double centerX = 0.0;
	private double turn;
	
	
	private final Object imgLock = new Object();
	
	public void visionCamera(){
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
        
        
        visionThread = new VisionThread(camera, new ReflectiveTapePipeline(), GripPipelineListener ->  {
            if (!GripPipelineListener.filterContoursOutput().isEmpty()) {
                Rect r = Imgproc.boundingRect(GRIP.filterContoursOutput().get(0));
                synchronized (imgLock) {
                    centerX = r.x + (r.width / 2);
                }
            }
        });
        visionThread.start();
        
            
        //this should get everything
	}
	
	public double autoLock(){
		double centerX=GRIP.getCenter();
		return centerX;
	}

}
