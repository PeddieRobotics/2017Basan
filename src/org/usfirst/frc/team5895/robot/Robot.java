package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.auto.*;
import org.usfirst.frc.team5895.robot.framework.*;
import org.usfirst.frc.team5895.robot.lib.TrajectoryDriveController;
import org.usfirst.frc.team5895.robot.lib.trajectory.TextFileReader;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Robot extends IterativeRobot {

	Looper loop;
	Joystick Jleft, Jright;
	DriveTrain drivetrain;
	Shooter shooter;
	Turret turret;
	Vision vision;

    public void robotInit() {

    	Jleft = new Joystick(0);
    	Jright = new Joystick(1);
    	drivetrain = new DriveTrain();
    	shooter = new Shooter();
    	turret = new Turret();
    	vision = new Vision();

    	loop = new Looper(10);
    	loop.add(drivetrain::update);
    	loop.start();

    }

    public void autonomousInit() {
    	String routine = SmartDashboard.getString("DB/String 0", "nothing");
    	
    	if (routine.contains("spin")) {
    		SpinInCirclesShootingWildly.run(drivetrain, shooter, turret);
    	}
    	if(routine.contains("blue")) {
    		BlueAuto.run(drivetrain, shooter, turret);
    	}
    	if(routine.contains("red")) {
    		RedAuto.run(drivetrain, shooter, turret);
    	}
    	else {
    		DoNothing.run();
    	}
    }

    public void teleopPeriodic() {
    	drivetrain.arcadeDrive(Jleft.getRawAxis(1), Jright.getRawAxis(0));
    }

    public void disabledInit() {
    	drivetrain.arcadeDrive(0, 0);
    	shooter.setSpeed(0);
    }
}
