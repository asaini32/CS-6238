package passHardenning;

import java.math.BigInteger;

public class InstructionTable {
	Utilities util;
	BigInteger[] polynomial; 
	BigInteger q;
	BigInteger[] alpha;
	BigInteger[] beta;
	int m;
	BigInteger r;
	float[] threshold;
	
	public BigInteger getR(){
		return r;
	}
	public InstructionTable(Initialization init){
		//TODO : find a way to point this.util to main's copy of the utilities main.util
		//  find a way to point this.polynomial to main's copy in main.init.polynomial
		// also for this.q to point to main.init.q
		// also for this.m to copy from main.init.m 
		this.m = init.getM();
		this.q = init.getQ();
		this.util = init.getUtil();
		this.polynomial = init.getPolynomial();
		threshold = new float[m];
		r = util.getRandomH(q);
		alpha = new BigInteger[m];
		beta = new BigInteger[m];
		
	}
	private void readFromDisk(String pass){
		
	}
	
	private void writeToDisk(String pass){
		
	}
	public void buildInstrTable(BigInteger r, char[] pwd){
		for(int i = 0; i < m; i++){
			alpha[i] = calculateAlpha(r, i, pwd);
			beta[i] = calculateBeta(r, i, pwd);
		}
	}
	
	public BigInteger getAlpha(int i){
		return alpha[i];		
	}
	public BigInteger getBeta(int i){
		return beta[i];
	}
	
	//r is unique to each user, see page 74 of Monrose section 5.1 item 1
	private BigInteger calculateAlpha(BigInteger r, int i, char[] pwd){
		return calculateAlphaBeta(r, i * 2 , pwd);
	}
	
	//wrapper for calculateAlphaBeta() 
	private BigInteger calculateBeta(BigInteger r, int i, char[] pwd){
		return calculateAlphaBeta(r, i * 2 + 1, pwd);
		
	}
	private BigInteger calculateAlphaBeta(BigInteger r, int input, char[] pwd){
		BigInteger randomizedX = util.P(r, input , q);
		BigInteger y = util.evaluatePoly(polynomial, r, randomizedX); // this is y_{ai}^0/1 in Monrose section 5.1 item 2
		BigInteger g = util.G(pwd, r, input, q); 
		return y.add(g).mod(q);
	}
}
