package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.framework.*;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;


public class Robot extends IterativeRobot {

	Looper u;
	Joystick Jleft, Jright;
	DriveTrain drivetrain;
	Intake intake;
	Climber climber;
	
    public void robotInit() {
    	
    	Jleft = new Joystick(0);
    	Jright = new Joystick(1);
    	drivetrain = new DriveTrain();
    	//intake = new Intake();
    	//climber = new Climber();
    	
    	u = new Looper(10);

    	
    	u.add(drivetrain::update);
    	//u.add(intake::update);
    	//u.add(climber::update);
    	u.start();
    }
    
    public void autonomousInit() {
    	drivetrain.autoDrive();
    	
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