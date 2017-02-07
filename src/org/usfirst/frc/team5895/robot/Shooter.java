package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
import org.usfirst.frc.team5895.robot.lib.PID;

public class Shooter {

	private Talon motor1;
	private Talon motor2;
	private double speed;
	
	PID PID;
	Counter Counter;
	   
	double Kp = 0.05;

	 
	public Shooter()
	{
		motor1 = new Talon(0); 
		motor2 = new Talon(1); 
		
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
	
	public double getSpeedMotor1() {
		double m1speed = (motor1.getSpeed())/60;
		return m1speed;	
	}
	
	public double getSpeedMotor2() {
		double m2speed = (motor2.getSpeed())/60;
		return m2speed;	
	}
	
	public void setSpeed(double setpoint) {
		PID.set(setpoint/60);
	}
	
	public void update() {
		motor1.set(PID.getOutput(Counter.getRate()));
		motor2.set(speed);
	}
	
}
