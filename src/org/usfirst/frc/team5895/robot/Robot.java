package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.framework.*;
import org.usfirst.frc.team5895.robot.lib.GripPipeline;
import org.usfirst.frc.team5895.robot.lib.trajectory.TextFileReader;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
public class Robot extends IterativeRobot {

	Looper u;//testing Daniel's Github
	Joystick Jleft, Jright;
	DriveTrain drivetrain;
	Intake intake;
	Climber climber;
	Vision GRIP;
	TextFileReader reader;
	
    public void robotInit() {
    	
    	Jleft = new Joystick(0);
    	Jright = new Joystick(1);
    	drivetrain = new DriveTrain();
    	GRIP= new Vision();
    	reader = new TextFileReader("/home/lvuser/FRC-2014/paths/CenterLanePathFar");
    	//intake = new Intake();
    	//climber = new Climber();
    	
    	u = new Looper(10);

    	
    	u.add(drivetrain::update);
    	u.add(GRIP::update);
    	//u.add(intake::update);
    	//u.add(climber::update);
    	u.start();
    	
    	DriverStation.reportError(reader.readWholeFile(), false);
    }
    
    public void autonomousInit() {
    	GRIP.autoLock();
    }
    
    public void teleopPeriodic() {
    	double speed=Jleft.getRawAxis(1);
    	double turn=-1*Jright.getRawAxis(0);
    	drivetrain.arcadeDrive(speed, turn);
    }
    
    public void disabledInit() {
    
    }  
    
    public void update() {
    	
    }
}