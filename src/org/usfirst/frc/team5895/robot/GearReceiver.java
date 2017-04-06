package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.framework.Waiter;

import edu.wpi.first.wpilibj.Solenoid;

public class GearReceiver {

	private Solenoid gateSol, holdingSol;
	private boolean gateState;
	private boolean holdingState;

	public GearReceiver() {
		gateSol = new Solenoid(ElectricalLayout.GEAR_SOLENOID);
		holdingSol = new Solenoid(ElectricalLayout.GEAR_HOLDER);
	}

	/**
	 * Closes the gear receiver
	 */
	public void closeFlap() {
		gateState=false; 
	}

	/**
	 * Opens the gear receiver
	 */
	public void openFlap() {
		gateState=true;
	}
	
	public void closeGear(){
		holdingState = false;
	}
	
	public void openGear(){
		holdingState = true;
	}

	public void toggleHolder() {
		holdingState = !holdingState;
	}
	
	public void update() {
		if(gateSol.get() != gateState){
			gateSol.set(gateState);
		}
		if(holdingSol.get() != holdingState) {
			holdingSol.set(holdingState);
		}
	}

}
