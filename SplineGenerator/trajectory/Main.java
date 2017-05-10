package trajectory;

import trajectory.io.JavaSerializer;
import trajectory.io.JavaStringSerializer;
import trajectory.io.TextFileSerializer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Jared341
 */
public class Main {
  public static String joinPath(String path1, String path2)
  {
      File file1 = new File(path1);
      File file2 = new File(file1, path2);
      return file2.getPath();
  }
  
  private static boolean writeFile(String path, String data) {
    try {
      File file = new File(path);

      // if file doesn't exists, then create it
      if (!file.exists()) {
          file.createNewFile();
      }

      FileWriter fw = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(data);
      bw.close();
    } catch (IOException e) {
      return false;
    }
    
    return true;
  }
  
  public static void main(String[] args) {
    String directory = "./auto_paths";
    if (args.length >= 1) {
      directory = args[0];
    }
    
    TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
    config.dt = .01;
    config.max_acc = 80.0;
    config.max_jerk = 60.0;
    config.max_vel = 10.0;
    
    final double kWheelbaseWidth = 27.0/12; //correct
    {
      config.dt = .01;
      config.max_acc = 80.0;
      config.max_jerk = 60.0;
      config.max_vel = 10.0;
      
      final String path_name = "Straight";
      
      WaypointSequence p = new WaypointSequence(10);
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(-8.0, 0, 0));

      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      //System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!!1");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
    
   
    {
        config.dt = .01;
        config.max_acc = 80.0;
        config.max_jerk = 60.0;
        config.max_vel = 10.0;
        
        final String path_name = "Balls_Blue";
        
        WaypointSequence p = new WaypointSequence(10);
        p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
        p.addWaypoint(new WaypointSequence.Waypoint(8.25, 8.75, 1.57));

        Path path = PathGenerator.makePath(p, config,
            kWheelbaseWidth, path_name);

        // Outputs to the directory supplied as the first argument.
        TextFileSerializer js = new TextFileSerializer();
        String serialized = js.serialize(path);
        //System.out.print(serialized);
        String fullpath = joinPath(directory, path_name + ".txt");
        if (!writeFile(fullpath, serialized)) {
          System.err.println(fullpath + " could not be written!!!!1");
          System.exit(1);
        } else {
          System.out.println("Wrote " + fullpath);
        }
      }
    
    {
        config.dt = .01;
        config.max_acc = 80.0;
        config.max_jerk = 60.0;
        config.max_vel = 10.0;
        
        final String path_name = "Balls_Red";
        
        WaypointSequence p = new WaypointSequence(10);
        p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
        p.addWaypoint(new WaypointSequence.Waypoint(8.75, -9.0, -1.57));


        Path path = PathGenerator.makePath(p, config,
            kWheelbaseWidth, path_name);

        // Outputs to the directory supplied as the first argument.
        TextFileSerializer js = new TextFileSerializer();
        String serialized = js.serialize(path);
        //System.out.print(serialized);
        String fullpath = joinPath(directory, path_name + ".txt");
        if (!writeFile(fullpath, serialized)) {
          System.err.println(fullpath + " could not be written!!!!1");
          System.exit(1);
        } else {
          System.out.println("Wrote " + fullpath);
        }
      }
    
    {
        config.dt = .01;
        config.max_acc = 80.0;
        config.max_jerk = 60.0;
        config.max_vel = 10.0;
        
        final String path_name = "Gear_Red";
        
        WaypointSequence p = new WaypointSequence(10);
        p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
        p.addWaypoint(new WaypointSequence.Waypoint(5.5, 0, -(3.14159*60/180)));
        p.addWaypoint(new WaypointSequence.Waypoint(6.25, -1.75, -(3.14159*60/180)));
 
        Path path = PathGenerator.makePath(p, config,
            kWheelbaseWidth, path_name);

        // Outputs to the directory supplied as the first argument.
        TextFileSerializer js = new TextFileSerializer();
        String serialized = js.serialize(path);
        //System.out.print(serialized);
        String fullpath = joinPath(directory, path_name + ".txt");
        if (!writeFile(fullpath, serialized)) {
          System.err.println(fullpath + " could not be written!!!!1");
          System.exit(1);
        } else {
          System.out.println("Wrote " + fullpath);
        }
      }
    
    {
        config.dt = .01;
        config.max_acc = 80.0;
        config.max_jerk = 60.0;
        config.max_vel = 10.0;
        
        final String path_name = "Gear_Blue";
        
        WaypointSequence p = new WaypointSequence(10);
        p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
        p.addWaypoint(new WaypointSequence.Waypoint(5.5, 0, (3.14159*60/180)));
        p.addWaypoint(new WaypointSequence.Waypoint(6.25, 1.75, (3.14159*60/180)));

        Path path = PathGenerator.makePath(p, config,
            kWheelbaseWidth, path_name);

        // Outputs to the directory supplied as the first argument.
        TextFileSerializer js = new TextFileSerializer();
        String serialized = js.serialize(path);
        //System.out.print(serialized);
        String fullpath = joinPath(directory, path_name + ".txt");
        if (!writeFile(fullpath, serialized)) {
          System.err.println(fullpath + " could not be written!!!!1");
          System.exit(1);
        } else {
          System.out.println("Wrote " + fullpath);
        }
      }
    
    {
        config.dt = .01;
        config.max_acc = 80.0;
        config.max_jerk = 60.0;
        config.max_vel = 10.0;
        
        final String path_name = "Gear_Center_Drive";
        
        WaypointSequence p = new WaypointSequence(10);
        p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
        p.addWaypoint(new WaypointSequence.Waypoint(7.25, 0, 0));

        Path path = PathGenerator.makePath(p, config,
            kWheelbaseWidth, path_name);

        // Outputs to the directory supplied as the first argument.
        TextFileSerializer js = new TextFileSerializer();
        String serialized = js.serialize(path);
        //System.out.print(serialized);
        String fullpath = joinPath(directory, path_name + ".txt");
        if (!writeFile(fullpath, serialized)) {
          System.err.println(fullpath + " could not be written!!!!1");
          System.exit(1);
        } else {
          System.out.println("Wrote " + fullpath);
        }
      }
    
    {
        config.dt = .01;
        config.max_acc = 80.0;
        config.max_jerk = 60.0;
        config.max_vel = 10.0;
        
        final String path_name = "Gear_Center_Blue";
        
        WaypointSequence p = new WaypointSequence(10);
        p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
        p.addWaypoint(new WaypointSequence.Waypoint(2, 0, 0));
        p.addWaypoint(new WaypointSequence.Waypoint(3, -2, -(3.14159/3)));

        Path path = PathGenerator.makePath(p, config,
            kWheelbaseWidth, path_name);

        // Outputs to the directory supplied as the first argument.
        TextFileSerializer js = new TextFileSerializer();
        String serialized = js.serialize(path);
        //System.out.print(serialized);
        String fullpath = joinPath(directory, path_name + ".txt");
        if (!writeFile(fullpath, serialized)) {
          System.err.println(fullpath + " could not be written!!!!1");
          System.exit(1);
        } else {
          System.out.println("Wrote " + fullpath);
        }
      }
    
    {
        config.dt = .01;
        config.max_acc = 80.0;
        config.max_jerk = 60.0;
        config.max_vel = 10.0;
        
        final String path_name = "Gear_Center_Red";
        
        WaypointSequence p = new WaypointSequence(10);
        p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
        p.addWaypoint(new WaypointSequence.Waypoint(2, 0, 0));
        p.addWaypoint(new WaypointSequence.Waypoint(3, 2, 3.14159/3));

        Path path = PathGenerator.makePath(p, config,
            kWheelbaseWidth, path_name);

        // Outputs to the directory supplied as the first argument.
        TextFileSerializer js = new TextFileSerializer();
        String serialized = js.serialize(path);
        //System.out.print(serialized);
        String fullpath = joinPath(directory, path_name + ".txt");
        if (!writeFile(fullpath, serialized)) {
          System.err.println(fullpath + " could not be written!!!!1");
          System.exit(1);
        } else {
          System.out.println("Wrote " + fullpath);
        }
      }
    
    {
        config.dt = .01;
        config.max_acc = 80.0;
        config.max_jerk = 60.0;
        config.max_vel = 10.0;
        
        final String path_name = "Balls_Red_Close";
        
        WaypointSequence p = new WaypointSequence(10);
        p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
        p.addWaypoint(new WaypointSequence.Waypoint(6.5, -8.75, -1.57));

        Path path = PathGenerator.makePath(p, config,
            kWheelbaseWidth, path_name);

        // Outputs to the directory supplied as the first argument.
        TextFileSerializer js = new TextFileSerializer();
        String serialized = js.serialize(path);
        //System.out.print(serialized);
        String fullpath = joinPath(directory, path_name + ".txt");
        if (!writeFile(fullpath, serialized)) {
          System.err.println(fullpath + " could not be written!!!!1");
          System.exit(1);
        } else {
          System.out.println("Wrote " + fullpath);
        }
      }
    
    {
        config.dt = .01;
        config.max_acc = 80.0;
        config.max_jerk = 60.0;
        config.max_vel = 10.0;
        
        final String path_name = "Balls_Blue_Close";
        
        WaypointSequence p = new WaypointSequence(10);
        p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
        p.addWaypoint(new WaypointSequence.Waypoint(6.5, 8.5, 1.57));

        Path path = PathGenerator.makePath(p, config,
            kWheelbaseWidth, path_name);

        // Outputs to the directory supplied as the first argument.
        TextFileSerializer js = new TextFileSerializer();
        String serialized = js.serialize(path);
        //System.out.print(serialized);
        String fullpath = joinPath(directory, path_name + ".txt");
        if (!writeFile(fullpath, serialized)) {
          System.err.println(fullpath + " could not be written!!!!1");
          System.exit(1);
        } else {
          System.out.println("Wrote " + fullpath);
        }
      }
    
    {
        config.dt = .01;
        config.max_acc = 80.0;
        config.max_jerk = 60.0;
        config.max_vel = 10.0;
        
        final String path_name = "Balls_Blue_Far";
        
        WaypointSequence p = new WaypointSequence(10);
        p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
        p.addWaypoint(new WaypointSequence.Waypoint(9.0, 9.75, 1.57));

        Path path = PathGenerator.makePath(p, config,
            kWheelbaseWidth, path_name);

        // Outputs to the directory supplied as the first argument.
        TextFileSerializer js = new TextFileSerializer();
        String serialized = js.serialize(path);
        //System.out.print(serialized);
        String fullpath = joinPath(directory, path_name + ".txt");
        if (!writeFile(fullpath, serialized)) {
          System.err.println(fullpath + " could not be written!!!!1");
          System.exit(1);
        } else {
          System.out.println("Wrote " + fullpath);
        }
      }
    
    {
        config.dt = .01;
        config.max_acc = 80.0;
        config.max_jerk = 60.0;
        config.max_vel = 10.0;
        
        final String path_name = "Balls_Red_Far";
        
        WaypointSequence p = new WaypointSequence(10);
        p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
        p.addWaypoint(new WaypointSequence.Waypoint(9.0, -9.75, -1.57));

        Path path = PathGenerator.makePath(p, config,
            kWheelbaseWidth, path_name);

        // Outputs to the directory supplied as the first argument.
        TextFileSerializer js = new TextFileSerializer();
        String serialized = js.serialize(path);
        //System.out.print(serialized);
        String fullpath = joinPath(directory, path_name + ".txt");
        if (!writeFile(fullpath, serialized)) {
          System.err.println(fullpath + " could not be written!!!!1");
          System.exit(1);
        } else {
          System.out.println("Wrote " + fullpath);
        }
      }
    
  }
}
