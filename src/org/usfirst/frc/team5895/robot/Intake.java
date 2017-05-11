package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class Intake {

    private Solenoid expandSolenoid;
    private boolean isDown;

	public Intake() {
	    expandSolenoid = new Solenoid(ElectricalLayout.EXPAND_SOLENOID);
	    isDown = false;
	}
	
	/**
	 * Retracts hopper
	 */
	public void close() {
	   isDown = false;
	}
	
	/**
	 * Extends hopper
	 */
	public void open() {
	   isDown = true;
	}

	public void update() {
	   
	   if (expandSolenoid.get() != isDown)
	       expandSolenoid.set(isDown);
	}

}
