package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.DriveTrain;
import org.usfirst.frc.team5895.robot.GearReceiver;
import org.usfirst.frc.team5895.robot.framework.Waiter;
import org.usfirst.frc.team5895.robot.lib.TrajectoryDriveController;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;

public class AutoInGeneral {
	private TrajectoryDriveController cController;

	public double[] run(DriveTrain drivetrain, GearReceiver gear, TrajectoryDriveController contr, double l, double r, double angle) {
		cController=contr;
		//Gear side is front
		double[] outputs = new double[2];
		outputs=cController.getOutput(l, r, angle);

		//DriverStation.reportError("the angle is" + getAngle(), false);
		return outputs;
	}
}