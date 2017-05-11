package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.auto.*;
import org.usfirst.frc.team5895.robot.framework.*;
import org.usfirst.frc.team5895.robot.lib.BetterJoystick;
import org.usfirst.frc.team5895.robot.framework.LookupTable;

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
	Intake intake;
	Vision vision;
	LookupTable redTable;
	LookupTable blueTable;
	boolean shooting = false;
	boolean autoAim = false;
	Recorder recorder;
	
	public void robotInit() {

		Jleft = new BetterJoystick(0);
		Jright = new BetterJoystick(1);
		Jsecond = new BetterJoystick(2);
		drivetrain = new DriveTrain();
		shooter = new Shooter();
		turret = new Turret();
		gear = new GearReceiver();
		climber = new Climber();
		intake = new Intake();
		vision = new Vision();
		recorder = new Recorder(drivetrain);
		
		
		loop = new Looper(10);
		loop.add(drivetrain::update);
		loop.add(shooter::update);
		loop.add(gear::update);
		loop.add(turret::update);
		loop.add(climber::update);
		loop.add(intake::update);
		loop.add(recorder::record);
		loop.start();

		loopVision = new Looper(200);
		loopVision.add(this::follow);
		loopVision.start();
		
		//lookup tables
		double[] redDist = {0, 6.3, 7, 8, 9, 15};
		double[] redRPM = {2800, 2950, 2990, 3095, 3230, 3300};
		double[] blueDist = {0, 6.2, 6.5, 6.9, 7.5, 8.5, 9.7, 15};
		double[] blueRPM = {2800, 2900, 2950, 2950, 3050, 3200, 3675, 3800};
		try {
			redTable = new LookupTable(redDist, redRPM);
			blueTable = new LookupTable(blueDist, blueRPM);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void autonomousInit() {
	//	recorder.startRecording("Auto.csv");
		autoAim = false;
		
		String routine = SmartDashboard.getString("DB/String 0", "nothing");
		String gameplan = SmartDashboard.getString("DB/String 1", "nothing");
		String distance = SmartDashboard.getString("DB/String 2", "nothing");
		
		if(routine.contains("blue")) {
			if(gameplan.contains("balls")){
				if(distance.contains("close")) {
					BlueAutoClose.run(drivetrain, shooter, turret, blueTable, vision, intake);
				} else if(distance.contains("far")) {
					BlueAutoFar.run(drivetrain, shooter, turret, blueTable, vision, intake);
				} else {
					BlueAuto.run(drivetrain, shooter, turret, blueTable, vision, intake);
				}
			}
			else if(gameplan.contains("gear")){
				if(distance.contains("center")) {
					BlueCenterGear.run(drivetrain, gear, blueTable, turret, shooter, vision);
				} else {
				BlueGear.run(drivetrain, gear, turret, shooter, vision);
				}	
			}	
			else{
				DoNothing.run();
			}
		}
		
		if(routine.contains("red")) {
			if(gameplan.contains("balls")){
				if(distance.contains("close")){
					RedAutoClose.run(drivetrain, shooter, turret, redTable, vision, intake);
				} else if(distance.contains("far")) {
					RedAutoFar.run(drivetrain, shooter, turret, redTable, vision, intake);
				} else{
					RedAuto.run(drivetrain, shooter, turret, redTable, vision, intake);
				}
			}
			else if(gameplan.contains("gear")){
				if(distance.contains("center")) {
					RedCenterGear.run(drivetrain, gear, redTable, turret, shooter, vision);
				}
				else {
				RedGear.run(drivetrain, gear, turret, shooter, vision);
			}
			}
		}
		
		else if(routine.contains("straight")) {
			if(gameplan.contains("balls")) {
				StraightShoot.run(drivetrain, turret, shooter, redTable, vision);
			}
		}
		else if(routine.contains("center")) {
			if(gameplan.contains("gear")) {
				CenterGear.run(drivetrain, gear, turret, shooter, vision);
			}
		}
		else {
			DoNothing.run();
		}
	}

	public void teleopPeriodic() {		
		//normal teleop drive
		drivetrain.arcadeDrive(Jleft.getRawAxis(1), -Jright.getRawAxis(0));
		
		//From here on this is the joysticks controls of the main driver
		
		//Open or close the gear intake
		if(Jleft.getRisingEdge(1)){
			gear.openGear();
			Waiter.waitFor(200);
			gear.pushGear();
		}
		else if(Jleft.getFallingEdge(1)) {
			gear.closeGear();
			gear.pushBack();
		}
		
		//open and close gear flap, also centers turret
		if(Jleft.getRisingEdge(2)) {
			gear.openFlap();
			autoAim = false;
			turret.turnTo(0);
		}
		else if(Jleft.getFallingEdge(2)) {
			gear.closeFlap();
			autoAim = true;
		}
		
		//expands and retracts hopper
		if(Jright.getRisingEdge(3)){
			intake.open();
		}
		else if(Jright.getRisingEdge(4)){
			intake.close();
		}
		
		//shooting with lookup tables
		if(Jright.getRisingEdge(1)) {
			if(turret.getAngle() < 0) {
				shooter.setSpeed(blueTable.get(vision.getDist() - 2.35));
				shooting = true;
			} else {
				shooter.setSpeed(redTable.get(vision.getDist() - 2.35));
				shooting = true;
			}
			shooting = true;
		} else if(Jright.getFallingEdge(1)) {
			shooting = false;
			shooter.setSpeed(0);
		}
		
		//set speed override
		else if(Jright.getRisingEdge(2)) {
			if(SmartDashboard.getString("DB/String 0", "nothing").contains("red")) {
				shooter.setSpeed(3160);
				shooting = true;
			} else if(SmartDashboard.getString("DB/String 0", "nothing").contains("blue")) {
				shooter.setSpeed(2965);
				shooting = true;
			}
		} else if(Jright.getFallingEdge(2)) {
			shooter.setSpeed(0);
			shooting = false;
		}
		
		//only shoots if at speed
		if(shooting && shooter.atSpeed()) {
			shooter.shoot();
		} else {
			shooter.stopShoot();
		}

		//turns turret to center
		if(Jsecond.getRisingEdge(1)) {
			autoAim = false;
			turret.turnTo(0);
		} if(Jsecond.getFallingEdge(1)) {
			autoAim = true;
		}
		
		//Climber State
		if(Jsecond.getRisingEdge(3)){
			climber.climb();
			intake.close();
		}else if (Jsecond.getRisingEdge(4)){
			climber.standing();
		}
	}

	public void teleopInit() {
		
		autoAim = true;
		
		drivetrain.arcadeDrive(0, 0);
		shooter.setSpeed(0);
		shooter.stopShoot();
		climber.stopClimbing();
		turret.turnTo(turret.getAngle());
		gear.closeFlap();
		gear.closeGear();
		shooting = false;
	}
	
	public void disabledInit() {
		recorder.stopRecording();
		drivetrain.arcadeDrive(0, 0);
		shooter.stopShoot();
		climber.stopClimbing();
		turret.turnTo(turret.getAngle());
		gear.closeFlap();
		gear.closeGear();
		shooting = false;
	}
	
	/**
	 * turret tracking
	 */
	public void follow() {
		vision.update();
		if(autoAim) { 
			if(SmartDashboard.getString("DB/String 0", "nothing").contains("red")) {
			turret.turnTo(turret.getAngle() + vision.getX() - 1);
			} else {
				turret.turnTo(turret.getAngle() + vision.getX() + 1);
			}
			turret.turnTo(turret.getAngle() + vision.getX());
		}
	}
}
