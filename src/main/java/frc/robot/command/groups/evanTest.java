package frc.robot.command.groups;

import frc.robot.commands.DriveTo;
import frc.robot.commands.RotatePID;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class evanTest extends CommandGroup {

    public evanTest() {
       // addSequential(new DriveTo(5, .15, 0, 5));
        //addSequential(new RotatePID(360));
        addSequential(new RotatePID(360));
        //addSequential(new RotatePID(360));
       // addSequential(new DriveTo(3, .15, 135, 5));
       // addSequential(new RotatePID(0));
       // addSequential(new DriveTo(3, .15, 0, 5));
       // addSequential(new RotatePID(135));
       // addSequential(new DriveTo(5, .15, 135, 5));
        //addSequential(new RotatePID(360));
        //addSequential(new DriveTo(8, .3, 360, 5));
    }
}
