package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Counter;
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
		flywheelMotor = new Talon(ElectricalLayout.FLYWHEEL_MOTOR);
		conveyorMotor = new Talon(ElectricalLayout.CONVEYOR_MOTOR);

		PID = new PID(Kp, 0, 0, 0);
		Counter = new Counter(ElectricalLayout.FLYWHEEL_COUNTER);
		Counter.setDistancePerPulse(1);

	}
	
	/**
	 * shoots the ball
	 */
	public void shoot(){
		speed = 0.6;

	}
	
	/**
	 * stop shooting the ball
	 */
	public void stopShoot() {
		speed = 0;

	}
	
	/**
	 * return the speed of the fly wheel in RPM
	 * @return the speed, in RPM, of the fly wheel
	 */
	public double getSpeed() {
		double flywheelSpeed = Counter.getRate()/60;
		return flywheelSpeed;
	}
	

	/**
	 * sets the setpoint for the PID used for the fly wheel (in RPM)
	 * @param setpoint angular speed set in RPM
	 */
	public void setSpeed(double setpoint) {
		PID.set(setpoint/60);
	}

	public void update() {
		flywheelMotor.set(PID.getOutput(Counter.getRate()));
		conveyorMotor.set(speed);
	}
	
	public boolean atSpeed()
	{
		if( getSpeed() < PID.getSetpoint() + 20 && getSpeed() > PID.getSetpoint() - 20 )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}

