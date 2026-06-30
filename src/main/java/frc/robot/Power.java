package frc.robot;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Watts;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.function.Supplier;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.RobotController;
import frc.robot.util.FullSubsystem;

public class Power extends FullSubsystem {
  public record Device(String name, Supplier<Double> currentAmps) {};

  private static final Power INSTANCE = new Power();
  public static Power getInstance() { return INSTANCE; }

  private static final String ENERGY_FOLDER = "Energy/";
  private static Map<String, Device[]> subsystemDevices = new HashMap<>();
  private Power() {}

  @Override
  public void periodicAfterScheduler() {
    double batteryVoltage = RobotController.getBatteryVoltage();
    DoubleAdder totalCurrentAmps = new DoubleAdder();

    subsystemDevices.forEach((subsystem, devices) -> {
      double totalSubsystemCurrentAmps = 0.0;

      for (Device d : devices) {
        double deviceCurrentAmps = d.currentAmps.get();
        double devicePowerWatts = deviceCurrentAmps * batteryVoltage;
        Logger.recordOutput(ENERGY_FOLDER + subsystem + "/" + d.name + "/Current", Amps.of(deviceCurrentAmps));
        Logger.recordOutput(ENERGY_FOLDER + subsystem + "/" + d.name + "/Power", Watts.of(devicePowerWatts));

        totalSubsystemCurrentAmps += deviceCurrentAmps;
      }

      double totalSubsystemPowerWatts = batteryVoltage * totalSubsystemCurrentAmps;
      Logger.recordOutput(ENERGY_FOLDER + subsystem + "/Current", Amps.of(totalSubsystemCurrentAmps));
      Logger.recordOutput(ENERGY_FOLDER + subsystem + "/Power", Watts.of(totalSubsystemPowerWatts));

      totalCurrentAmps.add(totalSubsystemCurrentAmps);
    });

    Logger.recordOutput(ENERGY_FOLDER + "Total Power", batteryVoltage * totalCurrentAmps.sum());
  }

  public static void addSubsystem(String subsystem, Device... devices) {
    subsystemDevices.put(subsystem, devices);
  }
}