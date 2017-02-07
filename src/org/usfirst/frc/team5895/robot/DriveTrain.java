package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.lib.NavX;
import org.usfirst.frc.team5895.robot.lib.PID;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class DriveTrain {
	private Talon Mleft;
	private Talon Mright;
	double Lspeed, Rspeed;
	private enum Mode_Type {TELEOP, AUTO};
	private Mode_Type mode = Mode_Type.AUTO;
	private Encoder Eleft, Eright;
	private NavX NavX;
	private final double Kpstraight=0.02;
	private final double Kpturn = 0.07;
	private final double Ki=0.0000005;
	private final double Kd=0.0001;
	private PID PIDStraight;
	private PID PIDTurning;
	
	public DriveTrain()
	{
		NavX=new NavX();
		
		Mleft = new Talon(1);
		Mright = new Talon(0);
		
		Eleft = new Encoder(2,3);
		Eright = new Encoder(0,1);
		
		Eleft.setDistancePerPulse(-1* 4*3.14/360);
		Eright.setDistancePerPulse(4*3.14/360);
		
		PIDStraight = new PID(Kpstraight, Ki, Kd, 1);
		PIDTurning= new PID(Kpturn, 0, 0, 1);
		
	}
	

	public double getDistance() {
	
		double distance = ((Eleft.getDistance()) + Eright.getDistance())/2;
		
		return distance;
	}
	
	public double getAngle(){
		double angle = NavX.getAngle();
		
		return angle;
	}
	
	public void resetEncodersAndNavX(){
		Eleft.reset();
		Eright.reset();
		NavX.reset();
	}
	
	public void autoDrive() {		
		PIDStraight.set(72);
		PIDTurning.set(0);
		
		mode = Mode_Type.AUTO;
	}
	
	public void arcadeDrive( double speed, double turn) {
		Lspeed = speed + turn;
		Rspeed = -speed + turn;
		mode = Mode_Type.TELEOP;
	}
	
	public void setLeftRightPower(double l, double r) {
		Lspeed = l;
		Rspeed = r;
		mode = Mode_Type.TELEOP;
	}
	
	public void update()
	{
		DriverStation.reportError("distance = " + getDistance()+"\n", false);
		//DriverStation.reportError("angle = " + getAngle()+"\n", false);
		
		switch(mode) {
		case AUTO:
			double output = PIDStraight.getOutput(getDistance());
			double spinningOutput = -PIDTurning.getOutput(getAngle());
			/*if(output>=0.5){
				output=0.5;
			}
			else if(output<=-0.5){
				output=-0.5;
			}*/
			Mleft.set(-output+spinningOutput);
			Mright.set(output+spinningOutput);
			break;
			
		case TELEOP:
			Mleft.set(Lspeed);
			Mright.set(Rspeed);
			
			break;
		
	}
	
}
}
