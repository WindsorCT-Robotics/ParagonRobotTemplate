package frc.robot.subsystems.led;

import java.util.Optional;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Led extends SubsystemBase {
    private final LedIO ledIO;
    private final LedIOInputsAutoLogged ledInputs = new LedIOInputsAutoLogged();
    private Optional<LedPattern> prevPattern = Optional.empty();

    public Led(LedIO ledIO) {
        this.ledIO = ledIO;
    }

    @Override
    public void periodic() {
        ledIO.updateInputs(ledInputs);
    }

    public void setPattern(LedPattern pattern, Color color, int startIndex, int endIndex) {
        if (prevPattern.isEmpty()) {
                ledIO.setPattern(pattern, color, startIndex, endIndex);
            prevPattern = Optional.of(pattern);
        } else {
            LedPattern presentPrevPattern = prevPattern.get();

            if (!presentPrevPattern.equals(pattern)) {
                ledIO.setPattern(pattern, color, startIndex, endIndex);
            };
            prevPattern = Optional.of(pattern);
        }
    }

    //** On setup, a parameter in another subsystem should have LedConsumer. With the LedConsumer, the allocated Led subsystem will supply the setPattern method, thus when the other subsystem accepts the method and pass in a pattern, it will call the setPattern method. */
    @FunctionalInterface
    public static interface LedConsumer {
        public void accept(LedPattern pattern);
    }
}