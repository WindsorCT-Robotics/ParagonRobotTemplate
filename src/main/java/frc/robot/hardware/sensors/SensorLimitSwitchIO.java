package frc.robot.hardware.sensors;

public interface SensorLimitSwitchIO {
  public static class SensorLimitSwitchIOInputs {
    public boolean connected = false;
    public boolean contact = false;
  }

  public default void updateInputs(SensorLimitSwitchIOInputs inputs) {}
}
