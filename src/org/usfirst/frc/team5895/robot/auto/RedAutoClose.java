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

public class RedAutoClose {

	public static void run(DriveTrain drivetrain, Shooter shooter, Turret turret, LookupTable table, Vision vision, Intake intake) {
		
		turret.turnTo(80);
		intake.open();
		drivetrain.auto_red_closeDrive();
		Waiter.waitFor(4000);
		drivetrain.arcadeDrive(0, 0);
		vision.update();
		turret.turnTo(turret.getAngle()+vision.getX());
		Waiter.waitFor(200);
		vision.update();
		turret.turnTo(turret.getAngle()+vision.getX());
		shooter.setSpeed(3240);
//		shooter.setSpeed(table.get(vision.getDist()));
		Waiter.waitFor(shooter::atSpeed, 2000);
		if(shooter.getSpeed() > 10) {
			shooter.shoot();
		}
	}
}