package frc.robot.subsystems.led;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.wpilibj.util.Color;

public interface LedIO {
    @AutoLog
    public static class LedIOInputs {
        public boolean connected = false;
    }

    public default void setColor(Color color, int startIndex, int endIndex) {};

    public default void setPattern(LedPattern pattern, Color color, int startIndex, int endIndex) {};

    public default void updateInputs(LedIOInputs inputs) {};
}
