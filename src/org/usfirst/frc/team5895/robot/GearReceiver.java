package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class GearReceiver {

	private Solenoid gateSol, holdingSol;
	private boolean gateState;

	public GearReceiver() {
		gateSol = new Solenoid(ElectricalLayout.GEAR_SOLENOID);
		holdingSol = new Solenoid(ElectricalLayout.GEAR_HOLDER);
	}

	/**
	 * Closes the gear receiver
	 */
	public void close() {
		gateState=false;
	}

	/**
	 * Opens the gear receiver
	 */
	public void open() {
		gateState=true;
	}

	public void update() {
		if(gateSol.get() != gateState){
			gateSol.set(gateState);
			holdingSol.set(!gateState);
		}
	}

}
