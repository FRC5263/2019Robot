package org.usfirst.frc.team5263.robot.command.groups;

import org.usfirst.frc.team5263.robot.commands.Lift;
import org.usfirst.frc.team5263.robot.commands.Suck;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CubeIntakeAuton extends CommandGroup {

    public CubeIntakeAuton() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.e
    	
    	addSequential(new Lift(-1, 1));

    	addSequential(new Lift(.5, 1));
    	addSequential(new Suck(-1, 1));
    	
    }
}
