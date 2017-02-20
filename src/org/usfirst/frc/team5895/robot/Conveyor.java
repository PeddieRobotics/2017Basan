package org.usfirst.frc.team5895.robot;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class Conveyor {
	
	private Talon conveyorMotor;
	private enum Mode_Type {REVERSE, SHOOT, STOPSHOOT};
	private Mode_Type mode = Mode_Type.SHOOT;
	private double conveyorTimeStamp = Double.MIN_VALUE;
	private double lastShoot;
	
	private Counter Counter;
	
	public Conveyor()
	{
		conveyorMotor = new Talon(ElectricalLayout.CONVEYOR_MOTOR);
		Counter = new Counter(ElectricalLayout.CONVEYOR_SENSOR);
		Counter.setDistancePerPulse(1);
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
		
		switch(mode) {
		case REVERSE:
			if (Timer.getFPGATimestamp() - conveyorTimeStamp < 0.25) {
				conveyorMotor.set(-0.6);
			}			
			else mode = Mode_Type.SHOOT;
			break;

		case SHOOT:
			conveyorMotor.set(0.6);
			double count = Counter.get();
			if (count == lastShoot ) {
				mode = Mode_Type.REVERSE;
				conveyorTimeStamp = Timer.getFPGATimestamp();	
			}
			lastShoot = count;
			break;
			
		case STOPSHOOT:
			conveyorMotor.set(0.0);
		}
		
	}
}