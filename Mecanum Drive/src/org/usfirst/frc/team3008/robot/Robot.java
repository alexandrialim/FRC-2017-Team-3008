package org.usfirst.frc.team3008.robot;
//Importing stuff from wpilib
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Spark;
//main drive class
public class Robot extends SampleRobot {
	RobotDrive robotDrive;
	Joystick joystick = new Joystick(0);//initializing joystick (should be changed to joyR) 
	Joystick turning = new Joystick(1);//initializing turning joy (change to joyL)
	Spark lift = new Spark(0); // initalizing lift spark
	
	// Channels for the wheels
	//Initializing ports
	final int kFrontLeftChannel = 8;
	final int kRearLeftChannel = 6;
	final int kRearRightChannel = 9;
	final int kFrontRightChannel = 7;

	
	public Robot() {
		System.out.println("Status:() : Succesfuly defined Robot()"); //Debug info (make UI later)
		robotDrive = new RobotDrive(kFrontLeftChannel, kRearLeftChannel, kFrontRightChannel, kRearRightChannel);
		robotDrive.setInvertedMotor(MotorType.kFrontLeft, true);// Inverts Front Left
		robotDrive.setInvertedMotor(MotorType.kRearRight, true);// Inverts Front Right
		System.out.println("Status:() : Finised motor inversion"); //Debug info (make UI later)
		robotDrive.setInvertedMotor(MotorType.kFrontRight, false);
		robotDrive.setExpiration(0.1);
		
	}
	//@Override
	//initializing joystick axises 
	
	double joystickX = 0;
	double joystickY = 0;
	double joystickZ = 0;
	double turningJoyX = 0;
	double turningJoyY = 0;
	//Initializing tank xyz as 0
	double yTank = 0;
	double xTank = 0;
	double zTank = 0;
	//initializing some random variables
	double otherPosTRUE = 0;
	boolean turningTrigger = turning.getTrigger();
	boolean driveTrigger = joystick.getTrigger();
	//initializing the maximum cussion and minimum cussion for easy editing
	double CUSSION_MAX = 2;
	double CUSSION_MIN = -2;
	//main body for functions
	public double sanicMode(){if(driveTrigger = true){return 2;}else{return 4;}} //returns 2 or 4 depending on if the trigger is down
	public double getTrueX(){if (joystickX < CUSSION_MAX && joystickX > CUSSION_MIN){return joystickX;}else return 0;}//returns cussioned X of joystick0
	public double getTrueY(){if (joystickY < CUSSION_MAX && joystickY > CUSSION_MIN){return joystickY;}else return 0;}//returns cussioned Y of joystick0
	public double getTurningTrueX(){if (turningJoyX < CUSSION_MAX && turningJoyX > CUSSION_MIN){return turningJoyX;}else return 0;}//returns cussioned X of joystick1
	public double getTurningTrueY(){if (turningJoyY < CUSSION_MAX && turningJoyY > CUSSION_MIN){return turningJoyY;}else return 0;}//returns cussioned Y of joystick1
	
	public void updateStickValues(){ // Update stick values function to be called inside for the main WHILE loop
		otherPosTRUE = 0;
		xTank = 0;
		yTank = 0;
		zTank = 0;
		//If joysticks are in x+ and x+ go forward and vice versa
		if (getTrueY() > 0 && getTurningTrueY() > 0 && otherPosTRUE == 0) {xTank = getTrueY() + getTurningTrueY() * -1 / sanicMode(); otherPosTRUE = 1;}//1
		if (getTrueY() < 0 && getTurningTrueY() < 0 && otherPosTRUE == 0) {xTank = getTrueY() + getTurningTrueY() * -1 / sanicMode(); otherPosTRUE = 1;}//-1
		// If joysticks are in x+ and x- turn in respective direction 
		if (getTrueY() < 0 && getTurningTrueY() > 0 && otherPosTRUE == 0) {yTank = getTrueY() * 2 / sanicMode(); otherPosTRUE = 1;}//1
		if (getTrueY() > 0 && getTurningTrueY() < 0 && otherPosTRUE == 0) {yTank = getTurningTrueY() * 2 / sanicMode(); otherPosTRUE = 1;}//-1
		//Straif based on joystick y axis
		if (getTrueX() > 0 && getTurningTrueX() > 0) {zTank = getTrueX() + getTurningTrueX() / sanicMode();}//1
		if (getTrueX() < 0 && getTurningTrueX() < 0) {zTank = getTrueY() + getTurningTrueY() / sanicMode();}// -1
		
	}
	
	public void done() {System.out.println("Status:() : All done!");}
	
	//lift running talon if turning trigger is true
	
	public void lift(){if (turningTrigger == true){lift.set(.7);}else{lift.set(0);}}
	
	public void operatorControl() {
		robotDrive.setSafetyEnabled(true);
		while (isOperatorControl() && isEnabled()) {
			//Updating joystickX and joystickY variables
			joystickX = joystick.getRawAxis(0);
			joystickY = joystick.getRawAxis(1);
			//Updating turningJoyX and turningJoyY
			turningJoyX = turning.getRawAxis(0);
			turningJoyY = turning.getRawAxis(1);
			//Updating 
			turningTrigger = turning.getTrigger();
			driveTrigger = joystick.getTrigger();
			
			updateStickValues(); // Updating yTank xTank and zTank see the function for more information
			lift(); //Oporation of the lift see lift function
			
			robotDrive.mecanumDrive_Cartesian(yTank, xTank, zTank, 0);
					
			//System.out.println(joystickX); //Debug info (make UI later)
			
			Timer.delay(0.005); // to prevent cpu hogging
		
		}
		
	}
	
}
