package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.framework.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;


public class Robot extends IterativeRobot {

	Looper u;
	Joystick Jleft, Jright;
	Encoder encody;
	DriveTrain drivetrain;
	Intake intake;
	Climber climber;
	
    public void robotInit() {
    	
    	Jleft = new Joystick(1);
    	Jright = new Joystick(0);
    	encody = new Encoder(0,1);
    	drivetrain = new DriveTrain();
    	intake = new Intake();
    	climber = new Climber();
    	
    	u = new Looper(10);

    	
    	u.add(drivetrain::update);
    	u.add(intake::update);
    	u.add(climber::update);
    	u.start();
    }
    
    public void autonomousInit() {
    	
    }
    
    public void teleopPeriodic() {
    	encody.setDistancePerPulse(1);
    	double count=encody.getDistance();
    	DriverStation.reportError("Counter: "+count, false);
    	
    }
    
    public void disabledInit() {
    
    }  
    
    public void update() {
    	
    }
}