/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Bot;
import frc.robot.subsystems.DriveTrainSubsystem;

import frc.robot.Robot;
import frc.robot.OI.ButtonName;

public class DriverOperated extends Command {

  private Bot robot;
  private DriveTrainSubsystem drivetrain;
  private boolean finishEarly = false;
	private double driveSpeedFactor = .65;
	private boolean facingForward = true;

  public DriverOperated(Bot robot) {
    this.robot = robot;
    try {
      this.drivetrain = (DriveTrainSubsystem) this.robot.getSubsystem(DriveTrainSubsystem.class);
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

    SmartDashboard.putNumber("Ultrasonic: ", drivetrain.getSonicDistance());
    // Driver Button
    boolean ButtonA = Robot.m_oi.getButtonMain(ButtonName.A);
    boolean ButtonB = Robot.m_oi.getButtonMain(ButtonName.B);
    boolean ButtonX = Robot.m_oi.getButtonMain(ButtonName.X);
    boolean ButtonY = Robot.m_oi.getButtonMain(ButtonName.Y);

    // Operator Buttons
    boolean OperatorB = Robot.m_oi.getButton(ButtonName.B);
    boolean OperatorX = Robot.m_oi.getButton(ButtonName.X);
    boolean OperatorRB = Robot.m_oi.getButton(ButtonName.RB);
    boolean OperatorLB = Robot.m_oi.getButton(ButtonName.LB);

    // if(ButtonY){
    // facingForward = true;
    // }else if (ButtonA){
    // facingForward = false;
    // }

    /*
     * Axis 0 = Left Stick X Axis 1 = Left Stick Y Axis 4 - Right Stick X Axis 5 -
     * Right Stick Y
     */

    double rightStickY = Robot.m_oi.driverGamepad.getRawAxis(1) * -1;
    double rightStickX = Robot.m_oi.driverGamepad.getRawAxis(4);

    if (ButtonX) {
      System.out.println("Full Speed");
      driveSpeedFactor = 1;
    } else if (ButtonB) {
      System.out.println("65%");
      driveSpeedFactor = .65;
    }

    if (facingForward)
      this.drivetrain.arcadeDrive(rightStickY * driveSpeedFactor, rightStickX * driveSpeedFactor);
    else
      this.drivetrain.arcadeDrive(rightStickY * driveSpeedFactor * -1, rightStickX * driveSpeedFactor * -1);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return finishEarly;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
