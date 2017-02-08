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
	private enum Mode_Type {TELEOP, AUTO};
	private Mode_Type mode = Mode_Type.TELEOP;
	private Encoder Eleft, Eright;
	private NavX NavX;
	private TrajectoryDriveController c;

	public DriveTrain()
	{
		NavX=new NavX();

		Mleft = new Talon(ElectricalLayout.DRIVE_LEFTMOTOR);
		Mright = new Talon(ElectricalLayout.DRIVE_RIGHTMOTOR);

		Eleft = new Encoder(ElectricalLayout.DRIVE_LEFTENCODER2,ElectricalLayout.DRIVE_LEFTENCODER, false, Encoder.EncodingType.k4X);
		Eright = new Encoder(ElectricalLayout.DRIVE_RIGHTENCODER,ElectricalLayout.DRIVE_RIGHTENCODER2, false, Encoder.EncodingType.k4X);

		Eleft.setDistancePerPulse(4/12.0*3.14/360);
		Eright.setDistancePerPulse(4/12.0*3.14/360);

		c = new TrajectoryDriveController("/home/lvuser/Turn.txt", 0, 0, 0, 1.0/14.6, 1.0/45.0, -0.02);

	}
/**
 * 
 * Returns distance (double)
 */

	public double getDistance() {

		double distance = ((-1*Eleft.getDistance()) + Eright.getDistance())/2;

		return distance;
	}
/**
 * 
 * Returns speed (double)
 */
	public double getSpeed() {
		return ((-1*Eleft.getRate()) + Eright.getRate())/2;
	}
/**
 * 
 * Returns angle (double)
 */
	public double getAngle(){
		double angle = NavX.getAngle();

		return angle;
	}
/**
 * 
 * Resets encoders and NavX
 */
	public void resetEncodersAndNavX(){
		Eleft.reset();
		Eright.reset();
		NavX.reset();
	}
/**
 * Set to auto driving
 */
	public void autoDrive() {
		resetEncodersAndNavX();
		c.reset();
		mode = Mode_Type.AUTO;
	}
/**
 * Two-controller driving
 */
	public void arcadeDrive( double speed, double turn) {
		Lspeed = speed + turn;
		Rspeed = -speed + turn;
		mode = Mode_Type.TELEOP;
	}
/**
 * Sets speed of both controllers
 */
	public void setLeftRightPower(double l, double r) {
		Lspeed = l;
		Rspeed = r;
		mode = Mode_Type.TELEOP;
	}
/**
 * Updates driving mode to either teleop or auto
 */
	public void update()
	{
		//DriverStation.reportError("distance = " + getDistance()+"\n", false);

		switch(mode) {
		case AUTO:

			double[] m = new double[2];

			m = c.getOutput(Eleft.getDistance(), Eright.getDistance(), getAngle()*3.14/180);

			DriverStation.reportError("the distance is" + getDistance(), false);

			Mleft.set(m[0]);
			Mright.set(-m[1]);
			break;

		case TELEOP:

			Mleft.set(Lspeed);
			Mright.set(Rspeed);

			break;

	}

}
}
