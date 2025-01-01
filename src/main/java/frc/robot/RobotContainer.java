// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.utility.IO;

public class RobotContainer {
  SendableChooser<Runnable> bindings = new SendableChooser<Runnable>();

  public IO io = new IO(bindings);

  public RobotContainer() {
    SmartDashboard.putData("Bindings", bindings);
    SmartDashboard.putData("Autonomous", new SequentialCommandGroup(
        new InstantCommand(() -> io.chassis.field_oritented = true)));

    io.configGlobal();
    io.config1Player();
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
