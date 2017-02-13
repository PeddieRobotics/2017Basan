package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.DriveTrain;
import org.usfirst.frc.team5895.robot.Shooter;
import org.usfirst.frc.team5895.robot.Turret;
import org.usfirst.frc.team5895.robot.framework.Waiter;

public class SpinInCirclesShootingWildly {

	public static void run(DriveTrain drivetrain, Shooter shooter, Turret turret) {
		//spin flywheel up to 3000RPM
		shooter.setSpeed(3000);
		
		//spin in place for 3 seconds
		drivetrain.arcadeDrive(0, 1.0);
		Waiter.waitFor(3000);
		drivetrain.arcadeDrive(0, 0);
		
		//turn turret
		turret.turnTo(20);
		
		//Wait for either 200ms or for turret to reach correct angle, whichever comes first
		//Waiter.waitFor(turret::atAngle, 200);
		
		//Wait for either 500ms or for flywheel to be at correct speed, whichever comes first
		Waiter.waitFor(shooter::atSpeed, 500);
		
		//Shoot!
		shooter.shoot();
	}
}
