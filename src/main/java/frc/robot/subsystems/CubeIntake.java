package org.usfirst.frc.team5263.robot.subsystems;

import org.usfirst.frc.team5263.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CubeIntake extends Subsystem {

	//objects
		//for intake wheels
	private static PWMSpeedController intakeMotorLeft;
	private static PWMSpeedController intakeMotorRight;
		//for intake lift
	private static PWMSpeedController intakeLiftMotor;
	
	private static Relay spike;
		//for lift encoder
	private static Encoder liftEncoder;
	
	
	public CubeIntake() {
		liftEncoder = new Encoder(RobotMap.intakeLiftEncoderChannelA, RobotMap.intakeLiftEncoderChannelB);
		if(RobotMap.isCompetitionBot) {
			intakeMotorLeft = new Victor (RobotMap.intakeMotorLeftChannel);
			intakeMotorRight = new Victor (RobotMap.intakeMotorRightChannel);
			intakeLiftMotor = new VictorSP(RobotMap.intakeLiftMotorChannel);
			spike = new Relay(RobotMap.SpikeRelayChannel);
		} else {
			intakeMotorLeft = new Jaguar (RobotMap.intakeMotorLeftChannel);
			intakeMotorRight = new Jaguar (RobotMap.intakeMotorRightChannel);
			intakeLiftMotor = new Jaguar(RobotMap.intakeLiftMotorChannel);
		}
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    
    //Positive - push the cube out
    //Negative - pull the cube in
    public static void driveMotors(double power) {
    	intakeMotorLeft.set(power);
    	intakeMotorRight.set(power);
    }
    
    public static void driveLiftMotor(double power) {
    	intakeLiftMotor.set(power);
    }
    
    public static void displayData() {
    	SmartDashboard.putNumber("Cube Intake Lift Encoder", liftEncoder.get());
    }
    
}

