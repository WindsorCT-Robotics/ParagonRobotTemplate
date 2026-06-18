package frc.robot.hardware.sensors.io;

import org.littletonrobotics.junction.AutoLog;

public interface SensorBeamBreakIO {
  @AutoLog
  public static class SensorBeamBreakIOInputs {
    public boolean connected = false;
    public boolean broken = false;
  }

  public default void updateInputs(SensorBeamBreakIOInputs inputs) {}
}
