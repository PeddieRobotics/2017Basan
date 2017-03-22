package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.DriveTrain;
import org.usfirst.frc.team5895.robot.GearReceiver;
import org.usfirst.frc.team5895.robot.framework.Waiter;

public class BlueGear {
	
	public static void run(DriveTrain drivetrain, GearReceiver gear) {
		
		
		drivetrain.auto_gears_blueDrive();
		Waiter.waitFor(5000);
		drivetrain.arcadeDrive(0, 0);
	}
}