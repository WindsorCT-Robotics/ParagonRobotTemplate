package frc.robot;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Watts;

import edu.wpi.first.wpilibj.RobotController;
import frc.robot.util.FullSubsystem;
import java.util.HashMap;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.function.DoubleSupplier;
import org.littletonrobotics.junction.Logger;

public class PowerLogger extends FullSubsystem {
  public record Device(String name, DoubleSupplier currentAmps) {}

  private static final String ROOT = "PowerLogger/";
  private static final HashMap<String, Device[]> subsystems = new HashMap<>();

  private PowerLogger() {}

  public static void addSubsystem(String subsystemName, Device device, Device... devices) {
    Device[] d = new Device[devices.length + 1];
    d[0] = device;
    for (int i = 1; i < d.length; i++) {
      d[i] = devices[i - 1];
    }
    subsystems.put(subsystemName, d);
  }

  public static void addSubsystem(String subsystemName, Device[] devices) {
    subsystems.put(subsystemName, devices);
  }

  @Override
  public void periodicAfterScheduler() {
    double batteryVoltage = RobotController.getBatteryVoltage();

    DoubleAdder totalCurrentAmps = new DoubleAdder();

    subsystems.forEach(
        (name, devices) -> {
          // "~" forces the folder to be lower.
          name = "~" + name;
          double totalSubsystemCurrentAmps = 0.0;

          for (Device device : devices) {
            double amps = device.currentAmps().getAsDouble();

            totalSubsystemCurrentAmps += amps;

            Logger.recordOutput(
                ROOT + name + "/~Devices/" + device.name() + "/Current", amps, Amps);
            Logger.recordOutput(
                ROOT + name + "/~Devices/" + device.name() + "/Power",
                batteryVoltage * amps,
                Watts);
          }

          Logger.recordOutput(ROOT + name + "/Current", totalSubsystemCurrentAmps, Amps);
          Logger.recordOutput(
              ROOT + name + "/Power", batteryVoltage * totalSubsystemCurrentAmps, Watts);

          totalCurrentAmps.add(totalSubsystemCurrentAmps);
        });

    double amps = totalCurrentAmps.doubleValue();
    Logger.recordOutput(ROOT + "Current", amps, Amps);
    Logger.recordOutput(ROOT + "Power", batteryVoltage * amps, Watts);
  }
}
