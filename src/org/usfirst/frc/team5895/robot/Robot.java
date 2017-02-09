package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.framework.*;
import org.usfirst.frc.team5895.robot.lib.TrajectoryDriveController;
import org.usfirst.frc.team5895.robot.lib.trajectory.TextFileReader;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
public class Robot extends IterativeRobot {

	Looper loop, loop_vision;
	Joystick Jleft, Jright;
	DriveTrain drivetrain;
	Vision vision;

    public void robotInit() {

    	Jleft = new Joystick(0);
    	Jright = new Joystick(1);
    	drivetrain = new DriveTrain();
    	vision = new Vision();

    	loop = new Looper(10);
    	loop.add(drivetrain::update);
    	loop.start();

	    loop_vision = new Looper(250);
	    loop_vision.add(vision::update);
	    loop_vision.start();
    }

    public void autonomousInit() {
    }

    public void teleopPeriodic() {
    	drivetrain.arcadeDrive(Jleft.getRawAxis(1), Jright.getRawAxis(0));
    }

    public void disabledInit() {

    }
}
