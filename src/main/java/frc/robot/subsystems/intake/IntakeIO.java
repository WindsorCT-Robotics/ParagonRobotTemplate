package frc.robot.subsystems.intake;

import org.wpilib.units.measure.AngularVelocity;
import org.wpilib.units.measure.Dimensionless;
import org.wpilib.units.measure.Voltage;
import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {
  @AutoLog
  public static class IntakeIOInputs {
    public boolean connected = false;
    public boolean hasPiece = false;
    public double positionRotations = 0.0;
    public double velocityRPM = 0.0;
    public double voltageVolts = 0.0;
    public double statorCurrentAmps = 0.0;
    public double supplyCurrentAmps = 0.0;
    public double temperatureCelsius = 0.0;
  }

  public default void updateInputs(IntakeIOInputs inputs) {}

  public default void stop() {}

  public default void setDutyCycle(Dimensionless percent) {}

  public default void setVoltage(Voltage voltage) {}

  public default void setVelocity(AngularVelocity velocity) {}

  public default void resetEncoder() {}
}
