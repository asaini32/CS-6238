package passHardenning;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.security.*;

public class Utilities {
	private SecureRandom random;
	private MessageDigest md;
	
	public Utilities(){
		try{
		random = SecureRandom.getInstance("SHA1PRNG");
		md = MessageDigest.getInstance("SHA-1");
		} catch(NoSuchAlgorithmException e){
			System.err.println("No such exception " + e );
			
		}
	}
	
	//generate a poly of degree m-1 with constant term = hpwd
	public BigInteger[] generatePoly(int m, BigInteger hpwd, BigInteger q){
		BigInteger[] coeffs = new BigInteger[m];
		
		coeffs[0] = hpwd; //the constant term is hpwd
		
		for(int i = 1; i < m; i++){
			coeffs[i] = getRandomH(q); //getRandomH method doubles up as get random element \in \Z_q
		}
		return coeffs;
	}
	
	public BigInteger getRandomQ(){
	    BigInteger candidateQ;
		do{
	    	byte bytes[] = new byte[20];
	    	random.nextBytes(bytes);
	    
	    	candidateQ = new BigInteger(bytes);
	    }
		while(isPrime(candidateQ) == false);
		
		return candidateQ;
	}
	
	public BigInteger getRandomH(BigInteger q){
		BigInteger candidateH;
		
		//find a random H that is less than Q
		do{
			byte bytes[] = new byte[20];
			random.nextBytes(bytes);
    
			candidateH = new BigInteger(bytes);
		} 
		while(candidateH.compareTo(q) != -1);
		return candidateH;
	}
	
	private boolean isPrime(BigInteger num){
		//Perform Miller Rabin's test
		
		// TODO 
		return true;
	}
	
	//implementation of G_pwd() "PRF" to use to calculate alpha and beta
	public BigInteger G(String pwd, BigInteger r, BigInteger input, BigInteger q){
		//try just a concatenation of key at the back (prevent length extension?)
		byte[] pwdB = pwd.getBytes();
		byte[] rB = r.toByteArray();
		byte[] inputB = input.toByteArray();
		
		//Concatenate all inputs into 1 long byte array
		int totalInputLen = pwdB.length + rB.length + inputB.length;
		byte[] totalInput = new byte[totalInputLen];
		System.arraycopy(inputB, 0, totalInput, 0,                       inputB.length);
		System.arraycopy(rB,     0, totalInput, inputB.length,           rB.length);
		System.arraycopy(pwdB,   0, totalInput, inputB.length+rB.length, pwdB.length);
		
		
		byte[] digest = md.digest(totalInput);
		return new BigInteger(digest).mod(q);
	}
	
	private void secretShare(){
		
	}
	
	private void OAEP_padding(){
		
	}
	
	private void ecrypt(){
		
	}
	
	private void getPolynomial(){
		
	}
}
