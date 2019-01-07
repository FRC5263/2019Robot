package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.BucketArm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FlipBucket extends Command {

	private boolean hitSwitch = false;
	
    public FlipBucket() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.myBucketArm);
    	
    	setTimeout(1.0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	if(BucketArm.isUpperLimitClosed() == false) {
//    		hitSwitch = false;
//    	}
//    	
//    	if(!hitSwitch) {
//    		BucketArm.driveMotor(1.0);
//    	}
//    	if(BucketArm.isUpperLimitClosed()) {
//    		hitSwitch = true;
//    	}
    	
    	BucketArm.driveMotor(1.0);
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut() || hitSwitch;
    }

    // Called once after isFinished returns true
    protected void end() {
    	BucketArm.driveMotor(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	BucketArm.driveMotor(0.0);
    }
}
