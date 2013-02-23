package passHardenning;
import java.util.Scanner;

public class Question {
	String question;
	
	public Question(String q){
		question = q;
	}
	
	public int ask(Scanner sc){
		while(true){
			try{	
				System.out.println(question);
				return sc.nextInt();
			} catch (Exception e){ System.err.println("Error reading input, please try again.");}
		}
	}
}
