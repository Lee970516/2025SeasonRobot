// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.LEDConstants;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.EndEffectorSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class CoralL4ToPrimitive extends Command {
  private final EndEffectorSubsystem m_EndEffectorSubsystem;
  private final ElevatorSubsystem m_ElevatorSubsystem;
  /** Creates a new CoralL4ToPrimitive. */
  public CoralL4ToPrimitive(EndEffectorSubsystem endEffectorSubsystem, ElevatorSubsystem elevatorSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_EndEffectorSubsystem = endEffectorSubsystem;
    this.m_ElevatorSubsystem = elevatorSubsystem;

    addRequirements(m_EndEffectorSubsystem, m_ElevatorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_EndEffectorSubsystem.coralL4Primitive_Arm();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_EndEffectorSubsystem.arriveSetPoint()) {
      m_ElevatorSubsystem.toPrimitive();
      if(m_ElevatorSubsystem.arriveSetPoint()) {
        m_EndEffectorSubsystem.primitiveArm();
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
