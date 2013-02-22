package passHardenning;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.security.*;

public class Utilities {
	private SecureRandom random;
	
	public Utilities(){
		try{
		random = SecureRandom.getInstance("SHA1PRNG");
		} catch(NoSuchAlgorithmException e){
			System.err.println("No such exception " + e );
			
		}
	}
	private  void generatePoly(){
		
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
	private boolean isPrime(BigInteger num){
		
		return false;
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
