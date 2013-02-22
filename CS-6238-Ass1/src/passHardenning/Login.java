package passHardenning;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.security.*;


public class Login {

	private BigInteger[] alphaBeta;
	BigInteger threshold;
	InstructionTable inst;
	
	public Login(){
		inst = new InstructionTable();
	}
	
	
	private void calculateXY(BigInteger[] featureValues){
		try{
			int count = featureValues.length;
			for(int i=0; i<count; i++)
			{	
				/*getting the alpha and beta values from the Instruction table
				*and comparing it to threshold value to get the alpha and 
				*beta values
				*/
				BigInteger value = featureValues[i];
				if((value.compareTo(threshold))<0){			    //a needs to be replaced by threshold value
					alphaBeta[i] = inst.getAlpha(i);					//a needs to be replaced by alpha value
				}
				else
				{
					alphaBeta[i] = inst.getBeta(i);				//a needs to be replaced by beta values
				}
			}
			
		}
		catch(Exception e){
			
		}
	}
	
	private void calculateHpwd(){
		
	}
	
	private void decryptHistoryFile(){
		
	}
	
	private void verifyHistoryFile(){
		
	}
	
	private void updateHistoryFile(){
		
	}
	
	private void generateInstructionsTable(String pass){
		
	}
}
