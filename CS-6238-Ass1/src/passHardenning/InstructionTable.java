package passHardenning;

import java.math.BigInteger;

public class InstructionTable {
	Utilities util;
	
	public InstructionTable(){
		//TODO : find a way to point this.util to main's copy of the utilities
		
	}
	private void decrypt(String pass){
		
	}
	
	private void endrypt(String pass){
		
	}
	
	//r is unique to each user, see page 74 of Monrose section 5.1 item 1
	public BigInteger getAlpha(BigInteger r, int i, BigInteger q, String pwd){
		return calculateAlphaBeta(r, i * 2 , q, pwd);
	}
	
	//wrapper for calculateAlphaBeta() 
	public BigInteger getBeta(BigInteger r, int i, BigInteger q, String pwd){
		return calculateAlphaBeta(r, i * 2 + 1, q, pwd);
		
	}
	private BigInteger calculateAlphaBeta(BigInteger r, int input, BigInteger q, String pwd){
		BigInteger y = util.P(r, input , q); //this is y_{ai}^0 in Monrose section 5.1 item 2
		BigInteger g = util.G(pwd, r, input, q); 
		return y.add(g).mod(q);
	}
}
