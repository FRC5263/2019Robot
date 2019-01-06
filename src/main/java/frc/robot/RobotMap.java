/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5263.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//robot type
	public static final boolean isCompetitionBot = true;
//	public static final boolean isCompetitionBot = false;
	
	//DIO channel variables
	public static final int leftEncoderChannelA = 0;
	public static final int leftEncoderChannelB = 1;
	public static final int rightEncoderChannelA = 2;
	public static final int rightEncoderChannelB = 3;
	public static final int ultrasonicInputChannel = 4;	 //
	public static final int ultrasonicOutputChannel = 5; //
    public static final int upperLimitSwitchChannel = 6; // not connected
    public static final int lowerLimitSwitchChannel = 7; // not connected 
	public static final int intakeLiftEncoderChannelA = 8;
	public static final int intakeLiftEncoderChannelB = 9;
	
	//PWM channel variables
    	//for drive train
    public static final int leftDriveMotorChannel = 0;
    public static final int rightDriveMotorChannel = 1;
		//for cube intake
	public static final int intakeMotorLeftChannel = 2;
	public static final int intakeMotorRightChannel = 3;
	public static final int liftMotorChannel = 4; //double check
	public static final int intakeLiftMotorChannel = 7;
		//for camera
	public static final int cameraXServoChannel = 5;
	public static final int cameraYServoChannel = 6;
	
	
	public static final int SpikeRelayChannel = 0;
	
	
}
