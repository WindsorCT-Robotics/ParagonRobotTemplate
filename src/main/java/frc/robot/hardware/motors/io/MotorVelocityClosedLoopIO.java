package frc.robot.hardware.motors.io;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.hardware.motors.io.MotorIO.MotorIOInputs;
import frc.robot.hardware.motors.io.MotorVelocityClosedLoopIO.MotorVelocityClosedLoopIOInputs;

public interface MotorVelocityClosedLoopIO extends MotorIO<MotorVelocityClosedLoopIOInputs> {
    @AutoLog
    public static class MotorVelocityClosedLoopIOInputs extends MotorIOInputs {
        public double targetVelocityRPM = 0.0;
    }

    /** Actions */

    /**
     * @param angularVelocity Sets the angular velocity target.
     */
    public void setTargetVelocity(AngularVelocity angularVelocity);
}
