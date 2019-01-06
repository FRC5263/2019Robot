package org.usfirst.frc.team5263.robot.commands;

import org.usfirst.frc.team5263.robot.Robot;
import org.usfirst.frc.team5263.robot.subsystems.BucketArm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FlipBucketDown extends Command {

	private boolean hitSwitch;
	
    public FlipBucketDown() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.myBucketArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(BucketArm.isLowerLimitClosed() == false) {
    		hitSwitch = false;
    	}
    	
    	if(!hitSwitch) {
    		BucketArm.driveMotor(1.0);
    	}
    	if(BucketArm.isLowerLimitClosed()) {
    		hitSwitch = true;
    	}
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return hitSwitch;
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
