/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Bot;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.MotorSubsystem;
import frc.robot.subsystems.PneumaticsSubsystem;
import frc.robot.Robot;
import frc.robot.OI.AxisName;
import frc.robot.OI.ButtonName;
import frc.robot.helpers.OperatorInterface;
import frc.robot.helpers.POVFunction;
import frc.robot.helpers.ButtonFunction;
import frc.robot.helpers.AxisFunction;

public class DriverOperated extends Command {

  private Bot robot;
  private DriveTrainSubsystem drivetrain;
  // private MotorSubsystem actuator;
  private PneumaticsSubsystem pneumatics;
  private boolean finishEarly = false;
	private double driveSpeedFactor = .65;
	private boolean facingForward = true;

  public DriverOperated(Bot robot) {
    this.robot = robot;
    try {
      this.drivetrain = (DriveTrainSubsystem) this.robot.getSubsystem(Bot.DRIVETRAIN);
      System.out.println("GOT DRIVETRAIN!");
    } catch( Exception e) {
      this.finishEarly = true;
    }
    // try {
    //   this.actuator = (MotorSubsystem) this.robot.getSubsystem(MotorSubsystem.class);
    // } catch( Exception e) {
    //   this.finishEarly = true;
    // }
    try {
      this.pneumatics = (PneumaticsSubsystem) this.robot.getSubsystem(Bot.PNEUMATICS);
    } catch( Exception e) {
      this.finishEarly = true;
    }
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    drivetrain.sonicSetAutomatic();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    //boosts robot speed when HOLDING right bumper
    OperatorInterface.setButtonFunction(ButtonName.RB, true, new ButtonFunction(){
      @Override
      public void call() {
        boostSpeed();
      }
    }, new ButtonFunction(){
      @Override
      public void call() {
        reduceSpeed();
      }
    });

    //arcade drive
    OperatorInterface.setAxisFunction(AxisName.RIGHTSTICKY, new AxisFunction(){
      @Override
      public void call(Double rightStickY) {
        OperatorInterface.setAxisFunction(AxisName.RIGHTSTICKX, new AxisFunction(){
          @Override
          public void call(Double rightStickX) {
            arcadeDrive(rightStickX, rightStickY);
          }
        });
      }
    });

    //pneumatic drive
    OperatorInterface.setPOVFunction(false, new POVFunction(){
      @Override
      public void call(int pov) {
        drivePneumatics(pov);
      }
    });

    //ACTUATOR DRIVER CODE
    // if (ButtonA) {
    //   actuator.powerMotor(-0.3);
    // } else if(ButtonY) {
    //   actuator.powerMotor(0.3);
    // } else {
    //   actuator.powerMotor(0);
    // }

  }

  private void boostSpeed() {
    this.driveSpeedFactor = 1.0;
  }

  private void reduceSpeed() {
    this.driveSpeedFactor = .65;
  }

  private void arcadeDrive(double rightStickX, double rightStickY) {
    this.drivetrain.arcadeDrive(rightStickY * driveSpeedFactor, rightStickX * driveSpeedFactor);
  }

  private void drivePneumatics(int pov) {
    if(pov == 0) {
      pneumatics.setDirectionForward();
    } else if(pov == 180) {
      pneumatics.setDirectionReverse();
    } else if(pov == 90 || pov == 270) {
      pneumatics.setSolenoidOff();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return finishEarly;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("TELEOP CAME TO A STOP! most likely a problem with getting required subsystems");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
