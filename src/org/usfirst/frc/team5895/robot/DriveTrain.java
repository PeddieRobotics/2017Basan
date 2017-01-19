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
	double dist;
	
	public DriveTrain()
	{
		Mleft = new Talon(1);
		Mright = new Talon(0);
		
		Eleft = new Encoder(2,3);
		Eright = new Encoder(0,1);
		
		Eleft.setDistancePerPulse(4*3.14/360);
		Eright.setDistancePerPulse(4*3.14/360);
		
	}
	
	public double getDistance() {
	
		double distance = ((-1*Eleft.getDistance()) + Eright.getDistance())/2;
		
		return distance;
	}
	
	public void autoDrive() {

		dist = getDistance();
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
			
			if(getDistance() - dist >= 72)
			{
				Mleft.set(0.0);
				Mright.set(0.0);
			}
			else
			{
				Mleft.set(-0.35);
				Mright.set(0.35);
			}
			
			break;
			
		case TELEOP:
			Mleft.set(Lspeed);
			Mright.set(Rspeed);
			
			break;
		
	}
	
}
}
