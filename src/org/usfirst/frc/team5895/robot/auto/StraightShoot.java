package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.DriveTrain;
import org.usfirst.frc.team5895.robot.Shooter;
import org.usfirst.frc.team5895.robot.Turret;
import org.usfirst.frc.team5895.robot.Vision;
import org.usfirst.frc.team5895.robot.framework.LookupTable;
import org.usfirst.frc.team5895.robot.framework.Waiter;

import edu.wpi.first.wpilibj.DriverStation;

public class StraightShoot {
	
	public static void run(DriveTrain drivetrain, Turret turret, Shooter shooter, LookupTable table, Vision vision) {
		
		drivetrain.driveStraight(-8.0);
		shooter.setSpeed(3150);
		Waiter.waitFor(3000);
		drivetrain.setLeftRightPower(0, 0);
		Waiter.waitFor(200);
		turret.turnTo(turret.getAngle()+vision.getX());
		Waiter.waitFor(shooter::atSpeed, 2000);
		if(shooter.getSpeed() > 10) {
			shooter.shoot();
		}
	}

}
