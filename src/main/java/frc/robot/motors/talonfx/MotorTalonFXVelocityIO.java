package frc.robot.motors.talonfx;

import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Value;
import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Dimensionless;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.CanID;
import frc.robot.motors.MotorVelocityClosedLoopIO;

public class MotorTalonFXVelocityIO implements MotorVelocityClosedLoopIO {
    protected final TalonFX motor;
    private final ControlRequest controlRequest;
    private double targetVelocityRPM = 0.0;

    public MotorTalonFXVelocityIO(CanID can, VelocityControlRequest velocityControlRequest) {
        motor          = new TalonFX(can.ID());
        controlRequest = switch (velocityControlRequest) {
            case DUTYCYCLE -> new VelocityDutyCycle(0);
            case VOLTAGE -> new VelocityVoltage(0);
            case TORQUE -> new VelocityTorqueCurrentFOC(0);
        };
    }

    @Override
    public void setTargetVelocity(AngularVelocity angularVelocity) {
        if (controlRequest instanceof VelocityDutyCycle request) {
            motor.setControl(request.withVelocity(angularVelocity));
        } else if (controlRequest instanceof VelocityVoltage request) {
            motor.setControl(request.withVelocity(angularVelocity));
        } else if (controlRequest instanceof VelocityTorqueCurrentFOC request) {
            motor.setControl(request.withVelocity(angularVelocity));
        } else {
            throw new IllegalStateException("Illegal State of VelocityControlRequest");
        }

        targetVelocityRPM = angularVelocity.in(RPM);
    }

    @Override
    public void resetEncoder() {
        motor.setPosition(0);
    }

    @Override
    public void setDutyCycle(Dimensionless percent) {
        double clampedPercent = MathUtil.clamp(percent.in(Value), -1, 1);
        motor.setControl(new DutyCycleOut(clampedPercent));
    }

    @Override
    public void setVoltage(Voltage voltage) {
        double clampedVolts = MathUtil.clamp(voltage.in(Volts), -12, 12);
        motor.setVoltage(clampedVolts);
    }

    @Override
    public void stop() {
        motor.stopMotor();
    }

    @Override
    public void updateInputs(MotorVelocityClosedLoopIOInputs inputs) {
        inputs.connected          = motor.isConnected();
        inputs.positionRotations  = motor.getPosition().getValueAsDouble();
        inputs.velocityRPM        = motor.getVelocity().getValue().in(RPM);
        inputs.voltageVolts       = motor.getMotorVoltage().getValueAsDouble();
        inputs.currentAmps        = motor.getStatorCurrent().getValueAsDouble();
        inputs.powerWatts         = inputs.voltageVolts * inputs.currentAmps;
        inputs.temperatureCelsius = motor.getDeviceTemp().getValueAsDouble();

        inputs.targetVelocityRPM = targetVelocityRPM;
    }

    public enum VelocityControlRequest {
        DUTYCYCLE,
        VOLTAGE,
        TORQUE
    }
}