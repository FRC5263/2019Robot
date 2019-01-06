package org.usfirst.frc.team5263.robot.command.groups;

import org.usfirst.frc.team5263.robot.commands.DriveTo;
import org.usfirst.frc.team5263.robot.commands.FlipBucket;
import org.usfirst.frc.team5263.robot.commands.RotatePID;
import org.usfirst.frc.team5263.robot.commands.Wait;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterAuton extends CommandGroup {

    public CenterAuton() {
    	String gameData = DriverStation.getInstance().getGameSpecificMessage();
    	
    	if(gameData.length() > 0) {
			if(gameData.charAt(0) == 'L'){
				System.out.println("Run Left Auto");
				
				addSequential(new Wait(5));
				addSequential(new DriveTo(5, .4, 0, 3));
				addSequential(new RotatePID(-90));
				
				addSequential(new DriveTo(5, .4, -90, 3));
				addSequential(new RotatePID(0));
				addSequential(new DriveTo(3, .4, 0, 3));
				addSequential(new RotatePID(90));
				
				addSequential(new DriveTo(3, .4, 90, 2));
				addSequential(new FlipBucket());
				//This function runs if the data is for the right side
			}else {
				System.out.println("Run Right Auto");
				addSequential(new Wait(5));
				
				addSequential(new DriveTo(5, .4, 0, 3));
				addSequential(new RotatePID(90));
				
				addSequential(new DriveTo(5, .4, 90, 3));
				addSequential(new RotatePID(0));
				addSequential(new DriveTo(3, .4, 0, 2));
				addSequential(new RotatePID(-90));
				
				addSequential(new DriveTo(5, .4, 90, 4));
				addSequential(new FlipBucket());
			}
    	}
    }
}