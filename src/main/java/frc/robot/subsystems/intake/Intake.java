package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Power;
import frc.robot.Power.Device;

public class Intake extends SubsystemBase {
  private final IntakeIO io;
  private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();
  private final Alert disconnectedMotorAlert = new Alert("Intake Motor disconnected.", AlertType.kError);
  
  public Intake(IntakeIO io) {
    this.io = io;
    Power.addSubsystem(getSubsystem(), new Device("Intake Motor", () -> inputs.statorCurrentAmps));
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Intake/Motor", inputs);
    disconnectedMotorAlert.set(!inputs.connected);
  }

  public void setVelocity(AngularVelocity velocity) {
    io.setVelocity(velocity);
  }

  public void stop() {
    io.stop();
  }
}