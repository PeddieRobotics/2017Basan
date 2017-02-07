package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
import org.usfirst.frc.team5895.robot.lib.PID;

public class Shooter {

	private Talon flywheelMotor;
	private Talon conveyorMotor;
	private double speed;
	
	PID PID;
	Counter Counter;

	double Kp = 0.05;


	public Shooter()
	{
		flywheelMotor = new Talon(FLYWHEEL_MOTOR);
		conveyorMotor = new Talon(CONVEYOR_MOTOR);

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
		double flywheelSpeed = (flywheelMotor.getSpeed())/60;
		return flywheelSpeed;
	}

	public double getSpeedMotor2() {
		double conveyorSpeed = (conveyorMotor.getSpeed())/60;
		return conveyorSpeed;
	}

	public void setSpeed(double setpoint) {
		PID.set(setpoint/60);
	}

	public void update() {
		flywheelMotor.set(PID.getOutput(Counter.getRate()));
		conveyorMotor.set(speed);
	}

}
