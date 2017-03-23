package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.DriveTrain;
import org.usfirst.frc.team5895.robot.GearReceiver;
import org.usfirst.frc.team5895.robot.framework.Waiter;

public class RedGear {
		
	public static void run(DriveTrain drivetrain, GearReceiver gear) {
		
		drivetrain.auto_gears_redDrive();
		Waiter.waitFor(5000);
		drivetrain.arcadeDrive(0, 0);
	}
}