package frc.robot.motors;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Dimensionless;
import edu.wpi.first.units.measure.Voltage;

public interface MotorIO<T extends MotorIO.MotorIOInputs> {
    @AutoLog
    public static class MotorIOInputs {
        public boolean connected         = false;
        public double positionRotations  = 0.0;
        public double velocityRPM        = 0.0;
        public double voltageVolts       = 0.0;
        public double currentAmps        = 0.0;
        public double powerWatts         = 0.0;
        public double temperatureCelsius = 0.0;
    }

    public void updateInputs(T inputs);
    
    //** Actions */

    /**
     * Stops the motor.
     */
    public void stop();

    /**
     * Sets a duty cycle to the motor.
     * @param percent The percent of the dutycyle applied to the motor.
     */
    public void setDutyCycle(Dimensionless percent);

    /**
     * Sets a voltage to the motor.
     * @param voltage The voltage applied to the motor.
     */
    public void setVoltage(Voltage voltage);

    /**
     * Resets the relative encoder to position = 0.
     */
    public void resetEncoder();
}