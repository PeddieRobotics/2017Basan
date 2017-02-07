package org.usfirst.frc.team5895.robot;
import org.usfirst.frc.team5895.robot.lib.PID;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Turret {

	NetworkTable table;
	Talon myMotor;
	UsbCamera camera;
	double setpoint;
	private PID myPID;
	private double aCenterX;
	double[] defaultValue;

	public Turret(){
	defaultValue = new double[0];
	myMotor = new Talon(ElectricalLayout.TURRET_MOTOR);
	myPID = new PID(0.012, 0.000003, 0.0000001, 1);
	myPID.set(40);

	camera = CameraServer.getInstance().startAutomaticCapture();
	camera.setExposureManual(0);
	CameraServer.getInstance().startAutomaticCapture();

	table = NetworkTable.getTable("GRIP/myContoursReport");

	}

	public void findCenterX(){
	double[] centerXs = table.getNumberArray("centerY", defaultValue);
	for(double centerX : centerXs){
		DriverStation.reportError("CenterX:" + centerX, false);
		aCenterX = centerX ;
		}
	}


	public void turnTo(){
	myMotor.set(-myPID.getOutput(aCenterX));
	if(table.getNumberArray("centerY", defaultValue).equals(null)){
		myMotor.set(0);
		}
	}
}
