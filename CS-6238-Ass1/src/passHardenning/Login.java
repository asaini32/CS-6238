package passHardenning;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.security.*;


public class Login {

	private BigInteger alpha;
	private BigInteger beta;
	private BigInteger[] xValues;
	private BigInteger[] yValues;
	float threshold;
	InstructionTable inst;
	Utilities util;
	Initialization init;
	History history;
	private BigInteger candidateHpwd; 


	public Login(InstructionTable inst, Utilities util, Initialization init, History history){
		this.inst = inst;
		this.util = util;
		this.init = init;
		this.history = history;
	}
	
	
	public void calculateXY(float[] featureValues){
		try{
			int count = featureValues.length;
			for(int i=0; i<count; i++)
			{	
				/*getting the alpha and beta values from the Instruction table
				*and comparing it to threshold value to get the alpha and 
				*beta values
				*/
				float value = featureValues[i];
				if(value<threshold){			    
					alpha = inst.getAlpha(i);					
					//calculating the x and y values from the alpha and beta values
					xValues[i] = util.P(inst.getR(), 2*i, init.getQ());
					yValues[i] = alpha.subtract((util.G(init.pwd, inst.getR(), 2*i, init.getQ()))).mod(init.getQ());
				}
				else
				{
					beta = inst.getBeta(i);				//a needs to be replaced by beta values
					xValues[i] = util.P(inst.getR(), 2*i+1, init.getQ());
					yValues[i] = beta.subtract((util.G(init.pwd, inst.getR(), 2*i+1, init.getQ()))).mod(init.getQ());
				}
			}
			calculateHpwd();   //calling the function to calculate Hpwd
			
		}
		catch(Exception e){
			System.out.println("Error in calculateXY");
		}
	}
	
	//method to calculate Hpwd
	private void calculateHpwd(){
		try{
			int count = init.getM();
			for(int i=0; i<count; i++){			
				//calculated 
				this.candidateHpwd.add(yValues[i].multiply(Lamda(i)).mod(init.getQ()));
			}
			decryptHistoryFile();    //calling the method to decrypt history file
		}
		catch(Exception e){
			
		}
	}
	
	private void decryptHistoryFile(){
		history.decrypt(candidateHpwd);
	}
	
	private void verifyHistoryFile(){
		
	}
	
	private void updateHistoryFile(){
		
	}
	
	private void generateInstructionsTable(String pass){
		
	}
	
	//method to calculate lamda that is used in Hped calculation
	private BigInteger Lamda(int i){
		BigInteger lamda = null;
		try{ 
			int count = init.getM();
			for(int j =0; j<count; j++){
				if(i!=j){
					lamda.multiply(xValues[j].divide(xValues[j].subtract(xValues[i])));
				}
			}
		}
		catch(Exception e){
			
		}
		return lamda;
	}
	
	public BigInteger getCandidateHpwd() {
		return candidateHpwd;
	}
	
}
