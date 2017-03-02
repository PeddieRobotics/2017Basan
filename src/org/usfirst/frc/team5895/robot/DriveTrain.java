package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.auto.AutoInGeneral;
import org.usfirst.frc.team5895.robot.lib.NavX;
import org.usfirst.frc.team5895.robot.lib.PID;
import org.usfirst.frc.team5895.robot.lib.TrajectoryDriveController;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class DriveTrain {
	private DriveTrain drive;
	private GearReceiver gear;
	private Shooter shooty;
	private AutoInGeneral auto;
	private Turret turrty;
	private Talon Mleft;
	private Talon Mright;
	double Lspeed, Rspeed;
	private enum Mode_Type {TELEOP, AUTO_RED, AUTO_BLUE, TURN_TO, DRIVE_TO};
	private Mode_Type mode = Mode_Type.TELEOP;
	private Encoder Eleft, Eright;
	private NavX NavX;
	private TrajectoryDriveController c_redShoot;
	private TrajectoryDriveController c_blueShoot;
	private TrajectoryDriveController cRLeft;
	private TrajectoryDriveController cMid;
	private TrajectoryDriveController cRRight;
	private TrajectoryDriveController cBLeft;
	private TrajectoryDriveController cBRight;
	private boolean reverseFrontBack = false;
	
	private String gameplan;
	private String gearSide;
	
	private PID distancePID;
	private double distance_kP = 0.02;
	private double distance_kI = 0;
	private double distance_kD = 0;
	private double distance_dV = 1;
	
	private PID turnPID;
	private double turn_kP = 0;
	private double turn_kI = 0;
	private double turn_kD = 0;
	private double turn_dV = 0;
	
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
			c_redShoot = new TrajectoryDriveController("/home/lvuser/Red.txt", 0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
			c_blueShoot = new TrajectoryDriveController("/home/lvuser/Straight.txt", 0.2, 0, 0, 1.0/13.0, 1.0/50.0, 0.008);
			cRLeft = new TrajectoryDriveController("/home/lvuser/AutoFiles/Gear/RedLeft.txt", 0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
			cMid = new TrajectoryDriveController("/home/lvuser/AutoFiles/Gear/middlePath.txt", 0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
			cRRight = new TrajectoryDriveController("/home/lvuser/AutoFiles/Gear/RedRight.txt", 0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
			cBLeft = new TrajectoryDriveController("/home/lvuser/AutoFiles/Gear/BlueLeft.txt", 0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
			cBRight = new TrajectoryDriveController("/home/lvuser/AutoFiles/Gear/BlueRight.txt", 0.2, 0, 0, 1.0/13.0, 1.0/50.0, -0.010);
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
		c_redShoot.reset();
		cRLeft.reset();
		cMid.reset();
		cRRight.reset();
		mode = Mode_Type.AUTO_RED;
	}
	
	/**
	 * Follows a path autonomously (blue alliance)
	 */
	public void auto_blueDrive() {
		resetEncodersAndNavX();
		c_blueShoot.reset();
		cBLeft.reset();
		cMid.reset();
		cBRight.reset();
		mode = Mode_Type.AUTO_BLUE;
	}
	
	public boolean trajectoryFinished(){
		if(c_blueShoot.onTarget() || c_redShoot.onTarget()|| cRLeft.onTarget() || cMid.onTarget() || cRRight.onTarget() || cBLeft.onTarget() || cBRight.onTarget()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * drives to a certain distance
	 * @param distance The distance to go
	 */
	public void driveTo(double distance) {
		distancePID.set(distance);
		mode = Mode_Type.DRIVE_TO;
	}
	/**
	 * turns to an angle
	 * @param angle The desired angle
	 */
	public void turnTo(double angle) {
		turnPID.set(angle);
		mode = Mode_Type.TURN_TO;
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
	
	public void setGearFront() {
		reverseFrontBack = true;
		
	}
	
	public void setIntakeFront() {
		reverseFrontBack = false;
	}
	
	/**
	 * Checks if we are within 1% of the distance we wanted
	 * @param distance
	 * @return
	 */
	public boolean atDistance(){
		if(getDistance() <= 1.01*distancePID.getSetpoint() && getDistance() >= .99*distancePID.getSetpoint()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Checks if we are within 1% of the angle we wanted
	 * @param angle
	 * @return
	 */
	public boolean atAngle(){
		if(getAngle() <= 1.01*turnPID.getSetpoint() && getAngle() >= .99*turnPID.getSetpoint()){
			return true;
		}else{
			return false;
		}
	}
	
	
	public void setAutoGameplan(String strats, String place){
		gameplan=strats;
		gearSide=place;
	}
	
	public void update()
	{
		//DriverStation.reportError("distance = " + getDistance()+"\n", false);

		switch(mode) {
		case AUTO_RED:
			double[] redSetters= new double[2];
			if(gameplan.contains("gear")){
				switch(gearSide){
				case "left":
					redSetters=auto.run(drive, gear, cRLeft, Eleft.getDistance(), Eright.getDistance(), getAngle()*3.14/180);
					break;
				case "middle":
					redSetters=auto.run(drive, gear, cMid, Eleft.getDistance(), Eright.getDistance(), getAngle()*3.14/180);
					break;
				case "right":
					redSetters=auto.run(drive, gear, cRRight, Eleft.getDistance(), Eright.getDistance(), getAngle()*3.14/180);
					break;
				}
			}
			else if(gameplan.contains("balls")){
				redSetters=auto.run(drive, gear, c_redShoot, Eleft.getDistance(), Eright.getDistance(), getAngle()*3.14/180);
			}
			
			Mleft.set(-redSetters[0]);
			Mright.set(redSetters[1]);
			break;
		
		case AUTO_BLUE:
			double[] blueSetters= new double[2];
			if(gameplan.contains("gear")){
				switch(gearSide){
				case "left":
					blueSetters=auto.run(drive, gear, cBLeft, Eleft.getDistance(), Eright.getDistance(), getAngle()*3.14/180);
					break;
				case "middle":
					blueSetters=auto.run(drive, gear, cMid, Eleft.getDistance(), Eright.getDistance(), getAngle()*3.14/180);
					break;
				case "right":
					blueSetters=auto.run(drive, gear, cBRight, Eleft.getDistance(), Eright.getDistance(), getAngle()*3.14/180);
					break;
				}
			}
			else if(gameplan.contains("balls")){
				blueSetters=auto.run(drive, gear, c_blueShoot, Eleft.getDistance(), Eright.getDistance(), getAngle()*3.14/180);
			}
			
			Mleft.set(-blueSetters[0]);
			Mright.set(blueSetters[1]);
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
		case DRIVE_TO:
			
			double distanceOutput = distancePID.getOutput(getDistance());
			Mleft.set(distanceOutput);
			Mright.set(distanceOutput);
			DriverStation.reportError("Distance is" + getDistance(), false);
			
			break;
		case TURN_TO:
			
			double turnOutput = turnPID.getOutput(getAngle());
			Mleft.set(turnOutput);
			Mright.set(turnOutput);
			DriverStation.reportError("Angle is " + getAngle(), false);
			break;
		}
	}
}
