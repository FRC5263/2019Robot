package org.usfirst.frc.team5263.robot.commands;

import org.usfirst.frc.team5263.robot.Robot;
import org.usfirst.frc.team5263.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveUntil extends Command {

	private double distance;
	private double power;
	private boolean isFinished = false;
	private double leftCorrection = 0;
	private double rightCorrection = 0;
	
    public DriveUntil(double distance, double power) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	setTimeout(4);
    	requires(Robot.myDrive);
    	this.distance = distance;
    	this.power = power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	DriveTrain.sharedInstance().resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute(){
//    	double difference = distance - DriveTrain.sharedInstance().getSonicDistance();
    	if(DriveTrain.sharedInstance().getSonicDistance() > distance) {
    		DriveTrain.sharedInstance().drive(power + leftCorrection, power + rightCorrection);
    	} else {
    		DriveTrain.sharedInstance().drive(0.0, 0.0);
    		isFinished = true;
    	}
    	
    	leftCorrection = -1 * ((DriveTrain.sharedInstance().getLeftEncoderInches() - DriveTrain.sharedInstance().getRightEncoderInches()) / 10);
		rightCorrection = 1 * ((DriveTrain.sharedInstance().getLeftEncoderInches() - DriveTrain.sharedInstance().getRightEncoderInches()) / 10);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
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
