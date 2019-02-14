/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.helpers;
import frc.robot.Robot;
import frc.robot.OI.AxisName;
import frc.robot.OI.ButtonName;

import frc.robot.helpers.ButtonFunction;
import frc.robot.helpers.AxisFunction;

public class OperatorInterface{

    public static void setButtonFunction(ButtonName button, boolean driverController, ButtonFunction function) {
        if(driverController) {
            if(Robot.m_oi.getDriverButton(button)) {
                try{
                    function.call();
                } catch (Exception e) {
                    System.out.println("Could not run " + function.toString());
                }
            }
        } else {
            if(Robot.m_oi.getOperatorButton(button)){
                try{
                    function.call();
                } catch (Exception e) {
                    System.out.println("Could not run " + function.toString());
                }
            }
        }
    }

    public static void setButtonFunction(ButtonName button, boolean driverController, ButtonFunction truthyFunction, ButtonFunction falsyFunction) {
        if(driverController) {
            if(Robot.m_oi.getDriverButton(button)) {
                try{
                    truthyFunction.call();
                } catch (Exception e) {
                    System.out.println("Could not run " + truthyFunction.toString());
                }
            } else {
                try{
                    falsyFunction.call();
                } catch (Exception e) {
                    System.out.println("Could not run " + falsyFunction.toString());
                }
            }
        } else {
            if(Robot.m_oi.getOperatorButton(button)){
                try{
                    truthyFunction.call();
                } catch (Exception e) {
                    System.out.println("Could not run " + truthyFunction.toString());
                }
            } else {
                try{
                    falsyFunction.call();
                } catch (Exception e) {
                    System.out.println("Could not run " + falsyFunction.toString());
                }
            }
        }
    }

    public static void setLeftStickXFunction(AxisFunction function) {
        try{
            function.call(Robot.m_oi.getDriverAxis(AxisName.LEFTSTICKX));
        } catch (Exception e) {
            System.out.println("Could not run " + function.toString());
        }
    }
}

