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
	
	
	public DriveTrain()
	{
		Mleft = new Talon(1);
		Mright = new Talon(0);
		
		Eleft = new Encoder(2,3);
		Eright = new Encoder(0,1);
		
	}
	
	public double getDistance() {
		
		Eleft.setDistancePerPulse(1);
		Eright.setDistancePerPulse(1);

		double distance = ((-1*Eleft.getDistance()) + Eright.getDistance())/2;
		
		return distance;
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
			break;
			
		case TELEOP:
			Mleft.set(Lspeed);
			Mright.set(Rspeed);
			
			break;
		
	}
	
}
}
