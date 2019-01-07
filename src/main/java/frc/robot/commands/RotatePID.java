package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotatePID extends Command {

	PIDController turnController = DriveTrain.sharedInstance().turnController;
	double angle;
	public RotatePID(double angle) {
		requires(Robot.myDrive);
		this.angle = angle;
		setTimeout(3);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		turnController.setSetpoint(angle);
		turnController.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return turnController.onTarget() || isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		turnController.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		turnController.disable();
	}
	
}
