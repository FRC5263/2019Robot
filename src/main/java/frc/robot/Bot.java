/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Generic constructor for all bot setups.
 */
public class Bot {
    private ArrayList<Subsystem> subsystems = new ArrayList<>(0);

    public Bot() {

    }

    public Bot(Subsystem[] subsystems) {
        for (Subsystem sub : subsystems) {
            this.subsystems.add(sub);
        }
    }

    public void addSubsystem(Subsystem subsystem) {
        this.subsystems.add(subsystem);
    }

    /**
     * @return a certain subsystem that is already on the 
     */
    public Subsystem getSubsystem(Class type) throws Exception {
        for(Subsystem sub : this.subsystems) {
            if(sub.getClass() == type) {
                return sub;
            }
        }
        throw new Exception("subsystem does not exist.");
    }
}