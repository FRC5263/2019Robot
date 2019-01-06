 package org.usfirst.frc.team5263.robot.commands;

import org.usfirst.frc.team5263.robot.OI.ButtonName;
import org.usfirst.frc.team5263.robot.Robot;
import org.usfirst.frc.team5263.robot.subsystems.BucketArm;
import org.usfirst.frc.team5263.robot.subsystems.CubeIntake;
import org.usfirst.frc.team5263.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5263.robot.subsystems.Vision;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriverOperated extends Command {

	private double camAxisXRotation = 0.5;
	private double camAxisYRotation = 0.5;
	
	private double driveSpeedFactor = .65;
	private double intakeDriveSpeed = 1;
	
	private boolean facingForward = true;
    public DriverOperated() {
    	requires(Robot.myDrive);
    	requires(Robot.myVision);
    }


    protected void initialize() {
    }


    protected void execute() {
    	//Driver Button
    	boolean ButtonA = Robot.m_oi.getButtonMain(ButtonName.A);
    	boolean ButtonB = Robot.m_oi.getButtonMain(ButtonName.B);
    	boolean ButtonX = Robot.m_oi.getButtonMain(ButtonName.X);
    	boolean ButtonY = Robot.m_oi.getButtonMain(ButtonName.Y);
    	
    	
    	//Operator Buttons
    	boolean OperatorB = Robot.m_oi.getButton(ButtonName.B);
    	boolean OperatorX = Robot.m_oi.getButton(ButtonName.X);
    	boolean OperatorRB = Robot.m_oi.getButton(ButtonName.RB);
    	boolean OperatorLB = Robot.m_oi.getButton(ButtonName.LB);
    	
    	
    	//-----------------------------------------------------------------------------------------------------------------------------------------
    	//DRIVE TRAIN CONTROL
    	
//    	if(ButtonY){
//    		facingForward = true;
//    	}else if (ButtonA){
//    		facingForward = false;
//    	}
    	
    	
    	
    	/*
    	 * Axis 0 = Left Stick X
    	 * Axis 1 = Left Stick Y 
    	 * Axis 4 - Right Stick X 
    	 * Axis 5 - Right Stick Y
    	 */
    	
    	double rightStickY = Robot.m_oi.driverGamepad.getRawAxis(1) * -1; 
    	double rightStickX = Robot.m_oi.driverGamepad.getRawAxis(4);	
    	
    	if(ButtonX) {
    		System.out.println("Full Speed");
    		driveSpeedFactor = 1;
    	}else if(ButtonB){
    		System.out.println("65%");
    		driveSpeedFactor = .65;
    	}
    	
    	if(facingForward)
    		DriveTrain.sharedInstance().arcadeDrive(rightStickY * driveSpeedFactor, rightStickX * driveSpeedFactor);
    	else 
    		DriveTrain.sharedInstance().arcadeDrive(rightStickY * driveSpeedFactor *-1, rightStickX * driveSpeedFactor *-1);
    		
    	//-----------------------------------------------------------------------------------------------------------------------------------------
    	//INTAKE CONTROL
    	
    	double rightTrigger = Robot.m_oi.operatorGamepad.getRawAxis(3) * intakeDriveSpeed;
    	double leftTrigger = Robot.m_oi.operatorGamepad.getRawAxis(2) * intakeDriveSpeed;
    	
    	//Check that both triggers aren't being pushed
    	if(rightTrigger > 0.1 && leftTrigger > 0.1) {
    		CubeIntake.driveMotors(0.0);
    	}else {
    		//When the rightTrigger is held the CubeIntake will push the cube out
    		if(rightTrigger > .1) {
    			//Take the power of the trigger
    			//set that to the speed of the CubeIntake Motors
    			CubeIntake.driveMotors(rightTrigger);
    		}else if(leftTrigger > .1) {
    			//Take the power of the trigger
    			//set that to the speed of the CubeIntake Motors
    			//Made negative so the motors pull in
    			CubeIntake.driveMotors(-leftTrigger);
    		}else {
    			//Do nothing one last time
    			CubeIntake.driveMotors(0.0);
    		}
    	}
    	
    	//WE MIGHT NEED THIS LATER ON, STAY TUNED
    	
//    	double intakeLiftSpeed = Robot.m_oi.operatorGamepad.getRawAxis(1) * -1;
//    	CubeIntake.driveLiftMotor(intakeLiftSpeed * 0.5);
    	//System.out.println("STICK SPEED " + intakeLiftSpeed);
    	
    	
    	
    	//control intake lift 
    	if(OperatorB){
    		CubeIntake.driveLiftMotor(0.6);
    	}else if(OperatorX){
    		CubeIntake.driveLiftMotor(-.2);
    	}else{
    		CubeIntake.driveLiftMotor(0);
    	}
    	
    	//-----------------------------------------------------------------------------------------------------------------------------------------
    	//FLIP BUCKET
    	
    	boolean aButton = Robot.m_oi.getButton(ButtonName.A);
    	boolean yButton = Robot.m_oi.getButton(ButtonName.Y);
    	
    	double bucketLiftSpeed = 1.0;
    	
    	if(aButton & yButton) {
    		//We don't want to do anything
    		BucketArm.driveMotor(0.0);
    	}else {
    		if(aButton) {
    			BucketArm.driveMotor(-1 * bucketLiftSpeed);
    		}else if(yButton) {
    			BucketArm.driveMotor(1 * bucketLiftSpeed);
    		}else {
    			BucketArm.driveMotor(0.0);
    		}
    	}
    	
    	if(OperatorRB)
    		bucketLiftSpeed = 1.0;
    	else if(OperatorLB)
    		bucketLiftSpeed = 0.75;
//    	else
//    		System.out.println("We did nothing");
    	//-----------------------------------------------------------------------------------------------------------------------------------------
    	//CAMERA
    	
    	switch(Robot.m_oi.operatorGamepad.getPOV()) {
    	case 0: 
    		increaseYAxis();
    	break;
    	case 45: 
    		increaseYAxis();
    		increaseXAxis();
    	break;
    	case 90:
    		increaseXAxis();
    		break;
    	case 135:
    		decreaseYAxis();
    		increaseXAxis();
    		break;
    	case 180:
    		decreaseYAxis();
    		break;
    	case 225:
    		decreaseXAxis();
    		decreaseYAxis();
    		break;
    	case 270:
    		decreaseXAxis();
    		break;
    	case 315:
    		decreaseXAxis();
    		increaseYAxis();
    		break;
    	}
    	
    	Vision.setCamAxisX(camAxisXRotation);
    	Vision.setCamAxisY(camAxisYRotation);
    }

    private void increaseXAxis() {
    	if(camAxisXRotation < 0.99) {
    		camAxisXRotation += 0.01;   
    	}
    }
    
    private void increaseYAxis() {
    	if(camAxisYRotation < 0.99) {
    		camAxisYRotation += 0.01;
    	}
    }
    
    private void decreaseXAxis() {
    	if(camAxisXRotation > 0.01) {
    		camAxisXRotation -= 0.01;
    		
    				}
    }
    
    private void decreaseYAxis() {
    	if(camAxisYRotation > 0.01) {
    		camAxisYRotation -= 0.01;
    	}
    }
    
    //-----------------------------------------------------------------------------------------------------------------------------------------
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
