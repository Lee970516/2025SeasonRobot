// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.TrackCommands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.PhotonConstants;
import frc.robot.subsystems.PhotonVisionSubsystem;
import frc.robot.subsystems.SwerveSubsystem_Kraken;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AprilTagX extends Command {
  /** Creates a new TrackReef. */
  private final PhotonVisionSubsystem m_PhotonVisionSubsystem;
  private final SwerveSubsystem_Kraken m_SwerveSubsystem;

  private final PIDController xPidController;

  private double xPidMeasurements;
  private double xPidOutput;


  public AprilTagX(PhotonVisionSubsystem photonVisionSubsystem, SwerveSubsystem_Kraken swerveSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_PhotonVisionSubsystem = photonVisionSubsystem;
    this.m_SwerveSubsystem = swerveSubsystem;

    addRequirements(m_PhotonVisionSubsystem, m_SwerveSubsystem);
    // PID
    xPidController = new PIDController(PhotonConstants.xPidController_Kp, PhotonConstants.xPidController_Ki, PhotonConstants.xPidController_Kd);
    //set limit
    xPidController.setIntegratorRange(PhotonConstants.xPidMinOutput, PhotonConstants.xPidMaxOutput);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_SwerveSubsystem.drive(0, 0, 0, false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if(m_PhotonVisionSubsystem.hasFrontRightTarget()) {
      xPidMeasurements = m_PhotonVisionSubsystem.getXPidMeasurements_FrontRight();
      xPidMeasurements = Math.abs(xPidMeasurements - 1) > 0.05 ? xPidMeasurements : 1;
      xPidOutput = -Math.min(PhotonConstants.xPidMaxOutput, Math.max(PhotonConstants.xPidMinOutput, xPidController.calculate(xPidMeasurements, 1)));
    }else {
      xPidOutput = 0;
    }

    SmartDashboard.putNumber("AprilTagX/ pidOutput", xPidOutput);

    m_SwerveSubsystem.drive(xPidOutput, 0, 0, false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_SwerveSubsystem.drive(0, 0, 0, false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
