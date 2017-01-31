package org.usfirst.frc.team3008.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.JoystickBase;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends SampleRobot {
	private static final AxisType Y = null;

	private static final AxisType Z = null;

	private static final AxisType X = null;

	RobotDrive robotDrive;

	// Channels for the wheels
	final int kFrontLeftChannel = 9;
	final int kRearLeftChannel = 8;
	final int kFrontRightChannel = 7;
	final int kRearRightChannel = 6;
	

	// The channel on the driver station that the joystick is connected to


	
	public static JoystickBase logitechAttack3D (int port) {
		Joystick joystick = new Joystick(1);

	    return ((Object) joystick).create(joystick::getRawAxis,
	                              joystick::getRawButton,
	                              joystick::getPOV,
	                              joystick::getY, // pitch
	                              () -> joystick.getTwist() * -1, // yaw is reversed
	                              joystick::getX); // roll
	                              /*joystick.getThrottle)  // throttle
	                              /*() -> joystick.getRawButton(1), //trigger*/
}
	
	
	public Robot() {
		robotDrive = new RobotDrive(kFrontLeftChannel, kRearLeftChannel, kFrontRightChannel, kRearRightChannel);
		robotDrive.setInvertedMotor(MotorType.kFrontLeft, false); // invert the
																	// left side
																	// motors
		robotDrive.setInvertedMotor(MotorType.kRearLeft, false); // you may need
																// to change or
																// remove this
																// to match your
																// robot
		robotDrive.setExpiration(0.1);
	}

	/**
	 * Runs the motors with Mecanum drive.
	 */
	@Override
	public void operatorControl() {
		robotDrive.setSafetyEnabled(true);
		while (isOperatorControl() && isEnabled()) {

			// Use the joystick X axis for lateral movement, Y axis for forward
			// movement, and Z axis for rotation.
			// This sample does not use field-oriented drive, so the gyro input
			// is set to zero.
			Joystick stick = new Joystick (0);
			Joystick stick1 = new Joystick (1);
			Joystick stick2 = new Joystick (2);
			
			double speedX = stick.getAxis(X);
			double speedY = stick.getAxis(Y);
			double speedZ = stick.getAxis(Z);
			
			robotDrive.mecanumDrive_Polar(speedX, speedY, speedZ);

			Timer.delay(0.05); // wait 5ms to avoid hogging CPU cycles
		}
	}


	private JoystickBase Joystick(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	private int getZ() {
		// TODO Auto-generated method stub
		return 0;
	}


	private int getX() {
		// TODO Auto-generated method stub
		return 0;
	}


	private int getY() {
		// TODO Auto-generated method stub
		return 0;
	}
}
