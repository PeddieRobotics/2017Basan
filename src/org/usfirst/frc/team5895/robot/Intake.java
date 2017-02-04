package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class Intake {
    //Make an intake subsystem that has a motor and a solenoid.
    //When the solenoid is on the intake will be down and the motor should be spinning.
    //When the intake is up the motor should be off.
    
    //solenoid on => intake down = motor on
    //              => intake up = motor off

    private Talon motor;
    private double speed;
    private Solenoid myCylinder;
    private boolean isDown;

}

   //Make an intake subsystem that has a motor and a solenoid.
   //When the solenoid is on the intake will be down and the motor should be spinning.
   //When the intake is up the motor should be off.
   
   //solenoid on => intake down = motor on
   //              => intake up = motor off
public void up() {
   isDown = false;
   speed = 0.0;
}

public void down() {
   isDown = true;
   speed = 0.5;
}

public void update() {
    controlCurrent();
   motor.set(speed);
   if (myCylinder.get() != isDown)
       myCylinder.set(isDown);
   
}
public void controlCurrent(){
    double limit = 5.0;
    if (pDP.getCurrent(3)>=limit){
        speed=0;
    }
}
}