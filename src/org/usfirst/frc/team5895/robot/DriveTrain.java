package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.lib.NavX;
import org.usfirst.frc.team5895.robot.lib.TrajectoryDriveController;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class DriveTrain {
	private Talon Mleft;
	private Talon Mright;
	double Lspeed, Rspeed;
	private String place;
	private enum Mode_Type {TELEOP, AUTO_SPLINE};
	private Mode_Type mode = Mode_Type.TELEOP;
	private Encoder Eleft, Eright;
	private NavX NavX;
	private TrajectoryDriveController c_red;
	private TrajectoryDriveController c_blue;
	private TrajectoryDriveController c_red_far;
	private TrajectoryDriveController c_blue_far;
	private TrajectoryDriveController c_blue_close;
	private TrajectoryDriveController c_red_close;
	private TrajectoryDriveController c_in_use;

	public DriveTrain()
	{
		NavX=new NavX();

		Mleft = new Talon(ElectricalLayout.DRIVE_LEFTMOTOR);
		Mright = new Talon(ElectricalLayout.DRIVE_RIGHTMOTOR);

		Eleft = new Encoder(ElectricalLayout.DRIVE_LEFTENCODER,ElectricalLayout.DRIVE_LEFTENCODER2, true, Encoder.EncodingType.k4X);
		Eright = new Encoder(ElectricalLayout.DRIVE_RIGHTENCODER,ElectricalLayout.DRIVE_RIGHTENCODER2, false, Encoder.EncodingType.k4X);

		Eleft.setDistancePerPulse(4/12.0*3.14/360);
		Eright.setDistancePerPulse(4/12.0*3.14/360);

		try {
			//Check back everything. generate the missing splines
			c_red = new TrajectoryDriveController("/home/lvuser/AutoFiles/Shoot/Balls_Red.txt", 0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
			c_blue = new TrajectoryDriveController("/home/lvuser/AutoFiles/Shoot/Balls_Red.txt", 0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
			c_red_far = new TrajectoryDriveController("/home/lvuser/AutoFiles/Shoot/Balls_Red.txt",0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
			c_blue_far = new TrajectoryDriveController("/home/lvuser/AutoFiles/Shoot/Balls_Red.txt",0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
			c_blue_close = new TrajectoryDriveController("/home/lvuser/AutoFiles/Shoot/Balls_Blue_Close.txt",0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
			c_red_close = new TrajectoryDriveController("/home/lvuser/AutoFiles/Shoot/Balls_Red_Close.txt",0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
		} catch (Exception e){
			DriverStation.reportError("Auto files not on robot!", false);
		}
	}

	/**
	 * Returns the average distance the drivetrain has driven
	 * 
	 * @return The distance in feet
	 */
	public double getDistance() {
		return (Eleft.getDistance() + Eright.getDistance())/2;
	}

	/**
	 * Returns the speed of the drivetrain
	 * 
	 * @return The speed in feet per second
	 */
	public double getSpeed() {
		return (Eleft.getRate() + Eright.getRate())/2;
	}

	/**
	 * Returns the angle of the robot
	 * 
	 * @return The angle of the robot in degrees
	 */
	public double getAngle(){
		return NavX.getAngle();
	}
	
	/**
	 * Resets encoders and NavX
	 */
	public void resetEncodersAndNavX(){
		Eleft.reset();
		Eright.reset();
		NavX.reset();
	}
	
	/**
	 * Follows a path autonomously (red alliance)
	 */
	public void auto_redDrive() {
		resetEncodersAndNavX();
		c_red.reset();
		c_in_use = c_red;
		mode = Mode_Type.AUTO_SPLINE;
	}
	
	/**
	 * Follows a path autonomously (blue alliance)
	 */
	public void auto_blueDrive() {
		resetEncodersAndNavX();
		c_blue.reset();
		c_in_use = c_blue;
		mode = Mode_Type.AUTO_SPLINE;
	}
	
	public void auto_blue_closeDrive() {
		resetEncodersAndNavX();
		c_blue_close.reset();
		c_in_use = c_blue_close;
		mode = Mode_Type.AUTO_SPLINE;
	}
	
	public void auto_red_closeDrive(){
		resetEncodersAndNavX();
		c_red_close.reset();
		c_in_use = c_red_close;
		mode = Mode_Type.AUTO_SPLINE;
	}
	
	public void auto_red_farDrive() {
		resetEncodersAndNavX();
		c_red_far.reset();
		c_in_use = c_red_far;
		mode = Mode_Type.AUTO_SPLINE;
	}
	
	public void auto_blue_farDrive() {
		resetEncodersAndNavX();
		c_blue_far.reset();
		c_in_use = c_blue_far;
		mode = Mode_Type.AUTO_SPLINE;
	}
	
	/**
	 * Two-controller driving
	 * 
	 * @param speed The forward/backwards motion
	 * @param turn The left/right turning motion
	 */
	public void arcadeDrive( double speed, double turn) {
		Lspeed = speed + turn;
		Rspeed = -speed + turn;
		mode = Mode_Type.TELEOP;
	}
	
	/**
	 * Sets speed of both sides
	 * 
	 * @param l The speed of the left side
	 * @param r The speed of the right side
	 */
	public void setLeftRightPower(double l, double r) {
		Lspeed = l;
		Rspeed = r;
		mode = Mode_Type.TELEOP;
	}
	
	
	/**
	 * Red Auto, Blue Auto
	 * Red Far Hopper, Blue Far Hopper
	 * Red Gear, Blue Gear
	 * Teleop
	 */
	public void update()
	{
		//DriverStation.reportError("distance = " + getDistance()+"\n", false);

		switch(mode) {
		case AUTO_SPLINE:
			
			double[] m = new double[2];

			m = c_in_use.getOutput(Eleft.getDistance(), Eright.getDistance(), -getAngle()*3.14/180);

			Mleft.set(-m[0]);
			Mright.set(m[1]);
			break;
		
		
		case TELEOP:
			
			Mleft.set(Lspeed);
			Mright.set(Rspeed);
			break;
		}
	}
}
