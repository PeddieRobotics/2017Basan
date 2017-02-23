package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.lib.PID;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Talon;

public class Flywheel {

	private Talon flywheelMotor;
	private double speed;

	PID PID;
	Counter Counter;

	double Kp = 0.24;
	double Ki = 0.00001;
	double Kd = 0.00000005;
	double dV = 1;


	public Flywheel()
	{
		flywheelMotor = new Talon(ElectricalLayout.FLYWHEEL_MOTOR);

		PID = new PID(Kp, Ki, Kd, dV);
		Counter = new Counter(ElectricalLayout.FLYWHEEL_COUNTER);
		Counter.setDistancePerPulse(1);
		
	}
	
	/**
	 * Shoot continuously
	 */
	public void shoot(){
		speed = 0.6;

	}
	
	/**
	 * Stop shooting
	 */
	public void stopShoot() {
		speed = 0;

	}
	
	/**
	 * Return the speed of the fly wheel in RPM
	 * @return The speed, in RPM, of the fly wheel
	 */
	public double getSpeed() {
		double flywheelSpeed = Counter.getRate()*60;
		return flywheelSpeed;
	}
	

	/**
	 * Sets the target RPM of the flywheel
	 * @param Setpoint angular speed set in RPM
	 */
	public void setSpeed(double setpoint) {
		PID.set(setpoint/60);
	}
	
	/**
	 * Tells whether flywheel speed is close to the setpoint
	 * @return Whether it's close or not
	 */
	
	public boolean atSpeed()
	{
		return ( getSpeed() < PID.getSetpoint() + 20 && getSpeed() > PID.getSetpoint() - 20 );
	
	}
	
	public void update() {
		flywheelMotor.set(PID.getOutput(Counter.getRate()));
	}
}
