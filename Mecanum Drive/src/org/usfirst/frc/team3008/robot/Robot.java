package org.usfirst.frc.team3008.robot; // Package team3008~robot

import edu.wpi.first.wpilibj.DigitalInput;
//Importing stuff from wpilib such as joystick base and Robotdekkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkppppskkkkkkkkkkkkkkkkkkkkkkkkkk
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
//main drive class
public class Robot extends SampleRobot {
	
	RobotDrive robotDrive; // Robot drive called robotDrive (Yep lmao)
	Joystick joystick = new Joystick(0);//initializing joystick (should be changed to joyR) 
	Joystick turning = new Joystick(1);//initializing turning joy (change to joyL)
	Spark sparklift = new Spark(5); // initalizing lift spark
	DigitalInput limitSwitch; //naming limit switch 
	
	
	
	// Channels for the wheels
	//Initializing ports
	final int kFrontLeftChannel = 1;
	final int kRearLeftChannel = 0;
	final int kRearRightChannel = 2;
	final int kFrontRightChannel = 3;

	public void robotInit(){
		limitSwitch = new DigitalInput(0);
	}
	public Robot() {
		
		System.out.println("Status:() : Succesfuly defined Robot()"); //Debug info (make UI later)
		//setting up robotdrive to be a new RobotDrive with k-MOTORS
		robotDrive = new RobotDrive(kFrontLeftChannel, kRearLeftChannel, kFrontRightChannel, kRearRightChannel); // Defining k-MOTOR' to robotDrive which is a RobotDrive to be used in robotDrive.exampleFunction()
		//Inverting motors
		robotDrive.setInvertedMotor(MotorType.kFrontLeft, false);// Inverts Front Left
		robotDrive.setInvertedMotor(MotorType.kRearRight, true);// Inverts Front Right
		robotDrive.setInvertedMotor(MotorType.kFrontRight, true);//Unused invert for kFrontRight notice FALSE bool
		sparklift.setInverted(true);//lift motor is inverted
		System.out.println("Status:() : Finised motor inversion"); //Debug info (make UI later)
		robotDrive.setExpiration(0.1);
		
	}
	
	
	
	//@Override //This should remain comented
	
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
	
	//initializing some random variables/bools
	double otherPosTRUE = 0;
	boolean turningTrigger = turning.getTrigger();
	boolean driveTrigger = joystick.getTrigger();
	
	//initializing the maximum cussion and minimum cussion for easy editing
	double CUSSION_MAX = 2;
	double CUSSION_MIN = -2;
	
	
	//Main setup area of functions used!
	
	
	
	public double sanicMode(){
		if(driveTrigger = true){return 2;}else{return 4;}
	}//returns 2 or 4 depending on if the trigger is down
	
	
	
	public double getTrueX()
	{
		if (joystickX < CUSSION_MAX && joystickX > CUSSION_MIN){return joystickX;}else return 0;
		}//returns cussioned X of joystick0
	
	public double getTrueY()
	{
		if (joystickY < CUSSION_MAX && joystickY > CUSSION_MIN){return joystickY;}else return 0;
		}//returns cussioned Y of joystick0
	
	public double getTurningTrueX()
	{
		if (turningJoyX < CUSSION_MAX && turningJoyX > CUSSION_MIN){return turningJoyX;}else return 0;
		}//returns cussioned X of joystick1
	
	public double getTurningTrueY()
	{
		if (turningJoyY < CUSSION_MAX && turningJoyY > CUSSION_MIN){return turningJoyY;}else return 0;
		}//returns cussioned Y of joystick1
	
	public void updateStickValues(){ // Update stick values function to be called inside for the main WHILE loop
		
		otherPosTRUE = 0;
		xTank = 0;
		yTank = 0;
		zTank = 0;
		
		//If joysticks are in x+ and x+ go forward and vice versa
		if (getTrueY() > 0 && getTurningTrueY() > 0 && otherPosTRUE == 0) 
			{
			xTank = getTrueY() + getTurningTrueY() * -1 / sanicMode(); otherPosTRUE = 1;
			}//1
		
		if (getTrueY() < 0 && getTurningTrueY() < 0 && otherPosTRUE == 0) 
			{
			xTank = getTrueY() + getTurningTrueY() * -1 / sanicMode(); otherPosTRUE = 1;
			}//-1
		
		
		
		// If joysticks are in x+ and x- turn in respective direction 
		if (getTrueY() < 0 && getTurningTrueY() > 0 && otherPosTRUE == 0)
			{
			yTank = getTrueY() * 2 / sanicMode(); otherPosTRUE = 1;
			}//1
		
		if (getTrueY() > 0 && getTurningTrueY() < 0 && otherPosTRUE == 0)
			{
			yTank = getTurningTrueY() * 2 / sanicMode(); otherPosTRUE = 1;
			}//-1
		
		
		
		//Straif based on joystick y axis
		if (getTrueX() > 0 && getTurningTrueX() > 0) 
			{
				zTank = getTrueX() + getTurningTrueX() / sanicMode();
			}//1
		
		if (getTrueX() < 0 && getTurningTrueX() < 0)
			{
				zTank = getTrueY() + getTurningTrueY() / sanicMode();
			}// -1
		
	}
	
	public void done() {System.out.println("Status:() : All done!");}
	
	//lift running lift spark if turning trigger is true
	public void lift(){if (turningTrigger == true){sparklift.set(0.7);}else{sparklift.set(0);}}
	
	public void operatorControl() {
		robotDrive.setSafetyEnabled(false);
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
			
			robotDrive.mecanumDrive_Cartesian(yTank, xTank, zTank, 0); // Runing robot drive with yTank xTank and zTank from PREDET...
			
			Timer.delay(0.005); // to prevent cpu hogging
			
			
		
			}	
		while (limitSwitch.get()){
			Timer.delay(0.005);
		}
	}
	
}
	
	
