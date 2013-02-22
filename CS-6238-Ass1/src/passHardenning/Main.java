package passHardenning;

public class Main {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Utilities util = new Utilities();
		Initialization init = new Initialization(); //contains all the system parameters like q
		History history = new History(init);
		
		//Check if user decided to initialize the whole system from scratch
		if(args.length > 0){
			if(args[0].equalsIgnoreCase("init")){
				doInit(init, util);
				return;	
			}
			
		} 
		//This case is when a system is already initialized, so 
		//read the q, h file, m, etc from an existing file instead
		else {
			

		}

		
	}
	
	private static void doInit(Initialization init, Utilities util){
		System.out.println("In init");
		init.doFirstInit(util);
		
	}
}
