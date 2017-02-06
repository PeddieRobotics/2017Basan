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
		m1 = new Talon(0); 
		m2 = new Talon(1); 
		
		PID = new PID(Kp, 0, 0, 0);
		Counter = new Counter(0);
		Counter.setDistancePerPulse(1);
	
	}
	
	public void shoot(){
		speed = 0.6;

	}
	
	public void stopShoot() {
		speed = 0;
		
	}
	
	public void setSpeed(double setpoint) {
		PID.set(setpoint/60);
		
	}
	
	public void update() {
		
		m1.set(PID.getOutput(Counter.getRate()));
		m2.set(speed);
	}
	
}
