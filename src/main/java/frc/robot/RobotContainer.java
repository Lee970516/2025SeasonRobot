// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.ChangeMode;
// import frc.robot.commands.ArmTest_IntakeAlgae_Floor;
// import frc.robot.commands.ArmTest_IntakeCoral;
import frc.robot.commands.ManualDrive_Neo;
import frc.robot.commands.IntakeCommands.Coral_L1;
import frc.robot.commands.IntakeCommands.Coral_L2;
import frc.robot.commands.IntakeCommands.Coral_L3;
import frc.robot.commands.IntakeCommands.Coral_L4;
import frc.robot.commands.IntakeCommands.IntakeCoral;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.EndEffectorSubsystem;
import frc.robot.subsystems.PhotonVisionSubsystem;
import frc.robot.subsystems.SwerveSubsystem_Neo;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final ClimberSubsystem m_ClimberSubsystem = new ClimberSubsystem();
  private final PhotonVisionSubsystem m_PhotonVisionSubsystem = new PhotonVisionSubsystem();
  // private final SwerveSubsystem_Kraken m_SwerveSubsystem = new SwerveSubsystem_Kraken(m_PhotonVisionSubsystem);
  private final SwerveSubsystem_Neo m_SwerveSubsystem_Neo = new SwerveSubsystem_Neo(m_PhotonVisionSubsystem);
  private final ElevatorSubsystem m_ElevatorSubsystem = new ElevatorSubsystem();
  private final EndEffectorSubsystem m_EffectorSubsystem = new EndEffectorSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  private final CommandXboxController driverController = new CommandXboxController(0);
  // private final CommandXboxController operatorController = new CommandXboxController(OperatorConstants.kOperatorControllerPort);

  // private final SendableChooser<Command> autoChooser;

  public RobotContainer() {
    // Configure the trigger bindings
    // autoChooser = AutoBuilder.buildAutoChooser();
    configureBindings();
    // SmartDashboard.putData("Auto Mode", autoChooser);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    DoubleSupplier xSpeedFunc = ()-> driverController.getRawAxis(1);
    DoubleSupplier ySpeedFunc = ()-> driverController.getRawAxis(0);
    DoubleSupplier zSpeedFunc = ()-> driverController.getRawAxis(4);

    BooleanSupplier isSlowFunc = ()-> driverController.getHID().getLeftBumperButton();
    BooleanSupplier ifFeed = ()-> driverController.getHID().getLeftBumperButton();

    driverController.x().whileTrue(
      Commands.runOnce(()->{
        m_SwerveSubsystem_Neo.resetGyro();
      })
    );

    // driverController.rightBumper().whileTrue(new TrackRightReef(m_PhotonVisionSubsystem, m_SwerveSubsystem));
    // driverController.a().whileTrue(new AprilTagRotation(m_PhotonVisionSubsystem, m_SwerveSubsystem));
    // driverController.y().whileTrue(new AprilTagY(m_PhotonVisionSubsystem, m_SwerveSubsystem));
    // driverController.x().whileTrue(new AprilTagX(m_PhotonVisionSubsystem, m_SwerveSubsystem));

    // driverController.rightBumper().whileTrue(new TrackCage(m_SwerveSubsystem, m_PhotonVisionSubsystem));
    // driverController.a().whileTrue(new TrackCoralStation(m_PhotonVisionSubsystem, m_SwerveSubsystem));
    // driverController.x().whileTrue(new TrackCage(m_SwerveSubsystem, m_PhotonVisionSubsystem));
    // driverController.y().whileTrue(new TrackProcessor(m_SwerveSubsystem, m_PhotonVisionSubsystem));
    // driverController.pov(0).whileTrue(new TrackNet(m_SwerveSubsystem, m_PhotonVisionSubsystem));



    driverController.a().toggleOnTrue(new Coral_L1(m_ElevatorSubsystem, m_EffectorSubsystem, ifFeed));
    driverController.pov(270).toggleOnTrue(new Coral_L2(m_ElevatorSubsystem, m_EffectorSubsystem, ifFeed));
    driverController.pov(90).toggleOnTrue(new Coral_L3(m_ElevatorSubsystem, m_EffectorSubsystem, ifFeed));
    // driverController.pov(0).whileTrue(new IntakeAlgae_Low(m_ElevatorSubsystem, m_EffectorSubsystem));
    // driverController.pov(180).whileTrue(new IntakeAlgae_High(m_ElevatorSubsystem, m_EffectorSubsystem));
    driverController.rightTrigger(0.4).toggleOnTrue(new Coral_L4(m_ElevatorSubsystem, m_EffectorSubsystem, ifFeed));
    // driverController.leftTrigger(0.4).whileTrue(new ShootNet(m_ElevatorSubsystem, m_EffectorSubsystem));
    // driverController.leftBumper().whileTrue(new ShootProcessor(m_ElevatorSubsystem, m_EffectorSubsystem));
    driverController.b().toggleOnTrue(new IntakeCoral(m_ElevatorSubsystem, m_EffectorSubsystem));
    driverController.y().whileTrue(new ChangeMode());
    // driverController.x().whileTrue(new IntakeAlgae_Floor(m_ElevatorSubsystem, m_EffectorSubsystem));
    // operatorController.y().onTrue(new ClimbCommand(m_ClimberSubsystem));

    // driverController.rightBumper().whileTrue(new ArmTest_IntakeAlgae_Floor(m_EffectorSubsystem));
    // driverController.leftBumper().whileTrue(new ArmTest_IntakeAlgae_Floor(m_EffectorSubsystem));
    // driverController.a().whileTrue(new ArmTest_IntakeCoral(m_EffectorSubsystem));
    // driverController.rightBumper().whileTrue(new ArmTest_IntakeAlgae_High(m_EffectorSubsystem));
    // driverController.b().whileTrue(new ArmTest_OutCoral(m_EffectorSubsystem));

    // m_SwerveSubsystem.setDefaultCommand(new ManualDrive_Kraken(m_SwerveSubsystem, xSpeedFunc, ySpeedFunc, zSpeedFunc, isSlowFunc));
    m_SwerveSubsystem_Neo.setDefaultCommand(new ManualDrive_Neo(m_SwerveSubsystem_Neo, xSpeedFunc, ySpeedFunc, zSpeedFunc, isSlowFunc));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}
