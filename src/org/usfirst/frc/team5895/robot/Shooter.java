package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
import org.usfirst.frc.team5895.robot.lib.PID;

public class Shooter {

	private Talon m1;
	private Talon m2;
	private double speed;
	
	PID PID;
	Counter Counter;
	   
	double Kp = 0.05;

	 
	public Shooter()
	{
		m1 = new Talon(0); //using PID
		m2 = new Talon(1); //hopper to the shooter ?
		
		Counter = new Counter(0);
	}
	
	public void shoot(){
		speed = 0.6;
		
		if(m2.getSpeed() > 1)
			stopShoot();

	}
	
	public void stopShoot() {
		speed = 0;
		
	}
	
	public void update() {
		PID.set(10);
		m1.set(PID.getOutput(Counter.getRate()));
		m2.set(speed);
	}
	
}
