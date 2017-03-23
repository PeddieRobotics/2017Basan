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
	private enum Mode_Type {TELEOP, AUTO_RED, AUTO_BLUE, AUTO_RED_GEAR, AUTO_BLUE_GEAR, AUTO_RED_FAR, AUTO_BLUE_FAR};
	private Mode_Type mode = Mode_Type.TELEOP;
	private Encoder Eleft, Eright;
	private NavX NavX;
	private TrajectoryDriveController c_red;
	private TrajectoryDriveController c_blue;
	private TrajectoryDriveController c_red_far;
	private TrajectoryDriveController c_blue_far;
	private TrajectoryDriveController c_red_gear;
	private TrajectoryDriveController c_blue_gear;
	private TrajectoryDriveController c_both_middle;
	private boolean reverseFrontBack = false;

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
			c_red = new TrajectoryDriveController("/home/lvuser/AutoFiles/Shoot/Balls_Red.txt", 0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
			c_blue = new TrajectoryDriveController("/home/lvuser/AutoFiles/Shoot/Balls_Blue.txt", 0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
			c_red_gear = new TrajectoryDriveController("/home/lvuser/AutoFiles/Gear/RedRight.txt", 0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010); 
			c_blue_gear = new TrajectoryDriveController("/home/lvuser/AutoFiles/Shoot/BlueRight.txt", 0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
			c_both_middle = new TrajectoryDriveController("/home/lvuser/AutoFiles/Gear/middlePath.txt", 0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
			c_red_far = new TrajectoryDriveController("",0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
			c_blue_far = new TrajectoryDriveController("",0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
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

		double distance = (Eleft.getDistance() + Eright.getDistance())/2;

		return distance;
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
		double angle = NavX.getAngle();

		return angle;
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
		mode = Mode_Type.AUTO_RED;
	}
	
	/**
	 * Follows a path autonomously (blue alliance)
	 */
	public void auto_blueDrive() {
		resetEncodersAndNavX();
		c_blue.reset();
		mode = Mode_Type.AUTO_BLUE;
	}
	
	public void auto_gears_redDrive(){
		resetEncodersAndNavX();
		c_red_gear.reset();
		mode = Mode_Type.AUTO_RED_GEAR;
	}
	
	public void auto_gears_blueDrive(){
		resetEncodersAndNavX();
		c_blue_gear.reset();
		mode = Mode_Type.AUTO_BLUE_GEAR;
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
	 * 	@param place The side of airship to hang auto gear
	 */
	public void setSide(String side){
		place=side;
	}
	
	/**
	 * Positions gear in front of robot
	 * 
	 * @param reverseFrontBack Sets direction of wheel motors
	 */
	public void setGearFront() {
		reverseFrontBack = true;
	}
	
	public void setIntakeFront() {
		reverseFrontBack = false;
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
		case AUTO_RED:

			double[] m_red = new double[2];

			m_red = c_red.getOutput(Eleft.getDistance(), Eright.getDistance(), -getAngle()*3.14/180);

			DriverStation.reportError("" + m_red[0], false);

			Mleft.set(-m_red[0]);
			Mright.set(m_red[1]);
			break;
		
		case AUTO_BLUE:
			
			double[] m_blue = new double[2];

			m_blue = c_blue.getOutput(Eleft.getDistance(), Eright.getDistance(), -getAngle()*3.14/180);

			DriverStation.reportError(""+m_blue[1], false);

			Mleft.set(-m_blue[0]);
			Mright.set(m_blue[1]);
			break;
		
		case AUTO_RED_FAR:
			double[] m_red_far = new double[2];
			
			m_red_far = c_red_far.getOutput(Eleft.getDistance(), Eright.getDistance(), -getAngle()*3.14/180);
			
			DriverStation.reportError(""+m_red_far[1], false);

			Mleft.set(-m_red_far[0]);
			Mright.set(m_red_far[1]);
			break;
		
		case AUTO_BLUE_FAR:
			double[] m_blue_far = new double[2];
			
			m_blue_far = c_blue_far.getOutput(Eleft.getDistance(), Eright.getDistance(), -getAngle()*3.14/180);
			
			DriverStation.reportError(""+m_blue_far[1], false);

			Mleft.set(-m_blue_far[0]);
			Mright.set(m_blue_far[1]);
			break;
			
		case AUTO_RED_GEAR:
			
			double[] g_red = new double[2];
			
			switch(place){
			case "middle":
				g_red = c_both_middle.getOutput(Eleft.getDistance(), Eright.getDistance(), getAngle()*3.14/180);
				break;
			case "right":
				g_red = c_red_gear.getOutput(Eleft.getDistance(), Eright.getDistance(), getAngle()*3.14/180);
				break;
			}
			
			Mleft.set(-g_red[0]);
			Mright.set(g_red[1]);
			break;
			
		case AUTO_BLUE_GEAR:
			double[] g_blue = new double[2];
			
			switch(place){
			case "middle":
				g_blue = c_both_middle.getOutput(Eleft.getDistance(), Eright.getDistance(), getAngle()*3.14/180);
				break;
			case "right":
				g_blue = c_blue_gear.getOutput(Eleft.getDistance(), Eright.getDistance(), getAngle()*3.14/180);
				break;
			}
			
			Mleft.set(-g_blue[0]);
			Mright.set(-g_blue[1]);
			break;
			
		case TELEOP:
			
			if(reverseFrontBack) {
			Mleft.set(-Lspeed);
			Mright.set(-Rspeed);
			}
			else {
			Mleft.set(Lspeed);
			Mright.set(Rspeed);
			}

			break;
		}
	}
}
