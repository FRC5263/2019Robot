package frc.robot.command.groups;

import frc.robot.commands.DriveTo;
import frc.robot.commands.FlipBucket;
import frc.robot.commands.RotatePID;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightAuton extends CommandGroup {

    public RightAuton() {
    	String gameData = DriverStation.getInstance().getGameSpecificMessage();
    	
    	if(gameData.length() > 0) {
			if(gameData.charAt(0) == 'L'){
				System.out.println("Run Left Auto");
				
				addSequential(new DriveTo(17.5, .4, 0, 7));
    			addSequential(new RotatePID(-90));
    			
    			addSequential(new DriveTo(13.25, .4, -90, 6));
    			addSequential(new RotatePID(-180));
    			addParallel(new DriveTo(0.5, .4, -180, 1));
    			
				addSequential(new FlipBucket());
				//This function runs if the data is for the right side
			}else {
				System.out.println("Run Right Auto");
				
				addSequential(new DriveTo(12, .4, 0, 6));
    			addSequential(new RotatePID(-90));
    			
    			addSequential(new DriveTo(1, .4, -90, 1));
    			addSequential(new FlipBucket());
				
			}
		}
    }
}
