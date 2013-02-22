package passHardenning;
import java.math.BigInteger;
import java.util.Random;

public class Initialization {

	
	Utilities util;
	private BigInteger q;
	private BigInteger hpwd;
	private int h;
	private BigInteger[] polynomial;
	private int m;
	
	
	
	public Initialization(){
		System.out.println("In constructor");
	}
	 
	//this is when it's the initialization for the first time
	public void doFirstInit(Utilities util){
		System.out.println("initialization class will take over.");
		this.util = util;
		this.chooseQ();
		this.chooseHPwd();
		this.chooseH();
		this.chooseM();
		this.choosePolynomial();
	}
	
	//this is when initializing from an existing file
	public void doInit(){
		
	}
	private void chooseM(){
		m = 10;
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
	
	private void chooseH(){
		h = 10; //set by ourselves
	}
	
	private void generateInstructionTable(){
		
	}
	
	private void generateHistoryFile(){
		
	}
	public BigInteger getQ(){
		return q;
		
	}
	public BigInteger getHpwd(){
		return hpwd;
	}
	public int getH(){
		return h;
	}
}
