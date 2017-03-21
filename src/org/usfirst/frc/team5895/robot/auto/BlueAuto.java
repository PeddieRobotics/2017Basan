package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.DriveTrain;
import org.usfirst.frc.team5895.robot.Intake;
import org.usfirst.frc.team5895.robot.Shooter;
import org.usfirst.frc.team5895.robot.Turret;
import org.usfirst.frc.team5895.robot.Vision;
import org.usfirst.frc.team5895.robot.framework.LookupTable;
import org.usfirst.frc.team5895.robot.framework.Waiter;

import edu.wpi.first.wpilibj.DriverStation;

public class BlueAuto {

	public static void run(DriveTrain drivetrain, Shooter shooter, Turret turret, LookupTable table, Vision vision, Intake intake) {
		
		turret.turnTo(-80);
		intake.down();
		drivetrain.auto_blueDrive();
		Waiter.waitFor(5000);
		drivetrain.arcadeDrive(0, 0);
		vision.update();
		turret.turnTo(turret.getAngle()+vision.getX());
		shooter.setSpeed(table.get(vision.getDist()));
		Waiter.waitFor(shooter::atSpeed, 2000);
		if(shooter.getSpeed() > 10) {
			shooter.shoot();
		}
	}
	
}
