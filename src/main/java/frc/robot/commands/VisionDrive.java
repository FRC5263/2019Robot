package org.usfirst.frc.team5263.robot.commands;

import org.usfirst.frc.team5263.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class VisionDrive extends Command {

	private UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	
	@SuppressWarnings("deprecation")
	private NetworkTable tables = NetworkTable.getTable("GRIP/grip");
	
	private int camHeight = 360;
	private int camWidth = 480;
	
	double[] area;
	double[] centerXS;
	double[] centerYS;
	double[] widthS;
	double[] centerXarray;
	double[] widtharray;
	
    public VisionDrive() {
        requires(Robot.myVision);
       
    }

    // Called just before this Command runs the first time
    @SuppressWarnings("deprecation")
	protected void initialize() {
    	camera.setResolution(camWidth, camHeight);
    	
    	area = tables.getNumberArray("area", (double[])null);
    	
    	centerXS = tables.getNumberArray("centerX", (double[])null);
		centerYS = tables.getNumberArray("centerY", (double[])null);
		widthS = tables.getNumberArray("width", (double[])null);
		centerXarray = new double[1];
		widtharray = new double[1];
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println(area.toString());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
