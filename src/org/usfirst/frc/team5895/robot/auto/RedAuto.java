package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.DriveTrain;
import org.usfirst.frc.team5895.robot.Shooter;
import org.usfirst.frc.team5895.robot.Turret;
import org.usfirst.frc.team5895.robot.Vision;
import org.usfirst.frc.team5895.robot.framework.LookupTable;
import org.usfirst.frc.team5895.robot.framework.Waiter;

public class RedAuto {

	public static void run(DriveTrain drivetrain, Shooter shooter, Turret turret, LookupTable table, Vision vision) {

		turret.turnTo(70);
		drivetrain.auto_balls_redDrive();
		Waiter.waitFor(drivetrain::trajectoryFinished,5000);
		turret.turnTo(turret.getAngle()+vision.getX());
		shooter.setSpeed(table.get(vision.getDist()));
		Waiter.waitFor(shooter::atSpeed, 2000);
		if(shooter.getSpeed()>0){
			shooter.shoot();
		}
	}

}
