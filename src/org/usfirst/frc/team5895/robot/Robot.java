package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.auto.*;
import org.usfirst.frc.team5895.robot.framework.*;
import org.usfirst.frc.team5895.robot.lib.BetterJoystick;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Robot extends IterativeRobot {

	Looper loop, loopVision;
	BetterJoystick Jleft, Jright, Jsecond;
	DriveTrain drivetrain;
	Shooter shooter;
	Turret turret;
	GearReceiver gear;
	Climber climber;
	Vision vision;
	Intake tornado;
	LookupTable table;

	public void robotInit() {

		Jleft = new BetterJoystick(0);
		Jright = new BetterJoystick(1);
		drivetrain = new DriveTrain();
		shooter = new Shooter();
		turret = new Turret();
		gear = new GearReceiver();
		climber = new Climber();
		vision = new Vision();
		tornado = new Intake(); //The intake function now is used for the tornado thing to push the balls

		loop = new Looper(10);
		//loop.add(drivetrain::update);
		loop.add(tornado::update);
		//loop.add(shooter::update);
		loop.start();

		loopVision = new Looper(200);
		loopVision.add(vision::update);
		loopVision.add(this::Follow);
		loopVision.start();

    	double[] RPM = {3000, 3100, 3125, 3125, 3190, 3225, 3250, 3275, 3300, 3325, 3375, 3400, 3425, 3465, 3500};
    	double[] dist = {7.6, 8.25, 9, 9.5, 10, 10.5, 10.8, 11, 11.3, 11.5, 12, 12.5, 13, 13.5, 14};
    	try {
			table = new LookupTable(dist, RPM);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void autonomousInit() {
		String routine = SmartDashboard.getString("DB/String 0", "nothing");
		String gameplan = SmartDashboard.getString("DB/String 1", "nothing");
		String place=SmartDashboard.getString("DB/String 2", "nothing");
		
		drivetrain.setAutoGameplan(gameplan, place);

		if(routine.contains("blue")) {
			drivetrain.auto_blueDrive();
		}
		else if(routine.contains("red")) {
			drivetrain.auto_redDrive();
		}
		else {
			DoNothing.run();
		}
	}

	public void teleopPeriodic() {
		drivetrain.arcadeDrive(Jleft.getRawAxis(1), Jright.getRawAxis(0));
		
		
		//From here on this is the joysticks controls of the main driver
		//Open or close the gear intake
		if(Jleft.getRisingEdge(0)){
			gear.open();
		}
		else if(Jleft.getRisingEdge(1)){
			gear.close();
		}

		//if we are shooting or not
		if(Jright.getRisingEdge(1)){
			shooter.shoot();
			DriverStation.reportError(""+shooter.getSpeed(), false);
			tornado.down();
		}else if(Jright.getFallingEdge(1)){
			shooter.stopShoot();
			shooter.setSpeed(0);
			tornado.up();
		}

		//Climber State
		/*if(Jsecond.getRisingEdge(4)){
			climber.climb();
		}else if (Jsecond.getRisingEdge(5)){
			climber.stopClimbing();
		}*/
	}

	public void teleopInit() {
		drivetrain.arcadeDrive(0, 0);
		shooter.setSpeed(0);
		shooter.stopShoot();
		climber.stopClimbing();
		turret.turnTo(turret.getAngle());
		gear.close();
	}
	
	public void disabledInit() {
		drivetrain.arcadeDrive(0, 0);
		shooter.setSpeed(0);
		shooter.stopShoot();
		climber.stopClimbing();
		turret.turnTo(turret.getAngle());
		gear.close();
	}
	
	public void Follow(){
		turret.turnTo(turret.getAngle()-vision.getX());
	}
}
