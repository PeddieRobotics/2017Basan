package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.NavX;
import org.usfirst.frc.team5895.robot.lib.PID;

public class TurnAround {

	public static void turn(PID myPid, NavX angle){
		myPid = new PID(0.012, 0.00003, 0.0000001, 1);
		
		myPid.set(angle.getAngle());
	}
	
}
