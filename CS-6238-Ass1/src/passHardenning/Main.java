package passHardenning;

import java.util.Scanner;


public class Main {

	/**
	 * @param args
	 */
	public static final int m = 10; //number of questions/answers/distinguishing features
	public static final int h = 2; //number of records to keep in history
	public static final int t = 10; //threshold value for each feature (for simplicity assume same t for all features)
	public static final double k = 1.0; // system parameter 
	
	public static void main(String[] args) {
		try{
		// New user or first time running
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("init")) {
				newUser();
			}
			else{ System.err.println("Invalid argument " + args[0]);}
		}
		// Normal operation, immediately perform log-in steps
		else {
				doLogin();
		}
		} catch (Exception e) {System.out.println("Exception occured. Aborting.");}
	}
	
	private static void newUser() {
		Initialization init = new Initialization(); 
		init.initializeNewUser(); //fill the init object with values needed later on.
		
		System.out.println("Your account has been created.");
		//erase the password from memory
		init.clearPassword();
		
	}
	
	private static void doLogin(){
		Initialization init = new Initialization(); 
		init.initializeExistingUser(); //fill the init object with values needed later on.		
		
		init.clearPassword();	
	}
	

}
