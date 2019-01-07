package frc.robot.command.groups;

import frc.robot.commands.DriveTo;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BreakBaseline extends CommandGroup {

    public BreakBaseline() {
        addSequential(new DriveTo(12, .5, 0, 10));
    }
}
