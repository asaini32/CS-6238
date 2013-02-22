package passHardenning;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.security.*;


public class Login {

	private BigInteger alpha;
	private BigInteger beta;
	private BigInteger[] xValues;
	private BigInteger[] yValues;
	BigInteger threshold;
	InstructionTable inst;
	Utilities util;
	
	public Login(InstructionTable inst, Utilities util){
		this.inst = inst;
		this.util = util;
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
					alpha = inst.getAlpha(i);					//a needs to be replaced by alpha value
					xValues[i] = util.P(inst.getR(), 2*i, init.getQ);
				}
				else
				{
					beta = inst.getBeta(i);				//a needs to be replaced by beta values
					xValues[i] = util.P(inst.getR(), 2*i+1, init.getQ);
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
