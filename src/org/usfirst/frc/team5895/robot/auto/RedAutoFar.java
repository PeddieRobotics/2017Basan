package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.DriveTrain;
import org.usfirst.frc.team5895.robot.Intake;
import org.usfirst.frc.team5895.robot.Shooter;
import org.usfirst.frc.team5895.robot.Turret;
import org.usfirst.frc.team5895.robot.Vision;
import org.usfirst.frc.team5895.robot.framework.LookupTable;
import org.usfirst.frc.team5895.robot.framework.Waiter;

public class RedAutoFar {
	
	public static void run(DriveTrain drivetrain, Shooter shooter, Turret turret, LookupTable table, Vision vision, Intake intake) {
	
		turret.turnTo(80);
		Waiter.waitFor(1000);
/*		intake.down();
		drivetrain.auto_red_farDrive();
		Waiter.waitFor(4000);
		drivetrain.arcadeDrive(0, 0);
*/		vision.update();
		turret.turnTo(turret.getAngle()+vision.getX()-1);
		Waiter.waitFor(200);
		vision.update();
		turret.turnTo(turret.getAngle()+vision.getX()-1);
		shooter.setSpeed(3250);
//		shooter.setSpeed(table.get(vision.getDist()));
		Waiter.waitFor(shooter::atSpeed, 2000);
		if(shooter.getSpeed() > 10) {
			shooter.shoot();
		}
	}

}
