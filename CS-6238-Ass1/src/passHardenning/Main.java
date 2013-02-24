package passHardenning;

import java.util.Scanner;


public class Main {

	/**
	 * @param args
	 */
	public static final int m = 10; //number of questions/answers/distinguishing features
	public static final int h = 10; //number of records to keep in history

	public static void main(String[] args) {
		
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
	}
	
	private static void newUser() {
		Initialization init = new Initialization(); 
		init.initializeNewUser(); //fill the init object with values needed later on.
		
		
		//erase the password from memory
		init.clearPassword();
	}
	
	private static void doLogin(){
		Initialization init = new Initialization(); 
		init.initializeExistingUser(); //fill the init object with values needed later on.
		
		//Invoke Login object
		init.getLogin().doLogin();
		
		
		init.clearPassword();	
	}
	

}
