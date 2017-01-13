package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;

public class Climber {
	private TalonSRX theMotor;
	private Joystick theJoystick;
	private enum Mode_Type {FIRSTSPIKE, CLIMBING, CHILLING}
	private Mode_Type mode= Mode_Type.FIRSTSPIKE;
	private PowerDistributionPanel pdp;
	
	
	public Climber(){
		theMotor = new TalonSRX(0);
		theJoystick = new Joystick(0);
		pdp = new PowerDistributionPanel();
	}
	
	private double getThatTime(){
		double firstSpikeTime=Timer.getFPGATimestamp();
		return firstSpikeTime;
	}
	
	private double getCurrent(){
		double current = pdp.getCurrent(0);
		return current;
	}
	
	public void update(){
		switch(mode){
		
		case FIRSTSPIKE:
			theMotor.set(1);
			if(Timer.getFPGATimestamp()-getThatTime() >= 0.5){
				mode = Mode_Type.CLIMBING;
			}
			break;
		
		case CLIMBING:
			theMotor.set(0.5);
			if(getCurrent()>=40 || theJoystick.getRawButton(1)){
				mode = Mode_Type.CHILLING;
			}
			break;
			
		case CHILLING:
			theMotor.set(0);
			break;
		}
	}
}
