package org.usfirst.frc.team5263.robot.commands;


import org.usfirst.frc.team5263.robot.Robot;
import org.usfirst.frc.team5263.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTo extends Command {

	private double encoderTarget;
	private double direction = 0;
	private boolean isFinished = false;
	private double leftCorrection = 0.0;
	private double rightCorrection = 0.0;
	private boolean driveByAngle = false;
	private double initialAngle;
	double driveDistanceFeet;
	double power;
	double seconds;

	public DriveTo(double driveDistanceFeet, double power, double angle, double seconds) {
		requires(Robot.myDrive);
		this.driveDistanceFeet = driveDistanceFeet;
		this.power = power;
		this.initialAngle = angle;
		this.driveByAngle = true;
		this.seconds = seconds;
		
		setTimeout(seconds);
	}
	public DriveTo(double driveDistanceFeet, double power, double seconds) {
		requires(Robot.myDrive);
		this.driveDistanceFeet = driveDistanceFeet;
		this.power = power;
		this.driveByAngle = false;
		this.seconds = seconds;
		
		setTimeout(seconds);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		DriveTrain.sharedInstance().resetEncoders();
		isFinished = false;
		if(!driveByAngle) {
			initialAngle = DriveTrain.sharedInstance().getRotation();
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		//Converts Feet to inches values
		encoderTarget = driveDistanceFeet * 12;
		// if target is negative, target is negative

		if(encoderTarget >= 0) {
			direction = 1.0;
		} else {
			direction = -1.0;
		}
		//then direction is negative

		encoderTarget = direction * (driveDistanceFeet * 12);
		//-1 * negative = positive

		//DriveTrain.getLeftEncoder()
		if(DriveTrain.sharedInstance().getLeftEncoderInches()*direction < encoderTarget) {
			DriveTrain.sharedInstance().drive((direction * power) + leftCorrection, (direction * power) + rightCorrection);
		}else {
			DriveTrain.sharedInstance().drive(0.0, 0.0);
			isFinished = true;
		}

//		leftCorrection = -1 * ((DriveTrain.getLeftEncoderInches() - DriveTrain.getRightEncoderInches()) / 10);
//		rightCorrection = 1 * ((DriveTrain.getLeftEncoderInches() - DriveTrain.getRightEncoderInches()) / 10);
		
		leftCorrection = -1 * ((DriveTrain.sharedInstance().getRotation() - initialAngle) / 80) ;
		rightCorrection = 1 * ((DriveTrain.sharedInstance().getRotation() - initialAngle) / 80) ;


	}
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		System.out.println("DriveTo ran");
		
		return isFinished || isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		isFinished = false;
		
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
