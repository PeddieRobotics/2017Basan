package org.usfirst.frc.team5895.robot;

public class ElectricalLayout {

	//Motors
	public static final int DRIVE_LEFTMOTOR = 0;
	public static final int DRIVE_RIGHTMOTOR = 1;
	public static final int FLYWHEEL_MOTOR = 2;
	public static final int CONVEYOR_MOTOR = 3;
	public static final int TORNADO_MOTOR = 4;
	public static final int TURRET_MOTOR = 5;
	public static final int CLIMBER_MOTOR = 6;

	//Solenoids
	public static final int EXPAND_SOLENOID = 1;
	public static final int HOPPER_SOLENOID = 4; //3 on real bot
	public static final int GEAR_FLAP_SOLENOID = 3;
	public static final int GEAR_DROP_SOLENOID = 2;
	public static final int SPARE = 0; //push

	//Digital Inputs
	public static final int DRIVE_LEFTENCODER = 0;
	public static final int DRIVE_LEFTENCODER2 = 1;
	public static final int DRIVE_RIGHTENCODER = 2;
	public static final int DRIVE_RIGHTENCODER2 = 3;
	public static final int FLYWHEEL_COUNTER = 4;
	public static final int TURRET_ENCODER = 5;
	public static final int TURRET_ENCODER2 = 6;
	public static final int LIMIT_SWITCHLEFT = 7;
	public static final int LIMIT_SWITCHRIGHT = 8;
	
	public static final int CLIMBER_PDB_PORT = 0;
}
