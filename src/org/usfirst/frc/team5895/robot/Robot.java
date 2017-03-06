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
	LookupTable table;
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
		
		
		loop = new Looper(10);
		loop.add(drivetrain::update);
		loop.add(shooter::update);
		loop.add(gear::update);
		loop.add(turret::update);
		loop.add(climber::update);
		loop.add(intake::update);
		loop.start();

		loopVision = new Looper(200);
		loopVision.add(this::follow);
		//start loop on first call to teleop

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
		
		//drivetrain.setSide(place);

		if(routine.contains("blue")) {
			if(gameplan.contains("balls")){
				BlueAuto.run(drivetrain, shooter, turret, table, vision, intake);
			}
			else if(gameplan.contains("gear")){
				BlueGear.run(drivetrain, gear);
			}
			else{
				DoNothing.run();
			}
		}
		if(routine.contains("red")) {
			if(gameplan.contains("balls")){
				RedAuto.run(drivetrain, shooter, turret, table, vision, intake);
			}
			else if(gameplan.contains("gear")){
				RedGear.run(drivetrain, gear);
			}
		}
		else {
			DoNothing.run();
		}
		if(routine.contains("straight")) {
			StraightShoot.run(drivetrain, turret, shooter, table, vision);
		}
		else {
			DoNothing.run();
		}
	}

	public void teleopPeriodic() {
		drivetrain.arcadeDrive(Jleft.getRawAxis(1), -Jright.getRawAxis(0));
		
		
		//From here on this is the joysticks controls of the main driver
		//Open or close the gear intake
		if(Jleft.getRisingEdge(1)){
			gear.open();
		}
		else if(Jleft.getRisingEdge(2)) {
			gear.close();
		}

		//Hopper Extendy Thing In or Out
		if(Jleft.getRisingEdge(3)) {
			intake.down();
		}
		else if(Jleft.getRisingEdge(4)) {
			intake.up();
		}
		
		//if we are shooting or not
		if(Jright.getRisingEdge(1)) {
			if(table.get(vision.getDist()) == 0){
				shooter.setSpeed(table.get(8));
				shooting = true;
			}else{
				shooter.setSpeed(table.get(vision.getDist()));
				shooting = true;
			}
		} else if(Jright.getFallingEdge(1)) {
			shooting = false;
			shooter.setSpeed(0);
		}
		
		if(shooting && shooter.atSpeed()) {
			shooter.shoot();
		} else {
			shooter.stopShoot();
		}
		

		//Climber State
		if(Jsecond.getRisingEdge(3)){
			climber.climb();
		}else if (Jsecond.getRisingEdge(4)){
			climber.standing();
		}
	}

	public void teleopInit() {
		
		if (autoAim == false) {
			loopVision.start();
			autoAim = true;
		} 
		
		drivetrain.arcadeDrive(0, 0);
		shooter.setSpeed(0);
		shooter.stopShoot();
		climber.stopClimbing();
		turret.turnTo(turret.getAngle());
		gear.close();
		shooting = false;
	}
	
	public void disabledInit() {
		drivetrain.arcadeDrive(0, 0);
//		shooter.setSpeed(0);
		shooter.stopShoot();
		climber.stopClimbing();
		turret.turnTo(turret.getAngle());
		gear.close();
		shooting = false;
	}
	
	public void follow(){
		if(shooter.getSpeed() < 200) { 
			vision.update();
			turret.turnTo(turret.getAngle() + vision.getX());
		}
	}
}
