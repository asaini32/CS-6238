package passHardenning;
import java.math.BigInteger;
import java.util.Random;

public class Initialization {
	private BigInteger q;
	Utilities util;
	
	public Initialization(){
		System.out.println("In constructor");
	}
	 
	//this is when it's the initialization for the first time
	public void doFirstInit(Utilities util){
		System.out.println("initialization class will take over.");
		this.util = util;
		this.chooseHPwd();
		
	}
	
	//this is when initializing from an existing file
	public void doInit(){
		
	}
	
	
	private void chooseHPwd(){
		q = util.getRandomQ();
	}
	
	private void chooseH(){
		
	}
	
	private void generateInstructionTable(){
		
	}
	
	private void generateHistoryFile(){
		
	}
	public BigInteger getQ(){
		return q;
		
	}
}
