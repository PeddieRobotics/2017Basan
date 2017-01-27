package org.usfirst.frc.team5895.robot.lib;

import org.usfirst.frc.team5895.robot.lib.trajectory.ChezyMath;
import org.usfirst.frc.team5895.robot.lib.trajectory.Trajectory;
import org.usfirst.frc.team5895.robot.lib.trajectory.TrajectoryFollower;


/**
 * TrajectoryDriveController.java
 * This controller drives the robot along a specified trajectory.
 * @author Tom Bottiglieri
 */
public class TrajectoryDriveController {
	
	  private boolean enabled = false;
	  Trajectory trajectory;
	  TrajectoryFollower followerLeft = new TrajectoryFollower("left");
	  TrajectoryFollower followerRight = new TrajectoryFollower("right");
	  double direction;
	  double heading;
	  double kTurn;

	  public TrajectoryDriveController(double kp, double ki, double kd, double kv, double ka, double kTurn) {
//  	  followerLeft.configure(1.5, 0, 0, 1.0/15.0, 1.0/34.0);
//  	  followerRight.configure(1.5, 0, 0, 1.0/15.0, 1.0/34.0);
//		  kTurn = -3.0/80.0;
		  followerLeft.configure(kp, ki, kd, kv, ka);
		  followerRight.configure(kp, ki, kd, kv, ka);
		  this.kTurn = kTurn;
	  }

	  public boolean onTarget() {
		  return followerLeft.isFinishedTrajectory();
	  }

	  /**
       * Should also reset drive encoders
       * 
       * @param leftProfile
       * @param rightProfile
       * @param direction
       * @param heading
       */
	  public void loadProfile(Trajectory leftProfile, Trajectory rightProfile, double direction, double heading) {
		  reset();
		  followerLeft.setTrajectory(leftProfile);
		  followerRight.setTrajectory(rightProfile);
		  this.direction = direction;
		  this.heading = heading;
	  }
  
	  public void loadProfileNoReset(Trajectory leftProfile, Trajectory rightProfile) {
		  followerLeft.setTrajectory(leftProfile);
		  followerRight.setTrajectory(rightProfile);
	  }

	  /**
	   * Also should reset drive encoders
       */
	  public void reset() {
		  followerLeft.reset();
		  followerRight.reset();
		  // drivebase.resetEncoders();
	  }
  
	  public int getFollowerCurrentSegment() {
		  return followerLeft.getCurrentSegment();
	  }
  
	  public int getNumSegments() {
		  return followerLeft.getNumSegments();
	  }
  
	  /**
	   * 
	   * @param leftEncDist
	   * @param rightEncDist
	   * @param angleRads
	   * @return An array with two doubles, the first is the left motor value, the second is the right
	   */
	  public double[] getOutput(double leftEncDist, double rightEncDist, double angleRads) {
		  double[] output = new double[2];
		  if (!enabled) {
			  output[0] = 0;
			  output[1] = 0;
			  return output;
		  }
		  
		  if (onTarget()) {
			  output[0] = 0;
			  output[1] = 0;
			  return output;
		  } else  {
			  double distanceL = direction * leftEncDist;
			  double distanceR = direction * rightEncDist;
	
			  double speedLeft = direction * followerLeft.calculate(distanceL);
			  double speedRight = direction * followerRight.calculate(distanceR);
	      
			  double goalHeading = followerLeft.getHeading();
			  double observedHeading = angleRads;
	
			  double angleDiffRads = ChezyMath.getDifferenceInAngleRadians(observedHeading, goalHeading);
			  double angleDiff = Math.toDegrees(angleDiffRads);
	
			  double turn = kTurn * angleDiff;
			  
			  output[0] = speedLeft + turn;
			  output[1] = speedRight - turn;
			  return output;
	      
	    }
	  }

	  public void enable() {
	    enabled = true;
	  }
	  
	  public void disable() {
	    enabled = false;
	  }

	  public boolean enabled() {
	    return enabled;
	  }
  
	  public void setTrajectory(Trajectory t) {
	    this.trajectory = t;
	  }
	
	  public double getGoal() {
	    return 0;
	  }
}
