package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.auto.*;
import org.usfirst.frc.team5895.robot.framework.*;
import org.usfirst.frc.team5895.robot.lib.BetterJoystick;
import org.usfirst.frc.team5895.robot.lib.TrajectoryDriveController;
import org.usfirst.frc.team5895.robot.lib.trajectory.TextFileReader;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Robot extends IterativeRobot {

	Looper loop;
	BetterJoystick Jleft, Jright, Jsecond;
	DriveTrain drivetrain;
	Shooter shooter;
	Turret turret;
	Vision vision;
	GearReceiver gear;
	Climber climber;

    public void robotInit() {

    	Jleft = new BetterJoystick(0);
    	Jright = new BetterJoystick(1);
    	drivetrain = new DriveTrain();
    	shooter = new Shooter();
    	turret = new Turret();
    	vision = new Vision();
		gear = new GearReceiver();
		climber = new Climber();

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
		
    	//Main driver's controls
    	GearState();
		Shooting();
		FrontSide();
		HoodState();
		
		//Second driver's controls
		FlywheelOverride();
		ElevatorOverride();
		ClimberState();
    }

    public void disabledInit() {
    	drivetrain.arcadeDrive(0, 0);
    	shooter.setSpeed(0);
    }
    
    //From here on this is the joysticks controls of the main driver
    //Open or close the gear intake
	void GearState(){
			if(Jleft.getRisingEdge(0)){
				gear.open();
			}
			else if(Jleft.getRisingEdge(1)){
				gear.close();
			}
		}
	//if we are shooting or not
	void Shooting(){
		boolean isShooting = false;

		if(Jright.getRisingEdge(0)){
			isShooting = true;
		}else if(Jright.getFallingEdge(0)){
			isShooting = false;
		}

		while(isShooting){
			shooter.shoot();
		}
	}
	//what direction the robot is taking
	void FrontSide(){
		if(Jleft.getRisingEdge(2)){
			//front side is the intake
		}else if(Jleft.getRisingEdge(3)){
			//front side is the gear intake
		}
	}
	
	//hood is up or down
	void HoodState(){
		if(Jright.getRisingEdge(2)){
			shooter.hoodUp();
		}else if(Jright.getRisingEdge(3)){
			shooter.hoodDown();
		}
	}
	
	//From here the code is for the second driver
	//Set the flywheel to a certain speed
	void FlywheelOverride(){
		if(Jsecond.getRisingEdge(0)){
			shooter.setSpeed(.3); //random value can be changed
		}
	}
	
	void ElevatorOverride(){
		if(Jsecond.getRisingEdge(1)){
			shooter.setConveyorSpeed(.6);
		}
	}
	
	void ClimberState(){
		if(Jsecond.getRisingEdge(4)){
			climber.climb();
		}else if (Jsecond.getRisingEdge(5)){
			climber.stopClimbing();
		}
	}
}
