package org.usfirst.frc.team5263.robot.command.groups;

import org.usfirst.frc.team5263.robot.commands.DriveTo;
import org.usfirst.frc.team5263.robot.commands.FlipBucket;
import org.usfirst.frc.team5263.robot.commands.Lift;
import org.usfirst.frc.team5263.robot.commands.RotatePID;
import org.usfirst.frc.team5263.robot.commands.Suck;
import org.usfirst.frc.team5263.robot.commands.Wait;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftAutonExtraSucc extends CommandGroup {

    public LeftAutonExtraSucc() {
    	String gameData = DriverStation.getInstance().getGameSpecificMessage();
    	
    	if(gameData.length() > 0) {
    		if(gameData.charAt(0) == 'L'){
    			System.out.println("Run Left Auto");
    			
    			addSequential(new DriveTo(12, .4, 0, 6));
    			addSequential(new RotatePID(90));
    			
    			addSequential(new DriveTo(1, .4, 90, 1));
    			
    			addSequential(new Lift(-1, 0.2));
    			
    			addSequential(new FlipBucket());
    			//This function runs if the data is for the left side
    		}else {
    			System.out.println("Run Right Auto");
    			
    			addSequential(new DriveTo(17.5, .4, 0, 7));
    			addSequential(new RotatePID(90));
    			
    			addSequential(new DriveTo(13.25, 0.4, 90, 6));
    			addSequential(new RotatePID(180));
    			addSequential(new DriveTo(1.2, .2, 180, 1));
    			
    			System.out.println("RUN");
    			
    			addSequential(new Lift(-1, .2));
    			
    			addSequential(new FlipBucket());
    			
    			addSequential(new Wait(1));
    			
    			addSequential(new DriveTo(-1.5, .4, 180, .5));
    			
    			addParallel(new Lift(1, .2));
    			
    			addParallel(new DriveTo(1.5, .2, 180, .5));
    			
    			addParallel(new Suck(.5, 1.5));
    			}
    	}
    }
}    
   
