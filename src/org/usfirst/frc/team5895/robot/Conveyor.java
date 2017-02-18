package org.usfirst.frc.team5895.robot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class Conveyor {
	
	PowerDistributionPanel pdp;
	private Talon conveyorMotor;
	private enum Mode_Type {REVERSE, SHOOT, STOPSHOOT};
	private Mode_Type mode = Mode_Type.SHOOT;
	private double conveyorTimeStamp = Double.MIN_VALUE;

	
	public Conveyor()
	{
		conveyorMotor = new Talon(ElectricalLayout.CONVEYOR_MOTOR);
	}
	
	/**
	 * Conveyor goes in the normal direction
	 */
	public void shoot() {
		mode = Mode_Type.SHOOT;
	}
	
	/**
	 * Conveyor stops shooting
	 */
	public void stopShoot() {
		mode = Mode_Type.STOPSHOOT;
	}
	
	public void update() {
		
		double current = pdp.getCurrent(ElectricalLayout.CONVEYOR_PDB_PORT);
		
		switch(mode) {
		case REVERSE:
			if (Timer.getFPGATimestamp() - conveyorTimeStamp < 0.1) {
				conveyorMotor.set(0.6);
			}			
			else mode = Mode_Type.SHOOT;
			break;

		case SHOOT:
			conveyorMotor.set(-0.6);
			
			if (current > 25.0) {
				mode = Mode_Type.REVERSE;
				conveyorTimeStamp = Timer.getFPGATimestamp();
			}
			break;
			
		case STOPSHOOT:
			conveyorMotor.set(0.0);
		}
		
	}
}