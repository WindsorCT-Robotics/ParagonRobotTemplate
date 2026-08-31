package frc.robot.subsystems.intake;

import org.wpilib.units.measure.AngularVelocity;
import org.wpilib.command2.SubsystemBase;
import org.wpilib.driverstation.Alert;

import frc.robot.PowerLogger;
import frc.robot.PowerLogger.Device;
import org.littletonrobotics.junction.Logger;

public class Intake extends SubsystemBase {
  private final IntakeIO io;
  private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();
  private final Alert disconnectedMotorAlert =
      new Alert("Intake Motor disconnected.", Alert.Level.HIGH);

  public Intake(IntakeIO io) {
    this.io = io;
    PowerLogger.addSubsystem("Intake", new Device("Motor", () -> inputs.supplyCurrentAmps));
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("~MyInputs/Intake/", inputs);
    disconnectedMotorAlert.set(!inputs.connected);
  }

  public void setVelocity(AngularVelocity velocity) {
    io.setVelocity(velocity);
    Logger.recordOutput("~MyOutputs/Intake/VelocitySetpoint", velocity);
  }

  public void stop() {
    io.stop();
  }
}
