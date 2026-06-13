package frc.robot.motors;

import frc.robot.motors.MotorIO.MotorIOInputs;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.motors.MotorVelocityClosedLoopIO.MotorVelocityClosedLoopIOInputs;

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
