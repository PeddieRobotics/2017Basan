package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class Intake {

    private Solenoid myCylinder;
    private boolean isDown;

	public Intake() {
	    myCylinder = new Solenoid(ElectricalLayout.INTAKE_SOLENOID);
	    isDown = false;
	}
	
	/**
	 * Lift intake up and stop taking balls
	 */
	public void up() {
	   isDown = false;
	}
	
	/**
	 * Puts the intake down and start taking balls
	 */
	public void down() {
	   isDown = true;
	}

	public void update() {
	   
	   if (myCylinder.get() != isDown)
	       myCylinder.set(isDown);
	}

}
