package frc.robot.subsystems.intake;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;

import frc.robot.CanID;
import frc.robot.hardware.motors.talonfx.MotorTalonFXVelocityIO;

public class IntakeMotorTalonFXIO extends MotorTalonFXVelocityIO {
  public IntakeMotorTalonFXIO(CanID can, VelocityControlRequest controlRequest) {
    super(can, controlRequest);

    TalonFXConfigurator configurator = motor.getConfigurator();
    TalonFXConfiguration configuration = new TalonFXConfiguration()
    .withCurrentLimits(
      new CurrentLimitsConfigs()
        .withStatorCurrentLimit(null)
        .withSupplyCurrentLimit(null)
    );

    configurator.apply(configuration);
  }
}
