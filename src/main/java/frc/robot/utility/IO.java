package frc.robot.utility;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

public class IO extends SubsystemBase {
        public final CommandXboxController drive = new CommandXboxController(0);
        public final CommandXboxController mech = new CommandXboxController(1);

        public final Swerve chassis = new Swerve();

        public CommandScheduler scheduler = CommandScheduler.getInstance();

        public IO(SendableChooser<Runnable> bindings) {
                bindings.setDefaultOption("Single Player", this::config1Player);
                bindings.addOption("Two Player", this::config2Player);
                bindings.addOption("Testing", this::configTesting);
        }

        public void configGlobal(){
                DriverStation.silenceJoystickConnectionWarning(true);
        }

        public void config1Player() {
                chassis.setDefaultCommand(new DefaultDrive(this, drive));

                drive.rightStick().onTrue(new InstantCommand(() -> chassis.field_oritented = !chassis.field_oritented));

                drive.start().onTrue(new InstantCommand(chassis::resetOdometry));
        }

        public void config2Player() {
                drive.leftBumper().onTrue(new InstantCommand(() -> chassis.field_oritented = !chassis.field_oritented));
                // drive.rightBumper().onTrue(new InstantCommand(() -> chassis.slow_mode = !chassis.slow_mode));

                drive.back().onTrue(new InstantCommand(chassis::resetOdometry));
        }

        public void configTesting() {
                drive.povDownLeft().onTrue(new InstantCommand(chassis::resetAbsolute));
                drive.povUpLeft().onTrue(new InstantCommand(chassis::disable));
                drive.povDownRight().onTrue(new InstantCommand(chassis::enable));

        }

        StructPublisher<Pose2d> estimated_pose = NetworkTableInstance.getDefault().getTable("Debug")
                        .getStructTopic("Estimated Pose", Pose2d.struct).publish();

        @Override
        public void periodic() {

        }
}