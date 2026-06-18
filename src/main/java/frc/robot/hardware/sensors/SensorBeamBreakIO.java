package frc.robot.hardware.sensors;

public interface SensorBeamBreakIO {
  public static class SensorBeamBreakIOInputs {
    public boolean connected = false;
    public boolean broken = false;
  }

  public default void updateInputs(SensorBeamBreakIOInputs inputs) {}
}
