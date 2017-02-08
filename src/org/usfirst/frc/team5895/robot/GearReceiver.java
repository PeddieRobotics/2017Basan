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
	 * sets the gear receiver up in the position of holding the gear 
	 * (has the gear)
	 */
	public void up() {
		isDown = false;
	}

	/**
	 * sets the gear receiver down in the position to receive the gear 
	 * (no gear)
	 */
	public void down() {
		isDown = true;

	}

	public void update() {

		if(myCylinder.get() != isDown)
			myCylinder.set(isDown);

	}

}
