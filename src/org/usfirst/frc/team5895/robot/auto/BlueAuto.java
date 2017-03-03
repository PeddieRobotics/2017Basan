package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.DriveTrain;
import org.usfirst.frc.team5895.robot.Shooter;
import org.usfirst.frc.team5895.robot.Turret;
import org.usfirst.frc.team5895.robot.framework.Waiter;

public class BlueAuto {

	public static void run(DriveTrain drivetrain, Shooter shooter, Turret turret) {
		
		shooter.setSpeed(3000);
		turret.turnTo(20);
		drivetrain.auto_blueDrive();
		Waiter.waitFor(7000);
		Waiter.waitFor(shooter::atSpeed, 200);
		shooter.shoot();
		
	}
	
}
