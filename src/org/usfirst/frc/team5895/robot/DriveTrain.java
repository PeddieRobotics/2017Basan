package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Talon;

public class DriveTrain {
	Talon left;
	Talon right;
	double Lspeed, Rspeed;
	
	public DriveTrain()
	{
		left = new Talon(1);
		right = new Talon(2);
	}
	public void arcadeDrive( double speed, double turn ){
		Lspeed = -speed + turn;
		Rspeed = speed + turn;
	}
	
	public void update()
	{
		left.set(Lspeed);
		right.set(Rspeed);
	}
	
}
