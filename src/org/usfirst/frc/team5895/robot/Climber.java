package org.usfirst.frc.team5895.robot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class Climber {

	PowerDistributionPanel pdp;
	private Talon climbMotor;
	private enum Mode_Type {CLIMBING, NOTHING, STANDING};
	private Mode_Type mode = Mode_Type.NOTHING;

	public Climber() {
		climbMotor = new Talon(ElectricalLayout.CLIMBER_MOTOR);
		pdp = new PowerDistributionPanel();
	}
	
	/**
	 * Start climbing
	 */
	public void climb() {
		mode = Mode_Type.CLIMBING;
	}
	
	/**
	 * Terminate climbing
	 */
	public void stopClimbing(){
		mode = Mode_Type.NOTHING;
	}
	
	/**
	 * State of robot once it reaches top of rope
	 */
	public void standing() {
		mode = Mode_Type.STANDING;
	}

	/**
	 * @return value of current
	 *
	 */
	public double getCurret() {
		return pdp.getCurrent(ElectricalLayout.CLIMBER_PDB_PORT);
	}
	
	/**
	 * Switches between different climb states
	 * Climbing, Nothing, Standing 
	 */
	public void update() {
		double current = pdp.getCurrent(ElectricalLayout.CLIMBER_PDB_PORT);
		switch(mode) {
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
