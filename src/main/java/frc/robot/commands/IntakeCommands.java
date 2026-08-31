package frc.robot.commands;

import org.wpilib.units.measure.AngularVelocity;
import org.wpilib.command2.Command;
import org.wpilib.command2.Commands;
import frc.robot.subsystems.intake.Intake;

public class IntakeCommands {
  private IntakeCommands() {}

  public static Command intake(Intake intake, AngularVelocity velocity) {
    return Commands.runEnd(() -> intake.setVelocity(velocity), () -> intake.stop(), intake);
  }
}
