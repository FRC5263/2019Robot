package org.usfirst.frc.team5263.robot.command.groups;

import org.usfirst.frc.team5263.robot.commands.DriveTo;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BreakBaseline extends CommandGroup {

    public BreakBaseline() {
        addSequential(new DriveTo(12, .5, 0, 10));
    }
}
