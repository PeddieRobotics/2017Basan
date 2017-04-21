package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.framework.Waiter;

import edu.wpi.first.wpilibj.Solenoid;

public class GearReceiver {

	private Solenoid flapSolenoid, dropSolenoid, pushSolenoid;
	private boolean flapState;
	private boolean dropState;
	private boolean pushState;

	public GearReceiver() {
		flapSolenoid = new Solenoid(ElectricalLayout.GEAR_FLAP_SOLENOID);
		dropSolenoid = new Solenoid(ElectricalLayout.GEAR_DROP_SOLENOID);
		pushSolenoid = new Solenoid(ElectricalLayout.SPARE);
	}

	/**
	 * Closes the gear receiver
	 */
	public void closeFlap() {
		flapState=false; 
	}

	/**
	 * Opens the gear receiver
	 */
	public void openFlap() {
		flapState=true;
	}
	
	public void closeGear(){
		dropState = false;
	}
	
	public void openGear(){
		dropState = true;
	}
	
	public void pushGear(){
		pushState = true;
	}
	
	public void pushBack(){
		pushState = false;
	}

	public void toggleHolder() {
		dropState = !dropState;
	}
	
	public void update() {
		if(flapSolenoid.get() != flapState){
			flapSolenoid.set(flapState);
		}
		if(dropSolenoid.get() != dropState) {
			dropSolenoid.set(dropState);
		}
		if(pushSolenoid.get() != pushState){
			pushSolenoid.set(pushState);
		}
	}

}
