package passHardenning;
import java.math.BigInteger;
import java.util.Random;


//Main holds a (singleton) Initialization object
//The Initialization object stores system wide parameters
//like q, m, the polynomial, the hpwd
public class Initialization {

	
	Utilities util;
	public Utilities getUtil() {
		return util;
	}

	public int getM() {
		return m;
	}

	public BigInteger[] getPolynomial() {
		return polynomial;
	}

	public String getPwd() {
		return pwd;
	}
	private BigInteger q; // the 160 bit prime number
	private int m; // 
	private BigInteger hpwd;
	private int h;
	private BigInteger[] polynomial;
	String pwd; //normal, unhardened password
	
	
	
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
