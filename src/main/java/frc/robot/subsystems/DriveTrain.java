package org.usfirst.frc.team5263.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;

import org.usfirst.frc.team5263.robot.RobotMap;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Ultrasonic;

import com.kauailabs.navx.frc.AHRS;
/**
 *
 */
public class DriveTrain extends Subsystem {

	static DriveTrain sharedInstance = null;

	//ROTATION PID #TEMPORARY
	public PIDController turnController;
	static final double turnControllerkP = 0.03; 		
	static final double turnControllerkI = 1.0E-5 ;
	static final double turnControllerkD = 0.1;
	static final double turnControllerkF = 0.00;
	static final double turnControllerkToleranceDegrees = 2.0f;

	//DriveTo PID #TEMPORARY
	public PIDController driveController;
	static final double driveControllerkP = 0.03; 		
	static final double driveControllerkI = 1.0E-5 ;
	static final double driveControllerkD = 0.1;
	static final double driveControllerkF = 0.00;
	static final double driveControllerkToleranceinches = 2.0f;
	public double driveControllerAngle = 0;	


	//ROTATION RATE PID #TEMPORARY
	public PIDController turnRateController;
	static final double turnRateControllerkP = 0.03; 		
	static final double turnRateControllerkI = 1.0E-5 ;
	static final double turnRateControllerkD = 0.1;
	static final double turnRateControllerkF = 0.00;
	static final double turnRateControllerkToleranceDegrees = 2.0f;
	
	//constants
	private final static double wheelDiameterInches = 6.0;
	private final static double encoderClicksPerRevolution = 360;
	private final static double ultrasonicOffset = 13; //inches the ultrasonic is mounted from the front of the robot

	//objects
	public Encoder LeftEncoder;
	public Encoder RightEncoder;
	public final PWMSpeedController leftMotor;
	public final PWMSpeedController rightMotor;
	private DifferentialDrive myRobot;
	public AHRS ahrs = new AHRS(SPI.Port.kMXP);
	private Ultrasonic sonic; 
	
	
	public DriveTrain() {
		//instantiating objects
		LeftEncoder = new Encoder(RobotMap.leftEncoderChannelA, RobotMap.leftEncoderChannelB);
		RightEncoder = new Encoder(RobotMap.rightEncoderChannelA, RobotMap.rightEncoderChannelB);
		if(RobotMap.isCompetitionBot) {
			leftMotor = new Spark(RobotMap.leftDriveMotorChannel);
			rightMotor = new Spark(RobotMap.rightDriveMotorChannel);
		} else {
			leftMotor = new Talon(RobotMap.leftDriveMotorChannel);
			rightMotor = new Talon(RobotMap.rightDriveMotorChannel);			
		}
		myRobot = new DifferentialDrive(leftMotor, rightMotor);
		sonic = new Ultrasonic(RobotMap.ultrasonicInputChannel,RobotMap.ultrasonicOutputChannel);//input, output on the sensor		
		sonic.setAutomaticMode(true);


		//ROTATION PID 
		turnController = new PIDController(turnControllerkP, turnControllerkI, turnControllerkD, turnControllerkF, new PIDSource() {

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {

			} 

			@Override
			public double pidGet() {
				// TODO Auto-generated method stub
				return getRotation();
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				// TODO Auto-generated method stub
				return PIDSourceType.kDisplacement;
			}
		},  new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				drive(output, -output);
			}
		});
		turnController.setName("Turn Controller");
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(turnControllerkToleranceDegrees);
		//DRIVE PID
		driveController = new PIDController(driveControllerkP, driveControllerkI, driveControllerkD, driveControllerkF, new PIDSource() {

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {

			} 

			@Override
			public double pidGet() {
				return getLeftEncoderInches();
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		},  new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				double correction = 1 * ((getRotation() - driveControllerAngle) / 100) ;

				drive(output - correction, output + correction);
			}
		});
		driveController.setName("Drive Controller");
		driveController.setOutputRange(-1.0, 1.0);
		driveController.setAbsoluteTolerance(driveControllerkToleranceinches);

		//ROTATION RATE PID
		

		//ROTATION PID 
		turnRateController = new PIDController(turnRateControllerkP, turnRateControllerkI, turnRateControllerkD, turnRateControllerkF, new PIDSource() {

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
			} 

			@Override
			public double pidGet() {
				// TODO Auto-generated method stub
				return ahrs.getActualUpdateRate() * ahrs.getRate();
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				// TODO Auto-generated method stub
				return PIDSourceType.kRate;
			}
		},  new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				drive(output, -output);
			}
		});
		turnRateController.setName("Turn Rate Controller");
		turnRateController.setOutputRange(-1.0, 1.0);
		turnRateController.setAbsoluteTolerance(turnRateControllerkToleranceDegrees);

	}


	public static DriveTrain sharedInstance() {
		if(sharedInstance == null) {
			sharedInstance = new DriveTrain();
		}
		return sharedInstance;
	}


	public void initDefaultCommand() {

	}

	public double getRotation() {
		return ahrs.getAngle();
	}

	public double getLeftEncoder() {
		return (double) LeftEncoder.get();
	}

	public double getRightEncoder() {
		return (double) RightEncoder.get();
	}

	/*
	 *                  1 revolution    pi * wheel diameter inches
	 * encoder clicks * ------------ * ---------------------------- = inches traveled
	 *                    # clicks             1 revolution
	 */
	public double getLeftEncoderInches() {
		return getLeftEncoder() * (1 / encoderClicksPerRevolution) * (Math.PI * wheelDiameterInches);
	}

	public double getRightEncoderInches() {
		return getRightEncoder() * (1 / encoderClicksPerRevolution) * (Math.PI * wheelDiameterInches);
	}

	public void resetEncoders() {
		LeftEncoder.reset();
		RightEncoder.reset();
	}

	public void drive(double leftPower, double rightPower) {
//		double leftAdjustedPower = Math.pow(Math.abs(leftPower), (1.0/3.0));
//		double rightAdjustedPower = Math.pow(Math.abs(rightPower), (1.0/3.0));
//		if(leftPower < 0) {
//			leftAdjustedPower *= -1;
//		}
//		if(rightPower < 0) {
//			rightAdjustedPower *= -1;
//		}
		myRobot.tankDrive(curveInput(leftPower), curveInput(rightPower));
	}
	public void arcadeDrive(double speed, double rotation ) {
		myRobot.arcadeDrive(curveInput(speed), curveInput(rotation));
		
	}

	private double curveInput(double input) {
		double adjustedInput = Math.pow(Math.abs(input), (1.0/3.0));
		if(input < 0) {
			adjustedInput *= -1;
		}
		return adjustedInput;
	}
	
	public void reset() {
		ahrs.reset();
	}

	public double getSonicDistance() {
		return sonic.getRangeInches() - ultrasonicOffset;
	}

	public void displayData() {
		//		putAHRSOnDashboard();
		SmartDashboard.putNumber("Ultrasonic Distance in inches    ", getSonicDistance());
		//		SmartDashboard.putNumber("Left Encoder Distance in inches  ", getLeftEncoderInches());
		//		SmartDashboard.putNumber("Right Encoder Distance in inches ", getRightEncoderInches());
		SmartDashboard.putNumber("Gyroscopic angle in degrees      ", getRotation());
		SmartDashboard.putNumber("left encoder val inches",getLeftEncoderInches());
		SmartDashboard.putNumber("right encoder val inches", getRightEncoderInches());
		//		SmartDashboard.putNumber("PID rate", rate);
		
	}

	public void putAHRSOnDashboard() {
		/* Display 6-axis Processed Angle Data                                      */
		SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
		SmartDashboard.putBoolean(  "IMU_IsCalibrating",    ahrs.isCalibrating());
		SmartDashboard.putNumber(   "IMU_Yaw",              ahrs.getYaw());
		SmartDashboard.putNumber(   "IMU_Pitch",            ahrs.getPitch());
		SmartDashboard.putNumber(   "IMU_Roll",             ahrs.getRoll());

		/* Display tilt-corrected, Magnetometer-based heading (requires             */
		/* magnetometer calibration to be useful)                                   */

		SmartDashboard.putNumber(   "IMU_CompassHeading",   ahrs.getCompassHeading());

		/* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
		SmartDashboard.putNumber(   "IMU_FusedHeading",     ahrs.getFusedHeading());

		/* These functions are compatible w/the WPI Gyro Class, providing a simple  */
		/* path for upgrading from the Kit-of-Parts gyro to the navx-MXP            */

		SmartDashboard.putNumber(   "IMU_TotalYaw",         ahrs.getAngle());
		SmartDashboard.putNumber(   "IMU_YawRateDPS",       ahrs.getRate());

		/* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */

		SmartDashboard.putNumber(   "IMU_Accel_X",          ahrs.getWorldLinearAccelX());
		SmartDashboard.putNumber(   "IMU_Accel_Y",          ahrs.getWorldLinearAccelY());
		SmartDashboard.putBoolean(  "IMU_IsMoving",         ahrs.isMoving());
		SmartDashboard.putBoolean(  "IMU_IsRotating",       ahrs.isRotating());

		/* Display estimates of velocity/displacement.  Note that these values are  */
		/* not expected to be accurate enough for estimating robot position on a    */
		/* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
		/* of these errors due to single (velocity) integration and especially      */
		/* double (displacement) integration.                                       */

		SmartDashboard.putNumber(   "Velocity_X",           ahrs.getVelocityX());
		SmartDashboard.putNumber(   "Velocity_Y",           ahrs.getVelocityY());
		SmartDashboard.putNumber(   "Displacement_X",       ahrs.getDisplacementX());
		SmartDashboard.putNumber(   "Displacement_Y",       ahrs.getDisplacementY());

		/* Display Raw Gyro/Accelerometer/Magnetometer Values                       */
		/* NOTE:  These values are not normally necessary, but are made available   */
		/* for advanced users.  Before using this data, please consider whether     */
		/* the processed data (see above) will suit your needs.                     */

		SmartDashboard.putNumber(   "RawGyro_X",            ahrs.getRawGyroX());
		SmartDashboard.putNumber(   "RawGyro_Y",            ahrs.getRawGyroY());
		SmartDashboard.putNumber(   "RawGyro_Z",            ahrs.getRawGyroZ());
		SmartDashboard.putNumber(   "RawAccel_X",           ahrs.getRawAccelX());
		SmartDashboard.putNumber(   "RawAccel_Y",           ahrs.getRawAccelY());
		SmartDashboard.putNumber(   "RawAccel_Z",           ahrs.getRawAccelZ());
		SmartDashboard.putNumber(   "RawMag_X",             ahrs.getRawMagX());
		SmartDashboard.putNumber(   "RawMag_Y",             ahrs.getRawMagY());
		SmartDashboard.putNumber(   "RawMag_Z",             ahrs.getRawMagZ());
		SmartDashboard.putNumber(   "IMU_Temp_C",           ahrs.getTempC());

		/* Omnimount Yaw Axis Information                                           */
		/* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount  */
		AHRS.BoardYawAxis yaw_axis = ahrs.getBoardYawAxis();
		SmartDashboard.putString(   "YawAxisDirection",     yaw_axis.up ? "Up" : "Down" );
		SmartDashboard.putNumber(   "YawAxis",              yaw_axis.board_axis.getValue() );

		/* Sensor Board Information                                                 */
		SmartDashboard.putString(   "FirmwareVersion",      ahrs.getFirmwareVersion());

		/* Quaternion Data                                                          */
		/* Quaternions are fascinating, and are the most compact representation of  */
		/* orientation data.  All of the Yaw, Pitch and Roll Values can be derived  */
		/* from the Quaternions.  If interested in motion processing, knowledge of  */
		/* Quaternions is highly recommended.                                       */
		SmartDashboard.putNumber(   "QuaternionW",          ahrs.getQuaternionW());
		SmartDashboard.putNumber(   "QuaternionX",          ahrs.getQuaternionX());
		SmartDashboard.putNumber(   "QuaternionY",          ahrs.getQuaternionY());
		SmartDashboard.putNumber(   "QuaternionZ",          ahrs.getQuaternionZ());

		/* Connectivity Debugging Support                                           */
		SmartDashboard.putNumber(   "IMU_Byte_Count",       ahrs.getByteCount());
		SmartDashboard.putNumber( "IMU_Update_Count", ahrs.getUpdateCount());
	}
}

