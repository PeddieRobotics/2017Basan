package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.DriveTrain;
import org.usfirst.frc.team5895.robot.GearReceiver;
import org.usfirst.frc.team5895.robot.framework.Waiter;

public class BlueGear {
	
	public static void run(DriveTrain drivetrain, GearReceiver gear) {
		
		//Gear side is front
		drivetrain.driveTo(-125);
		Waiter.waitFor(drivetrain::atDistance, 5000.0);
		drivetrain.turnTo(60);
		Waiter.waitFor(drivetrain::atAngle, 5000);
		gear.open();
		drivetrain.driveTo(-10);
		
	}

}