package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import org.usfirst.frc.team5895.robot.lib.PID;

public class Shooter {

	private Talon flywheelMotor;
	private Talon conveyorMotor;
	private Solenoid hood;
	private double speed;
	boolean hoodPosition;

	PID PID;
	Counter Counter;

	double Kp = 0.2;
	double Ki = 0.00001;
	double Kd = 0.00000005;
	double dV = 1;


	public Shooter()
	{
		flywheelMotor = new Talon(ElectricalLayout.FLYWHEEL_MOTOR);
		conveyorMotor = new Talon(ElectricalLayout.CONVEYOR_MOTOR);
		hood = new Solenoid(ElectricalLayout.FLYWHEEL_SOLENOID);

		PID = new PID(Kp, Ki, Kd, dV);
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
	 * conveyor goes in reverse
	 */
	
	public void reverse() {
		speed = -0.6;
	}
	
	/**
	 * hood is on the big angle
	 */
	public void hoodUp(){
		hoodPosition = true; 
	}
	
	/**
	 * hood is on the small angle
	 */
	public void hoodDown(){
		hoodPosition = false;
	}
	
	/**
	 * return the speed of the fly wheel in RPM
	 * @return the speed, in RPM, of the fly wheel
	 */
	public double getSpeed() {
		double flywheelSpeed = Counter.getRate()*60;
		return flywheelSpeed;
	}
	

	/**
	 * sets the setpoint for the PID used for the fly wheel (in RPM)
	 * @param setpoint angular speed set in RPM
	 */
	public void setSpeed(double setpoint) {
		PID.set(setpoint/60);
	}
	
	/**
	 * tells whether flywheel is within 20 rpm of the setpoint
	 * @return whether it's close or not
	 */
	
	public boolean atSpeed()
	{
		return ( getSpeed() < PID.getSetpoint() + 20 && getSpeed() > PID.getSetpoint() - 20 );
	
	}
	
	public void update() {
		flywheelMotor.set(PID.getOutput(Counter.getRate()));
		conveyorMotor.set(speed);
		hood.set(hoodPosition);
	}
}

