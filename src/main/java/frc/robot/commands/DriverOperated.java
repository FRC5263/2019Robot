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
  private MotorSubsystem actuator;
  private MotorSubsystem bucket;
  private MotorSubsystem suck;
  private PneumaticsSubsystem pneumatics;
  private boolean finishEarly = false;
	private double driveSpeedFactor = .45;
	private boolean facingForward = true;

  public DriverOperated(Bot robot) {
    this.robot = robot;
    try {
      this.drivetrain = (DriveTrainSubsystem) this.robot.getSubsystem(Bot.DRIVETRAIN);
      System.out.println("GOT DRIVETRAIN!");
    } catch( Exception e) {
      this.finishEarly = true;
    }
    try {
      this.pneumatics = (PneumaticsSubsystem) this.robot.getSubsystem(Bot.PNEUMATICS);
    } catch( Exception e) {
      this.finishEarly = true;
    }

    // try {
    //   this.bucket = (MotorSubsystem) this.robot.getSubsystem(Bot.BUCKET);
    // }catch(Exception e){
    //   this.finishEarly = true;
    // }
    try {
      this.actuator = (MotorSubsystem) this.robot.getSubsystem(Bot.ACTUATOR);
    }catch(Exception e){
      this.finishEarly = true;
    }
    try {
      this.bucket = (MotorSubsystem) this.robot.getSubsystem(Bot.BUCKET);
    }catch(Exception e){
      this.finishEarly = true;
    }
    try {
      this.suck = (MotorSubsystem) this.robot.getSubsystem(Bot.SUCK);
    }catch(Exception e){
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
    OperatorInterface.setButtonFunction(ButtonName.RB, true, 
    new ButtonFunction(){
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

    // //arcade drive
    // OperatorInterface.setAxisFunction(AxisName.LEFTSTICKY, true, new AxisFunction(){
    //   @Override
    //   public void call(Double leftStickY) {
    //     OperatorInterface.setAxisFunction(AxisName.RIGHTSTICKX, true, new AxisFunction(){
    //       @Override
    //       public void call(Double rightStickX) {
    //         arcadeDrive(rightStickX, leftStickY * -1);
    //       }
    //     });
    //   }
    // });

        // //arcade drive
    OperatorInterface.setAxisFunction(AxisName.LEFTSTICKY, false, new AxisFunction(){
      @Override
      public void call(Double leftStickY) {
        OperatorInterface.setAxisFunction(AxisName.RIGHTSTICKX, false, new AxisFunction(){
          @Override
          public void call(Double rightStickX) {
            arcadeDrive(rightStickX, leftStickY * -1);
          }
        });
      }
    });

    //pneumatic drive
    OperatorInterface.setPOVFunction(true, new POVFunction(){
      @Override
      public void call(int pov) {
        drivePneumatics(pov);
      }
    });


    // OperatorInterface.setAxisFunction(AxisName.RIGHTTRIGGER,false, new AxisFunction(){
    //   @Override
    //   public void call(Double rightTrigger){
    //     OperatorInterface.setAxisFunction(AxisName.LEFTTRIGGER ,false, new AxisFunction(){
    //       @Override
    //       public void call(Double leftTrigger){
    //         if(rightTrigger > 0 && leftTrigger > 0){
              
    //         }else if(rightTrigger > 0){
    //           bucketRun(rightTrigger);
    //         }else if(leftTrigger > 0){
    //           bucketRun(-leftTrigger);
    //         }
    //       }
    //     });
    //   }
    // });

    //ACTUATOR DRIVER CODE
    // if (ButtonA) {
    //   actuator.powerMotor(-0.3);
    // } else if(ButtonY) {
    //   actuator.powerMotor(0.3);
    // } else {
    //   actuator.powerMotor(0);
    // }

    OperatorInterface.setAxisFunction(AxisName.LEFTSTICKY, true, new AxisFunction(){
      @Override
      public void call(Double axisValue) {
        driveActuator(-axisValue);
      }
    });

    OperatorInterface.setAxisFunction(AxisName.RIGHTSTICKY, true, new AxisFunction(){
      @Override
      public void call(Double axisValue) {
        powerBucket(axisValue);
      }
    });

    OperatorInterface.setAxisFunction(AxisName.RIGHTTRIGGER, true, new AxisFunction(){
      @Override
      public void call(Double rightTrigger) {
        OperatorInterface.setAxisFunction(AxisName.LEFTTRIGGER, true, new AxisFunction(){
          @Override
          public void call(Double leftTrigger) {
              driveSuck(rightTrigger - leftTrigger);
              // System.out.println(rightTrigger - leftTrigger);
          }
        });
      }
    });

  }

  private void boostSpeed() {
    this.driveSpeedFactor = 0.85;
  }

  private void reduceSpeed() {
    this.driveSpeedFactor = .45;
  }

  private void arcadeDrive(double rightStickX, double rightStickY) {
    this.drivetrain.arcadeDrive(rightStickY * driveSpeedFactor, rightStickX * driveSpeedFactor);
  }

  private void powerBucket(Double power){
    this.bucket.powerMotor(power);
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

  private void driveActuator(Double power) {
    if(power > 0.0) {
      this.actuator.powerMotor(power * 0.8);
    } else {
      this.actuator.powerMotor(power * 0.3);
    }
    // System.out.println("POWER: " + power);
  }

  private void driveSuck(double power) {
    this.suck.powerMotor(power);
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
