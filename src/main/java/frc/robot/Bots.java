/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Bot;
import frc.robot.subsystems.DriveTrainSubsystem;

//import hardware
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Ultrasonic;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;


/**
 * Storage of Different Bot configurations.
 */
public class Bots {

    public static Bot createCompetitionBot(){
        return new Bot(
            new Subsystem[]{
                new DriveTrainSubsystem(
                    new SpeedControllerGroup(new WPI_TalonSRX(2), new WPI_TalonSRX(3)),
                    new SpeedControllerGroup(new WPI_TalonSRX(4), new WPI_TalonSRX(5)),
                    null, // new Encoder(-1, -1),
                    null, // new Encoder(-1, -1),
                    null, // new Ultrasonic(-1, -1),
                    new AHRS(SPI.Port.kMXP)
                    )
            }
        );
    }
    

}
