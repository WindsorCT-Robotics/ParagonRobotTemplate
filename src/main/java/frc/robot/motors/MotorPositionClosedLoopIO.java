package frc.robot.motors;

import frc.robot.motors.MotorIO.MotorIOInputs;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Angle;
import frc.robot.motors.MotorPositionClosedLoopIO.MotorPositionClosedLoopIOInputs;

public interface MotorPositionClosedLoopIO extends MotorIO<MotorPositionClosedLoopIOInputs> {
    @AutoLog
    public static class MotorPositionClosedLoopIOInputs extends MotorIOInputs {
        public double targetPositionRotations = 0.0;
    }

    /** Actions */

    /**
     * @param angle The target angle that the motor will attempt to move towards.
     */
    public void setTargetPosition(Angle angle);
}