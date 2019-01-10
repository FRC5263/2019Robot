package frc.robot.command.groups;

import frc.robot.commands.DriveTo;
import frc.robot.commands.RotatePID;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class evanTest extends CommandGroup {

    public evanTest() {
        addSequential(new DriveTo(12, .5, 0, 10));
        addSequential(new RotatePID(90));
        addSequential(new DriveTo(12, .3, 0, 2));
    }
}
