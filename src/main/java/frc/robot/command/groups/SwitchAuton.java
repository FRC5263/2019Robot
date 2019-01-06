package org.usfirst.frc.team5263.robot.command.groups;

import org.usfirst.frc.team5263.robot.commands.DriveTo;
import org.usfirst.frc.team5263.robot.commands.FlipBucket;
import org.usfirst.frc.team5263.robot.commands.Lift;
import org.usfirst.frc.team5263.robot.commands.RotatePID;
import org.usfirst.frc.team5263.robot.commands.Suck;
import org.usfirst.frc.team5263.robot.commands.Wait;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SwitchAuton extends CommandGroup {

	private String LRC;
	public String gameData;
	
    public SwitchAuton(String LRC) {
    	this.LRC = LRC;
    }
    
    private final double DrivePower = 0.5;
    private final double OneFifthDriveSpeed = 0.4;
    
    private final double LiftPower = 0.2;
    private final double LongSuckInSpeed = 0.5;
    
    private final double ShortFirstDriveLength = 12;
    private final double ShortSecondDriveLength = 1;
    
    private final double LongFirstDriveLength = 17.5;
    private final double LongSecondDriveLength = 13.25;
    private final double LongThirdDriveLength = 1;
    private final double LongFourthDriveLength = 1.5;
    
    protected void initialize() {
		
    	this.gameData = DriverStation.getInstance().getGameSpecificMessage();
    	if(LRC == "L"){
    		
    		if(gameData.length() > 0) {
        		if(gameData.charAt(0) == 'L'){
        			System.out.println("Run Left Auto");
        			
        			addSequential(new DriveTo(ShortFirstDriveLength, DrivePower, 0, 6));
        			addSequential(new RotatePID(90));
        			
        			addSequential(new DriveTo(ShortSecondDriveLength, DrivePower, 90, 1));
        			
        			addSequential(new Lift(-1, LiftPower));
        			
        			addSequential(new FlipBucket());
        			//This function runs if the data is for the right side
        		}else {
        			System.out.println("Run Right Auto");
        			
        			addSequential(new DriveTo(LongFirstDriveLength, DrivePower, 0, 7));
        			addSequential(new RotatePID(90));
        			
        			addSequential(new DriveTo(LongSecondDriveLength, DrivePower, 90, 6));
        			addSequential(new RotatePID(180));
        			
        			//Driving slower than usual
        			addSequential(new DriveTo(LongThirdDriveLength, OneFifthDriveSpeed, 180, 1));
        			
        			System.out.println("RUN");
        			addSequential(new Lift(-1, .2));

        			addSequential(new FlipBucket());
        			
        			addSequential(new Wait(1));
        			
        			addSequential(new DriveTo(-LongFourthDriveLength, DrivePower, 180, .5));
        			
        			addParallel(new DriveTo(LongFourthDriveLength, OneFifthDriveSpeed, 180, .5));
        			
        			addParallel(new Suck(LongSuckInSpeed, 1.5));	
        			}
        	}
    		
    	}else if(LRC == "R"){
    		if(gameData.length() > 0) {
    			if(gameData.charAt(0) == 'L'){
    				addSequential(new DriveTo(LongFirstDriveLength, DrivePower, 0, 7));
        			addSequential(new RotatePID(-90));
        			
        			addSequential(new DriveTo(LongSecondDriveLength, DrivePower, -90, 6));
        			addSequential(new RotatePID(-180));
        			
        			//Driving slower than usual
        			addSequential(new DriveTo(LongThirdDriveLength, OneFifthDriveSpeed, -180, 1));
        			
        			System.out.println("RUN");
        			addSequential(new Lift(-1, .2));

        			addSequential(new FlipBucket());
        			
        			addSequential(new Wait(1));
        			
        			addSequential(new DriveTo(-LongFourthDriveLength, DrivePower, -180, .5));
        			
        			addParallel(new DriveTo(LongFourthDriveLength, OneFifthDriveSpeed, -180, .5));
        			
        			addParallel(new Suck(LongSuckInSpeed, 1.5));
    				//This function runs if the data is for the right side
    			}else {
    				addSequential(new DriveTo(ShortFirstDriveLength, DrivePower, 0, 6));
        			addSequential(new RotatePID(-90));
        			
        			addSequential(new DriveTo(ShortSecondDriveLength, DrivePower, -90, 1));
        			
        			addSequential(new Lift(-1, LiftPower));
        			
        			addSequential(new FlipBucket());
    				
    			}
    		}
    	}else if(LRC == "C"){
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
    	}else{
    		System.out.println("No");
    	}	
    	
    	//This function runs if the field data is for the left side
    	
    	
    	
    	
    }
    
    
    private static void goLRC(){
		
	}
    
}
