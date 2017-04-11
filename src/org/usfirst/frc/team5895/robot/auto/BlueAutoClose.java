package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.DriveTrain;
import org.usfirst.frc.team5895.robot.Intake;
import org.usfirst.frc.team5895.robot.Shooter;
import org.usfirst.frc.team5895.robot.Turret;
import org.usfirst.frc.team5895.robot.Vision;
import org.usfirst.frc.team5895.robot.framework.LookupTable;
import org.usfirst.frc.team5895.robot.framework.Waiter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BlueAutoClose {

	public static void run(DriveTrain drivetrain, Shooter shooter, Turret turret, LookupTable table, Vision vision, Intake intake) {
		
		turret.turnTo(-80);
		intake.open();
		drivetrain.auto_blue_closeDrive();
//		drivetrain.driveStraight(9.0);
		shooter.setSpeed(2950);
//		Waiter.waitFor(drivetrain::atDistance, 4500);
//		drivetrain.turnTo(-90);
//		Waiter.waitFor(drivetrain::atAngle, 3000);
//		drivetrain.driveStraight(7.6);
//		Waiter.waitFor(drivetrain::atDistance, 1500);
		Waiter.waitFor(drivetrain::isFinished, 4000);
		drivetrain.arcadeDrive(0, 0);
		Waiter.waitFor(200);
		turret.turnTo(turret.getAngle()+vision.getX() + 2);
//		shooter.setSpeed(table.get(vision.getDist()));
		Waiter.waitFor(shooter::atSpeed, 2000);
		if(shooter.getSpeed() > 10) {
			shooter.shoot();
		}
	}
}
