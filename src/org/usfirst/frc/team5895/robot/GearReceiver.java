package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class GearReceiver {

	private Solenoid myCylinder;
	private boolean isDown;

	public GearReceiver() {
		myCylinder = new Solenoid(ElectricalLayout.GEAR_SOLENOID);
		isDown = false;

	}

	/**
	 * Sets the gear receiver up
	 */
	public void up() {
		isDown = false;
	}

	/**
	 * Sets the gear receiver down
	 */
	public void down() {
		isDown = true;
	}

	public void update() {
		if(myCylinder.get() != isDown)
			myCylinder.set(isDown);
	}

}
