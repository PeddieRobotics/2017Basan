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
	 * Closes the gear flap
	 */
	public void closeFlap() {
		flapState=false; 
	}

	/**
	 * Opens the gear flap
	 */
	public void openFlap() {
		flapState=true;
	}
	
	/**
	 * Closes the gear drop
	 */
	public void closeGear(){
		dropState = false;
	}
	
	/**
	 * opens the gear drop
	 */
	public void openGear(){
		dropState = true;
	}
	
	/**
	 * pushes gear forward
	 */
	public void pushGear(){
		pushState = true;
	}
	
	/**
	 * puts back the gear push
	 */
	public void pushBack(){
		pushState = false;
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
