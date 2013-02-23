package passHardenning;
import java.math.BigInteger;


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

	public void doLogin(){	
		this.calculateXY(init.answers); 					 //pass feature value
		this.calculateHpwd();   							//calling the function to calculate Hpwd
		this.decryptHistoryFile();							//decrypting the history file.
		boolean status = this.verifyHistoryFile();			//verifying the history file.
		if(status)
		{
			System.out.println("Login Successful");
		}
		this.updateHistoryFile();							//updating the history file.
	}

	private void calculateXY(int[] featureValues){
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
					yValues[i] = alpha.subtract((util.G(init.getPwd(), inst.getR(), 2*i, init.getQ()))).mod(init.getQ());
				}
				else
				{
					beta = inst.getBeta(i);				//a needs to be replaced by beta values
					xValues[i] = util.P(inst.getR(), 2*i+1, init.getQ());
					yValues[i] = beta.subtract((util.G(init.getPwd(), inst.getR(), 2*i+1, init.getQ()))).mod(init.getQ());
				}
			}

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
				//calculated the hardened password from the 
				//values in the alpha beta instruction table
				this.candidateHpwd.add(yValues[i].multiply(Lamda(i)).mod(init.getQ()));
			}
			decryptHistoryFile();    //calling the method to decrypt history file
		}
		catch(Exception e){

		}
	}

	//calls the decrypt method in the history file
	private void decryptHistoryFile(){
		history.decrypt(candidateHpwd);
	}

	//verifies the decrypted file
	private boolean verifyHistoryFile(){
		return history.checkDecryption();
	}

	//updated the history file
	//calls a method in the history class.
	private void updateHistoryFile(){
		history.update();
	}

	private void generateInstructionsTable(String pass){
		//TODO: the instruction table needs to be build
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

	//getter for candidate password
	public BigInteger getCandidateHpwd() {
		return candidateHpwd;
	}

}
