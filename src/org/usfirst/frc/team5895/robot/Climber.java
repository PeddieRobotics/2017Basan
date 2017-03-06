package org.usfirst.frc.team5895.robot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class Climber {

	PowerDistributionPanel pdp;
	private Talon climbMotor;
	private enum Mode_Type {WAITING, CLIMBING, NOTHING, STANDING};
	private Mode_Type mode = Mode_Type.NOTHING;
	private double climbTimeStamp = Double.MIN_VALUE;

	public Climber() {
		climbMotor = new Talon(ElectricalLayout.CLIMBER_MOTOR);
		pdp = new PowerDistributionPanel();
	}
	
	/**
	 * Start climbing
	 */
	public void climb() {
		climbTimeStamp = Timer.getFPGATimestamp();
		mode = Mode_Type.WAITING;
	}
	
	/**
	 * Terminate climbing
	 */
	public void stopClimbing(){
		mode = Mode_Type.NOTHING;
	}
	
	public void standing() {
		mode = Mode_Type.STANDING;
	}
	
	public double getCurret() {
		return pdp.getCurrent(ElectricalLayout.CLIMBER_PDB_PORT);
	}

	public void update() {
		double current = pdp.getCurrent(ElectricalLayout.CLIMBER_PDB_PORT);
		switch(mode) {
		case WAITING:
			if (Timer.getFPGATimestamp() - climbTimeStamp < 1) {
				climbMotor.set(1);
			}
			else mode = Mode_Type.CLIMBING;
			break;

		case CLIMBING:
			climbMotor.set(1);
			if (current > 80) {
				mode = Mode_Type.STANDING;
			}
			break;

		case NOTHING:
			climbMotor.set(0.0);
			break;
			
		case STANDING:
			climbMotor.set(0.25);
			break;
		}
	}
}
