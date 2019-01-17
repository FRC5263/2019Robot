package frc.robot.command.groups;

import frc.robot.commands.DriveTo;
import frc.robot.commands.RotatePID;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class evanTest extends CommandGroup {

    public evanTest() {
        addSequential(new DriveTo(8, .3, 0, 5));
        addSequential(new RotatePID(90));
        addSequential(new DriveTo(8, .3, 90, 5));
        addSequential(new RotatePID(180));
        addSequential(new DriveTo(8, .3, 180, 5));
        addSequential(new RotatePID(270));
        addSequential(new DriveTo(8, .3, 270, 5));
        addSequential(new RotatePID(360));
        addSequential(new DriveTo(8, .3, 360, 5));
    }
}
