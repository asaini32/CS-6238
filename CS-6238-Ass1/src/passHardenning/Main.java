package passHardenning;

import java.util.Scanner;

public class Main {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Utilities util = new Utilities();
		Initialization init = new Initialization(); //contains all the system parameters like q
		History history = new History(init);
		InstructionTable inst = new InstructionTable(init);
		Login login = new Login(inst, util, init, history);
		
		
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
		
		//Ask the log in questions
		Question[] questions = new Question[init.getM()];
		initializeQuestions(questions);
		int[] answers = askQuestions(questions);
		
		
		
	}
	
	private static void initializeQuestions(Question[] questions) {
		// TODO Auto-generated method stub
		String qString[] = {"1. How long ...", 
				            "2. How many ...",
				            "3. How much ...",
				            "4. How many ...",
				            "5. How many ...",
				            "6. How many ...",
				            "7. How many ...",
				            "8. How many ...",
				            "9. How many ...",
				            "10. How many ...",};
		
		for(int i = 0; i < questions.length; i++){
			questions[i] = new Question(qString[i]);
		}
	}

	private static void doInit(Initialization init, Utilities util){
		System.out.println("In init");
		init.doFirstInit(util);
		
	}
	private static int[] askQuestions(Question[] questions){
		int[] answers = new int[questions.length];
		Scanner sc = new Scanner(System.in);
		for(int i = 0; i < answers.length; i++){
			answers[i] = questions[i].ask(sc);	
		}
		return answers;
	}
}
