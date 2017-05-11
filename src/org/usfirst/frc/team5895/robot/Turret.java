package org.usfirst.frc.team5895.robot;
import org.usfirst.frc.team5895.robot.lib.BetterEncoder;
import org.usfirst.frc.team5895.robot.lib.PID;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Turret {

	private Talon turret_motor; //left is positive
	private PID PID;
	private Encoder turret_encoder;
	double output;
	
	/**
	 * Creates a Turret Object and sets initial values
	 * 
	 */
	public Turret(){
	
	turret_motor = new Talon(ElectricalLayout.TURRET_MOTOR);
	
	PID = new PID(0.012, 0.00003, 0.0000001, false);

	turret_encoder = new Encoder(ElectricalLayout.TURRET_ENCODER, ElectricalLayout.TURRET_ENCODER2,  true, Encoder.EncodingType.k4X);
	turret_encoder.setDistancePerPulse(1.0/13);

	}

	/**
	 * Sets the turret's PID to turn to a desired angle
	 * 
	 * @param angle Angle that the turret should be set to
	 */
	public void turnTo(double angle){
		
		if (angle > 90) {
			angle = 90;
		}
		
		if (angle < -90) {
			angle = -90;
		}
		
		PID.set(angle);
	}
	
	/**
	 * Returns whether the turret's angle is within the correct range or not
	 * 
	 * @return If turret is within angle range or not
	 */
	public boolean atAngle() {
		return ((getAngle() < PID.getSetpoint()+ 0.25) && (getAngle() > PID.getSetpoint() - 0.25));
	}
	
	/**
	 * Returns the angle of the turret
	 * 
	 * @return Angle of turret
	 */
	public double getAngle() {
		return turret_encoder.getDistance();
	}
	
		
	/**
	 * Regulates the desired speed of the turret and updates it constantly
	 * 
	 */
	public void update() {
	
		output = PID.getOutput(turret_encoder.getDistance());
		
		if (output > 0.25) {
			output = 0.25;
		}
		
		if (output < -0.25) {
			output = -0.25;
		}
		
		turret_motor.set(output);
	}
}
