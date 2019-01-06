package org.usfirst.frc.team5263.robot.commands;


import org.usfirst.frc.team5263.robot.Robot;
import org.usfirst.frc.team5263.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;


/**
 *
 */
public class DriveToPID extends Command {


	private boolean driveByAngle = false;
	private double initialAngle;
	double driveDistanceInches;
	PIDController driveController;

	public DriveToPID(double driveDistanceInches, double angle) {
		construct(driveDistanceInches);
		this.initialAngle = angle;
		this.driveByAngle = true;
	}
	public DriveToPID(double driveDistanceInches) {
		construct(driveDistanceInches);
		this.driveByAngle = false;
	}
	
	private void construct(double driveDistanceInches) {
		requires(Robot.myDrive);
		driveController = DriveTrain.sharedInstance().driveController;
		this.driveDistanceInches = driveDistanceInches;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		DriveTrain.sharedInstance().resetEncoders();
		if(!driveByAngle) {
			initialAngle = DriveTrain.sharedInstance().getRotation();
		}
		DriveTrain.sharedInstance().driveControllerAngle = initialAngle;
		driveController.setSetpoint(driveDistanceInches);
		driveController.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

	}
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		System.out.println("DriveTo ran");
		return driveController.onTarget();
	}

	// Called once after isFinished returns true
	protected void end() {
		driveController.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		driveController.disable();
	}
}
