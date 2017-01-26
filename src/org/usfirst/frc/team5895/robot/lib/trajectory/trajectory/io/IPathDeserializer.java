package org.usfirst.frc.team5895.robot.lib.trajectory.trajectory.io;

import org.usfirst.frc.team5895.robot.lib.trajectory.trajectory.Path;

/**
 * Interface for methods that deserializes a Path or Trajectory.
 * 
 * @author Jared341
 */
public interface IPathDeserializer {
  
  public Path deserialize(String serialized);
}
