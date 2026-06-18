package frc.robot.motors.talonfx;

import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.Value;
import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.PositionTorqueCurrentFOC;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Dimensionless;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.CanID;
import frc.robot.motors.MotorPositionClosedLoopIO;

public class MotorTalonFXPositionIO implements MotorPositionClosedLoopIO {
  protected final TalonFX motor;
  private final ControlRequest controlRequest;
  private double targetPositionRotations = 0.0;

  public MotorTalonFXPositionIO(CanID can, PositionControlRequest positionControlRequest) {
    motor = new TalonFX(can.ID());
    controlRequest = switch (positionControlRequest) {
      case DUTYCYCLE -> new PositionDutyCycle(0);
      case VOLTAGE   -> new PositionVoltage(0);
      case TORQUE    -> new PositionTorqueCurrentFOC(0);
    };
  }

  @Override
  public void setTargetPosition(Angle angle) {
    if (controlRequest instanceof PositionDutyCycle request) {
      motor.setControl(request.withPosition(angle));
    } else if (controlRequest instanceof PositionVoltage request) {
      motor.setControl(request.withPosition(angle));
    } else if (controlRequest instanceof PositionTorqueCurrentFOC request) {
      motor.setControl(request.withPosition(angle));
    } else {
      throw new IllegalStateException("Illegal State of PositionControlRequest");
    }

    targetPositionRotations = angle.in(Rotations);
  }

  @Override
  public void resetEncoder() {
    motor.setPosition(0);
  }

  @Override
  public void setDutyCycle(Dimensionless percent) {
    double clampedPercent = MathUtil.clamp(percent.in(Value), -1, 1);
    motor.setControl(new DutyCycleOut(clampedPercent));
  }

  @Override
  public void setVoltage(Voltage voltage) {
    double clampedVolts = MathUtil.clamp(voltage.in(Volts), -12, 12);
    motor.setVoltage(clampedVolts);
  }

  @Override
  public void stop() {
    motor.stopMotor();
  }

  @Override
  public void updateInputs(MotorPositionClosedLoopIOInputs inputs) {
    inputs.connected               = motor.isConnected();
    inputs.positionRotations       = motor.getPosition().getValueAsDouble();
    inputs.velocityRPM             = motor.getVelocity().getValue().in(RPM);
    inputs.voltageVolts            = motor.getMotorVoltage().getValueAsDouble();
    inputs.currentAmps             = motor.getStatorCurrent().getValueAsDouble();
    inputs.powerWatts              = inputs.voltageVolts * inputs.currentAmps;
    inputs.temperatureCelsius      = motor.getDeviceTemp().getValueAsDouble();

    inputs.targetPositionRotations = targetPositionRotations;
  }

  public enum PositionControlRequest {
    DUTYCYCLE,
    VOLTAGE,
    TORQUE
  }
}