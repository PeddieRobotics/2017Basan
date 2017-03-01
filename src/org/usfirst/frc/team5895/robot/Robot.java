package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.auto.*;
import org.usfirst.frc.team5895.robot.framework.*;
import org.usfirst.frc.team5895.robot.lib.BetterJoystick;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
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
		loop.add(shooter::update);
		loop.start();

		loopVision = new Looper(200);
		loopVision.add(vision::update);
		loopVision.start();

	}

	public void autonomousInit() {
		String routine = SmartDashboard.getString("DB/String 0", "nothing");
		String gameplan = SmartDashboard.getString("DB/String 1", "nothing");
		String place=SmartDashboard.getString("DB/String 2", "nothing");

		if(routine.contains("blue")) {
			if(gameplan.contains("balls")){
				BlueAuto.run(drivetrain, shooter, turret);
			}
			else if(gameplan.contains("gear")){
				BlueGear.run(drivetrain, gear, place);
			}
			else{
				DoNothing.run();
			}
		}
		if(routine.contains("red")) {
			if(gameplan.contains("balls")){
				RedAuto.run(drivetrain, shooter, turret);
			}
			else if(gameplan.contains("gear")){
				RedGear.run(drivetrain, gear, place);
			}
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
}
