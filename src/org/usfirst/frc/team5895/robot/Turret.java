package org.usfirst.frc.team5895.robot;
import org.usfirst.frc.team5895.robot.lib.PID;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Turret {

	private Talon turret_motor;
	private PID PID;
	private Encoder turret_encoder;
	private DigitalInput limitSwitchLeft;
	private DigitalInput limitSwitchRight;
	
	public Turret(){
	
	turret_motor = new Talon(ElectricalLayout.TURRET_MOTOR);
	
	PID = new PID(0.012, 0.000003, 0.0000001, 1);

	turret_encoder = new Encoder(ElectricalLayout.TURRET_ENCODER, ElectricalLayout.TURRET_ENCODER2);
	turret_encoder.setDistancePerPulse(1);
	
	limitSwitchLeft = new DigitalInput(ElectricalLayout.LIMIT_SWITCHLEFT);
	limitSwitchRight = new DigitalInput(ElectricalLayout.LIMIT_SWITCHRIGHT);

	}

	public void turnTo(double angle){
		PID.set(angle);
	
	}
	
	
	public void update() {
		
		double output = PID.getOutput(turret_encoder.getDistance());
		
		if(limitSwitchLeft.get() && output > -0.25) {
			output = 0;
		}
		if(limitSwitchRight.get() && output < 0.25) {
			output = 0;
		}
		
		turret_motor.set(output);
		
	}
}
