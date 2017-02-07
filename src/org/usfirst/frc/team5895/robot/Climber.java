package org.usfirst.frc.team5895.robot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class Climber {
	
	PowerDistributionPanel pdp;
	private Talon climbMotor;
	private enum Mode_Type {WAITING, CLIMBING, NOTHING};
	private Mode_Type mode = Mode_Type.WAITING;
	private double climbTimeStamp = Double.MIN_VALUE;
	
	public Climber() {
		climbMotor = new Talon(0);
	}
	
	public void climb() {
		climbTimeStamp = Timer.getFPGATimestamp();
		mode = Mode_Type.WAITING;
	}
	
	public void stopClimbing(){
		mode = Mode_Type.NOTHING;
	}
	
	public void update() {
		double current = pdp.getCurrent(3);
		switch(mode) {
		case WAITING:
			if (Timer.getFPGATimestamp() - climbTimeStamp < 0.5) {
				climbMotor.set(0.75);
			}
			else mode = Mode_Type.CLIMBING;
			break;
			
		case CLIMBING:
			climbMotor.set(0.5);
			if (current > 131.0) {
				mode = Mode_Type.NOTHING;
			}
			break;
			
		case NOTHING:
			climbMotor.set(0.0);
			break;
		}
	}
}
