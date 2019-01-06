package org.usfirst.frc.team5263.robot.subsystems;

import org.usfirst.frc.team5263.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BucketArm extends Subsystem {
	
	//objects
	private static Victor liftMotor = new Victor(RobotMap.liftMotorChannel);
	private static DigitalInput upperLimitSwitch = new DigitalInput(RobotMap.upperLimitSwitchChannel);
	private static DigitalInput lowerLimitSwitch = new DigitalInput(RobotMap.lowerLimitSwitchChannel);
    
	//methods
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	
	public static void driveMotor(double power) {
		liftMotor.set(power);
	}
	
	public static boolean isUpperLimitClosed() {
		return upperLimitSwitch.get();
	}
    
	public static boolean isLowerLimitClosed() {
		return lowerLimitSwitch.get();
	}
	
	public static double currentTime() {
		return 1.0;
	} 
    
}

