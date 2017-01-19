package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class DriveTrain {
	private Talon Mleft;
	private Talon Mright;
	double Lspeed, Rspeed;
	private enum Mode_Type {TELEOP, AUTO};
	private Mode_Type mode = Mode_Type.AUTO;
	private Encoder Eleft, Eright;
	private double dist;
	private final double Kp;
	private double setpoint;
	private double error;
	private PID PID;
	
	public DriveTrain()
	{
		Mleft = new Talon(1);
		Mright = new Talon(0);
		
		Eleft = new Encoder(2,3);
		Eright = new Encoder(0,1);
		
		Eleft.setDistancePerPulse(4*3.14/360);
		Eright.setDistancePerPulse(4*3.14/360);
		
		PID = new PID(1, 0, 0, 1);
		
	}
	

	
	public double getDistance() {
	
		double distance = ((-1*Eleft.getDistance()) + Eright.getDistance())/2;
		
		return distance;
	}
	
	public void autoDrive() {

		dist = getDistance();
		
		PID.set(72);
		
		mode = Mode_Type.AUTO;
	}
	
	public void arcadeDrive( double speed, double turn) {
		Lspeed = speed + turn;
		Rspeed = -speed + turn;
		mode = Mode_Type.TELEOP;
	}
	
	public void update()
	{
		DriverStation.reportError("distance = " + getDistance(), false);
		
		switch(mode) {
		case AUTO:
			double output = PID.getOutput(dist);
			Mleft.set(-output);
			Mright.set(output);
			
			break;
			
		case TELEOP:
			Mleft.set(Lspeed);
			Mright.set(Rspeed);
			
			break;
		
	}
	
}
}
