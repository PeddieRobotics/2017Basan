package org.usfirst.frc.team5895.robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.TalonSRX;

public class Turret {

	private Talon myMotor;
	private PID myPID;
	private Encoder e;
	private double Kp;
	private double Ki;
	private double Kd;
	private double dV;
	private double degreesPerPulse;
	
	public Turret() {
		Kp=0.1;
		Ki=0;
		Kd=0;
		dV=1/100;
		degreesPerPulse=0;
		myMotor = new TalonSRX(ElectricalLayout.TURRET_MOTOR);
		myPID = new PID(Kp, Ki, Kd, dV);
		e = new Encoder(ElectricalLayout.TURRET_ENCODER,ElectricalLayout.TURRET_ENCODER2);
		e.setDistancePerPulse(degreesPerPulse);
	}
	
	public void set(double angle) {
		myPID.set(angle);
	}
		
	public double getAngle() {
		return e.getDistance();
	}
	
	public boolean atAngle(){
		if(Math.abs(e.getDistance()-myPID.getSetpoint())<0.25){
			return true;
		} else
			return false;
	}
	
	public void update() {
//		myMotor.set(myPID.getOutput(e.getDistance()));
	}	
}