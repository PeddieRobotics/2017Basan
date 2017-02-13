package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class GearReceiver {

	private Solenoid myCylinder;
	private boolean isClosed;

	public GearReceiver() {
		myCylinder = new Solenoid(ElectricalLayout.GEAR_SOLENOID);
		isClosed = false;

	}

	/**
	 * Closes the gear receiver
	 */
	public void close() {
		isClosed = false;
	}

	/**
	 * Opens the gear receiver
	 */
	public void open() {
		isClosed = true;
	}

	public void update() {
		if(myCylinder.get() != isClosed)
			myCylinder.set(isClosed);
	}

}
