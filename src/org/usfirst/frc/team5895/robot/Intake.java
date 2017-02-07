package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class Intake {

    private Talon motor;
    private double speed;
    private Solenoid myCylinder;
    private boolean isDown;

	public Intake() {
	    motor = new Talon(2);
	    speed = 0;
	    myCylinder = new Solenoid(INTAKE_SOLENOID);
	    isDown = false;
	}

	public void up() {
	   isDown = false;
	   speed = 0.0;
	}

	public void down() {
	   isDown = true;
	   speed = 0.5;
	}

	public void update() {
	   //controlCurrent(); don't need this for Intake
	   motor.set(speed);
	   if (myCylinder.get() != isDown)
	       myCylinder.set(isDown);
	}

}
