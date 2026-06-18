package frc.robot.hardware.sensors.io;

import org.littletonrobotics.junction.AutoLog;

public interface SensorTimeOfFlightIO {
  @AutoLog
  public static class SensorTimeOfFlightIOInputs {
    public boolean connected = false;
    public double rangeMM = 0.0;
  }

  public default void updateInputs(SensorTimeOfFlightIOInputs inputs) {}
}
