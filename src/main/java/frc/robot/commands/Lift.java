package org.usfirst.frc.team5263.robot.commands;

import org.usfirst.frc.team5263.robot.Robot;
import org.usfirst.frc.team5263.robot.subsystems.CubeIntake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Lift extends Command {

	
	double power;
    public Lift(double power, double timeout) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.myCubeIntake);
        setTimeout(timeout);
        this.power = power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//System.out.println("RUN PLease");
    	CubeIntake.driveLiftMotor(power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	CubeIntake.driveLiftMotor(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	CubeIntake.driveLiftMotor(0.0);
    }
}
