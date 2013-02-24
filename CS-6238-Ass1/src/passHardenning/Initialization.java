package passHardenning;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;


//This is a (big) entity class 
//Main holds a (singleton) Initialization object
//The Initialization object stores system wide parameters
//like q, m, the polynomial, the hpwd, etc
//Main passes the init object around to whoever needs the information
public class Initialization {
	private static final String qString[] = 
		{ "1. How long ...", "2. How many ...",
		"3. How much ...", "4. How many ...", "5. How many ...",
		"6. How many ...", "7. How many ...", "8. How many ...",
		"9. How many ...", "10. How many ...", };
	
	private Utilities util;
	private String userName;
	int[] answers; // raw feature values (answers to questions)
	private char[] pwd; //normal, unhardened password
	private BigInteger hpwd; //hardened password
	
	private BigInteger q; // the 160 bit prime number that's modulus group
	private BigInteger[] polynomial;

	private final int m;  // how many questions/feature answers
	private final int h;  // how mBigIntegerany records to keep in history file

	private History history;
	private InstructionTable inst;
	private Login login;

	//This constructor initializes variables common to both 
	//the NewUser and ExistingUser use cases.
	public Initialization(){
		util = new Utilities();
		m = Main.m;
		h = Main.h;
		userName = readUserName();
		pwd = readPwd();
		answers = askQuestions();
		inst = new InstructionTable(this);
		history = new History(this);
	}

	public void initializeNewUser(){
		//Generate files for this new user...
		generateInstructionTable();		
		System.out.println("hpwd is " + hpwd);
		generateHistoryFile();
		
	} 
	public void initializeExistingUser(){
		inst.readInstrTable(); // read the alpha and beta values
		
		login = new Login(inst, util, this, history);
		login.doLogin();
	}
	
	public void clearPassword(){
		java.util.Arrays.fill(pwd, ' ');
		hpwd = null; // not really overwriting since BigIntegers are immutable
	}
	
	//this is when initializing from an existing file
	public void doInit(){
		
	}
	public void askUserForPassword(){
		//Use System.in or scanner class to get user's password and set to this.pwd
	}
	
	private void choosePolynomial(){
		polynomial = util.generatePoly(m, hpwd, q);
		
	}
	private void chooseQ(){
		//q is the prime 
		q = util.getRandomQ();
	}
	
	private void chooseHPwd(){
		//hpwd is the hardened password
		hpwd = util.getRandomH(q);
	}
	
	//This is run when a new user is created
	//Or else the instruction table is usually
	//read from a file instead. 
	private void generateInstructionTable(){
		//Choose the system parameters for the 1st time
		chooseQ();
		chooseHPwd();
		System.out.println("immediately hpwd is " + hpwd + " and compare is " + hpwd.compareTo(BigInteger.ZERO) );
		
		choosePolynomial();
		
		//Calculate the alpha and beta values
		inst.buildInstrTable(); //calculate the alpha and beta values
		inst.writeInstrTable(); //write it to disk for future logins to use
	}
	
	//This is run to create a new history file
	//for a new user. Or else the history is usually
	//read from a file instead.
	private void generateHistoryFile(){
		history.update(); //create new history file and encrypt and write it to disk.
	}
	
	
	private char[] readPwd(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Password: ");
		return sc.next().toCharArray();
		//return System.console().readPassword("Password: ");
	}
	
	//Prompt for and return user name
	private String readUserName(){
		Scanner sc  = new Scanner(System.in); 
		String name;
		while(true){
			System.out.println("Pick a 1 word (alphanumeric and underscore) username: ");
			name = sc.nextLine();
			if(name.matches("^\\w+$")){ 
				System.out.println("Name of " + name + " accepted.");
				return name;
			}
			System.out.println("Invalid username, please try again.");
		}
	}
	
	//Ask the log in questions and return all the answers in 1 array
	private int[] askQuestions(){
		Question[] questions = new Question[Main.m];
		initializeQuestions(questions);
		return askQuestions(questions);
	}

	//Set the questions here. Make sure they all take in integer replies
	private void initializeQuestions(Question[] questions) {
		
		for (int i = 0; i < questions.length; i++) {
			questions[i] = new Question(qString[i]);
		}
	}
	
	private int[] askQuestions(Question[] questions) {
		int[] answers = new int[questions.length];
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < answers.length; i++) {
			answers[i] = questions[i].ask(sc);
		}
		return answers;
	}	
	
	//Getters
	public BigInteger getQ(){
		return q;
		
	}
	public BigInteger getHpwd(){
		return hpwd;
	}
	public int getH(){
		return h;
	}
	public Utilities getUtil() {
		return util;
	}

	public int getM() {
		return m;
	}

	public BigInteger[] getPolynomial() {
		return polynomial;
	}

	public char[] getPwd() {
		return pwd;
	}


	public static String[] getQstring() {
		return qString;
	}


	public String getUserName() {
		return userName;
	}


	public int[] getAnswers() {
		return answers;
	}


	public History getHistory() {
		return history;
	}


	public InstructionTable getInst() {
		return inst;
	}


	public Login getLogin() {
		return login;
	}
}
