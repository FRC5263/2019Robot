package org.usfirst.frc.team5263.robot.subsystems;

import org.usfirst.frc.team5263.robot.RobotMap;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Vision extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private static Servo camAxisX = new Servo(RobotMap.cameraXServoChannel);
	private static Servo camAxisY = new Servo(RobotMap.cameraYServoChannel);
	
	@SuppressWarnings("deprecation")
	NetworkTable tables;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public static void setCamAxisX(double rotation) {
    	camAxisX.set(rotation);
    }
    
    public static void setCamAxisY(double rotation) {
    	camAxisY.set(-rotation);
    }
    
    public void VisionPeriodic() {
//    	setCamAxisX(getSmartDashboardNumber("Cam X Value", .5));
//    	setCamAxisY(getSmartDashboardNumber("Cam Y Value", .5));
    }
    
    private double getSmartDashboardNumber(String name, double defaultValue) {
    	double value;
    	value = SmartDashboard.getNumber(name, -1);
    	
    	if(value < 0 || value > 1.0) {
    		SmartDashboard.putNumber(name, defaultValue);
			value = defaultValue;
		}
    	return value;
    }
}