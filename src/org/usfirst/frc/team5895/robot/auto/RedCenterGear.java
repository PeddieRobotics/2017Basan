package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.DriveTrain;
import org.usfirst.frc.team5895.robot.GearReceiver;
import org.usfirst.frc.team5895.robot.Shooter;
import org.usfirst.frc.team5895.robot.Turret;
import org.usfirst.frc.team5895.robot.Vision;
import org.usfirst.frc.team5895.robot.framework.LookupTable;
import org.usfirst.frc.team5895.robot.framework.Waiter;

import edu.wpi.first.wpilibj.DriverStation;

public class RedCenterGear {
	
	public static void run(DriveTrain drivetrain, GearReceiver gear, LookupTable redTable, Turret turret, Shooter shooter, Vision vision) {

		drivetrain.auto_center_gearDrive();
		Waiter.waitFor(drivetrain::isFinished, 4000);
		drivetrain.arcadeDrive(0, 0);
		gear.openGear();
		Waiter.waitFor(200);
		gear.pushGear();
		Waiter.waitFor(500);
		gear.pushBack();
		Waiter.waitFor(100);
		gear.pushGear();
		Waiter.waitFor(500);
		drivetrain.auto_center_gear_redDrive();
		shooter.setSpeed(3200);
		Waiter.waitFor(drivetrain::isFinished, 4000);
		drivetrain.arcadeDrive(0, 0);
		gear.closeGear();
		gear.pushBack();
		turret.turnTo(turret.getAngle() + vision.getX() - 2);
		Waiter.waitFor(200);
		Waiter.waitFor(shooter::atSpeed, 2000);
		if(shooter.getSpeed() > 10) {
			shooter.shoot();
//			DriverStation.reportError("" + shooter.getSpeed(), false);
		}
	}
}
