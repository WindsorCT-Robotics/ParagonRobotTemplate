package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.hardware.motors.io.MotorVelocityClosedLoopIO;
import frc.robot.hardware.motors.io.MotorVelocityClosedLoopIOInputsAutoLogged;

public class Intake extends SubsystemBase {
  private final MotorVelocityClosedLoopIO motor;
  private final MotorVelocityClosedLoopIOInputsAutoLogged inputs = new MotorVelocityClosedLoopIOInputsAutoLogged();
  
  public Intake(MotorVelocityClosedLoopIO motor) {
    this.motor = motor;
  }

  @Override
  public void periodic() {
    motor.updateInputs(inputs);
    Logger.processInputs("Intake/Motor", inputs);
  }

  public void setVelocity(AngularVelocity velocity) {
    motor.setTargetVelocity(velocity);
  }

  public void stop() {
    motor.stop();
  }
}