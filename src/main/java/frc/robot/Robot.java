/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package org.usfirst.frc.team5263.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5263.robot.command.groups.BreakBaseline;
import org.usfirst.frc.team5263.robot.command.groups.CenterAuton;
import org.usfirst.frc.team5263.robot.command.groups.DriveToShape;
import org.usfirst.frc.team5263.robot.command.groups.LeftAuton;
import org.usfirst.frc.team5263.robot.command.groups.LeftAutonExtraSucc;
import org.usfirst.frc.team5263.robot.command.groups.LeftONLYLEFT;
import org.usfirst.frc.team5263.robot.command.groups.RightAuton;
import org.usfirst.frc.team5263.robot.command.groups.SwitchAuton;
import org.usfirst.frc.team5263.robot.command.groups.rightONLYRIGHT;
import org.usfirst.frc.team5263.robot.commands.DriveTo;
import org.usfirst.frc.team5263.robot.commands.DriveUntil;
import org.usfirst.frc.team5263.robot.commands.DriverOperated;
import org.usfirst.frc.team5263.robot.commands.Lift;
import org.usfirst.frc.team5263.robot.commands.RotatePID;
import org.usfirst.frc.team5263.robot.commands.Rotation;
import org.usfirst.frc.team5263.robot.commands.VisionDrive;
import org.usfirst.frc.team5263.robot.commands.Wait;
import org.usfirst.frc.team5263.robot.subsystems.BucketArm;
import org.usfirst.frc.team5263.robot.subsystems.CubeIntake;
import org.usfirst.frc.team5263.robot.subsystems.DriveTrain;
//import org.usfirst.frc.team5263.robot.subsystems.Pneumatics;
//import org.usfirst.frc.team5263.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team5263.robot.subsystems.Vision;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
//	public static final ExampleSubsystem kExampleSubsystem
//			= new ExampleSubsystem();
	public static final DriveTrain myDrive = DriveTrain.sharedInstance();
	public static final Vision myVision = new Vision();
	public static final BucketArm myBucketArm = new BucketArm();
	public static final CubeIntake myCubeIntake = new CubeIntake();
	
	public static OI m_oi;
	Command teleop = new DriverOperated();
	Command m_autonomousCommand;
	SendableChooser<commandName> m_chooser = new SendableChooser<>();
	
	
	enum commandName{
		Left, Center, Right, Wait, Succ, Base, leftonly, rightonly;
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		CameraServer.getInstance().startAutomaticCapture();
		
		m_oi = new OI();
		
		m_chooser.addDefault("Default Auto", commandName.Wait);
		m_chooser.addObject("Left Auton", commandName.Left);
		m_chooser.addObject("Right Auton", commandName.Right);
		m_chooser.addObject("Center Auton", commandName.Center);
		m_chooser.addObject("Base", commandName.Base);
		m_chooser.addObject("Succ", commandName.Succ);
		m_chooser.addObject("RIGHTONLY RIGHT", commandName.rightonly);
		m_chooser.addObject("LEFTONLY LEFT", commandName.leftonly);
		SmartDashboard.putData("Auto mode", m_chooser);
		LiveWindow.add(DriveTrain.sharedInstance().turnController);
		LiveWindow.add(DriveTrain.sharedInstance().driveController);
		
		
	}

	/**	
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		teleop.cancel();
		DriveTrain.sharedInstance().reset();
		//very temporary, yell at me later
		Vision.setCamAxisX(0.5);
		Vision.setCamAxisY(0.5);
		System.out.println("autonomous init");
		if (m_autonomousCommand != null) {
			System.out.println("Cancelling existing command");
			m_autonomousCommand.cancel();
		}

		m_autonomousCommand = null;
		
		switch (m_chooser.getSelected()) {
		case Right:
			m_autonomousCommand = new SwitchAuton("R");
			break;
		case Left:
			m_autonomousCommand = new SwitchAuton("L");
			break;
		case Center:
			m_autonomousCommand = new SwitchAuton("C");
			break;
		case Wait:
			m_autonomousCommand = new Wait(1000);
			break;
		case Succ:
			m_autonomousCommand = new LeftAutonExtraSucc();
			break;
		case Base:
			m_autonomousCommand = new BreakBaseline();
			break;
		case leftonly:
			m_autonomousCommand = new LeftONLYLEFT();
			break;
		case rightonly:
			m_autonomousCommand = new rightONLYRIGHT();
			break;
		default:
			break;
		}

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		
		
		if (m_autonomousCommand != null) {
			System.out.println("selected command is " + m_autonomousCommand.getName());
			m_autonomousCommand.start();
		} else {
			System.out.println("selected command is NULL, running NO AUTON");
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Vision.setCamAxisX(0.5);
		Vision.setCamAxisY(0.5);
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		//Vision.setCamAxisX(0);
		//Vision.setCamAxisY(0);
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		teleop.start();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		//Vision.setCamAxisX(0);
		//Vision.setCamAxisY(0);
		Scheduler.getInstance().run();
		myVision.VisionPeriodic();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {		
		
	}

	@Override
	public void robotPeriodic() {
		DriveTrain.sharedInstance().displayData();
		CubeIntake.displayData();
	}
}
