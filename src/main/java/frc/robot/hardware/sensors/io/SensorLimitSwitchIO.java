package frc.robot.hardware.sensors.io;

import org.littletonrobotics.junction.AutoLog;

public interface SensorLimitSwitchIO {
  @AutoLog
  public static class SensorLimitSwitchIOInputs {
    public boolean connected = false;
    public boolean contact = false;
  }

  public default void updateInputs(SensorLimitSwitchIOInputs inputs) {}
}
