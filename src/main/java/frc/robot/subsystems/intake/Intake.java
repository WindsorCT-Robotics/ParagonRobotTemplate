package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Power;
import frc.robot.Power.Device;
import frc.robot.hardware.motors.io.MotorVelocityClosedLoopIO;
import frc.robot.hardware.motors.io.MotorVelocityClosedLoopIOInputsAutoLogged;

public class Intake extends SubsystemBase {
  private final MotorVelocityClosedLoopIO motor;
  private final MotorVelocityClosedLoopIOInputsAutoLogged inputs = new MotorVelocityClosedLoopIOInputsAutoLogged();
  private final Alert disconnectedMotorAlert = new Alert("Intake Motor disconnected.", AlertType.kError);
  
  public Intake(MotorVelocityClosedLoopIO motor) {
    this.motor = motor;
    Power.addSubsystem(getSubsystem(), new Device("Intake Motor", () -> inputs.currentAmps));
  }

  @Override
  public void periodic() {
    motor.updateInputs(inputs);
    Logger.processInputs("Intake/Motor", inputs);
    disconnectedMotorAlert.set(!inputs.connected);
  }

  public void setVelocity(AngularVelocity velocity) {
    motor.setTargetVelocity(velocity);
  }

  public void stop() {
    motor.stop();
  }
}