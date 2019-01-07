package frc.robot.command.groups;

import frc.robot.commands.FlipBucket;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToShape extends CommandGroup {

	double turnPower;
	double drivePower;
	double degrees;
	double feet;
    public DriveToShape(double drivePower) {
    	
    	this.drivePower = drivePower;
    	
//    	addSequential(new DriveTo(12, .4, 0));
//    	addSequential(new RotatePID(90));
//    	
//    	addSequential(new DriveTo(1, .4, 90));
    	addSequential(new FlipBucket());
    }
}
