package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;
import org.usfirst.frc.team5895.robot.lib.PID;
import org.usfirst.frc.team5895.robot.lib.TrajectoryDriveController;

public class Shooter {

	private Talon flywheelMotor;
	private Talon conveyorMotor;
	private Talon tornadoMotor;
	private double conveyorSpeed;
	private double tornadoSpeed;
	private boolean noSpeed;

	PowerDistributionPanel pdp;
	PID PID;
	Counter Counter;

	double Kp = 0.24;
	double Ki = 0.000018;
	double Kd = 0.00000005;
	double dV = 1;

	public Shooter()
	{
		pdp = new PowerDistributionPanel();
		flywheelMotor = new Talon(ElectricalLayout.FLYWHEEL_MOTOR);
		conveyorMotor = new Talon(ElectricalLayout.CONVEYOR_MOTOR);
		tornadoMotor = new Talon(ElectricalLayout.TORNADO_MOTOR);

		PID = new PID(Kp, Ki, Kd, dV, false);
		Counter = new Counter(ElectricalLayout.FLYWHEEL_COUNTER);
		Counter.setDistancePerPulse(1);
		
		noSpeed = false;
	}
	
	/**
	 * Shoot continously
	 */
	public void shoot(){
		conveyorSpeed = 0.6;
		tornadoSpeed = 1;
		
	}
	
	/**
	 * Stop shooting
	 */
	public void stopShoot() {
		conveyorSpeed = 0;
		tornadoSpeed = 0;
	}
	
	/**
	 * Conveyor goes in reverse
	 */
	public void reverse() {
		conveyorSpeed = -0.6;
		tornadoSpeed = -1;
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
		if(setpoint == 0) {
			noSpeed = true;
		} else noSpeed = false;
		PID.set(setpoint/60);
	}
	
	/**
	 * Tells whether flywheel speed is close to the setpoint
	 * @return Whether it's close or not
	 */
	
	public boolean atSpeed()
	{
		return ( getSpeed() < PID.getSetpoint()*60 + 30 && getSpeed() > PID.getSetpoint()*60 - 30 );	
	}
	
	public void update() {
		DriverStation.reportError("The speed is" + getSpeed(), false);
		
		double output = -PID.getOutput(Counter.getRate());
		if(output > 0) {
			output = 0;
			PID.resetIntegral();
		}
		if(noSpeed == true) {
			flywheelMotor.set(0);
		} else
			flywheelMotor.set(output);
			conveyorMotor.set(conveyorSpeed);
			tornadoMotor.set(tornadoSpeed);
	}
}

