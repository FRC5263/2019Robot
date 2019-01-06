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
public class LeftONLYLEFT extends CommandGroup {

    public LeftONLYLEFT() {
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
    			
    			addSequential(new DriveTo(7, .4, 0, 5));
    			addSequential(new RotatePID(-15));
    			addSequential(new DriveTo(5, .4, -15, 5));
    			}
    	}
    }
}    
   
