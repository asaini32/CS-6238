package passHardenning;
import java.math.BigInteger;


public class Login {

	private BigInteger alpha;
	private BigInteger beta;
	private BigInteger[] xValues;
	private BigInteger[] yValues;
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
		this.xValues = new BigInteger[init.getM()];
		this.yValues = new BigInteger[init.getM()];
	}

	//assumes threshold, feature values, pwd are already read. 
	public void doLogin(){	
		this.calculateXY(init.answers); 					 //pass feature value
		this.calculateHpwd();   							//calling the function to calculate Hpwd
		System.out.println("hpwd is " + candidateHpwd);
		this.init.hpwd = candidateHpwd;
		this.decryptHistoryFile();							//decrypting the history file.
		boolean status = this.verifyHistoryFile();			//verifying the history file.
		if(status)
		{
			System.out.println("Login Successful");
		}
		
		this.updateHistoryFile();							//updating the history file.
		
		if(history.isFull()) {
			System.out.println("File is full, updating mean and standard deviation");
			inst.updateMean(history.getHistoryFile());
			inst.calculateStd_Dev(history.getHistoryFile());
		}
		init.randomizeInstructionTable();
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
				if(value<inst.threshold[i]){			    
					alpha = inst.getAlpha(i);					
					//calculating the x and y values from the alpha and beta values
					xValues[i] = util.P(inst.getR(), 2*i, inst.q);
					yValues[i] = alpha.subtract((util.G(init.getPwd(), inst.getR(), 2*i, inst.q))).mod(inst.q);
				}
				else
				{
					beta = inst.getBeta(i);				//a needs to be replaced by beta values

					xValues[i] = util.P(inst.getR(), 2*i+1, inst.q);
					yValues[i] = beta.subtract((util.G(init.getPwd(), inst.getR(), 2*i+1, inst.q))).mod(inst.q);
				}
				//System.out.println("x[" + i + "] is " + xValues[i]);
				//System.out.println("y[" + i + "] is " + yValues[i]);
			}

		}
		catch(Exception e){
			System.out.println("Error in calculateXY " + e);
			e.printStackTrace();
		}
	}

	//method to calculate Hpwd
	private void calculateHpwd(){
		try{
			int count = init.getM();
			this.candidateHpwd = new BigInteger("0");
			for(int i=0; i<count; i++){			
				//calculated the hardened password from the 
				//values in the alpha beta instruction table
				candidateHpwd = this.candidateHpwd.add(yValues[i].multiply(Lamda(i)).mod(inst.q));
			}
			candidateHpwd = candidateHpwd.mod(inst.q);
			//System.out.println("Candidate Hpwd: " + candidateHpwd);
			//System.out.println("q is " + inst.q);
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
		BigInteger lamda = new BigInteger("1");
		try{ 
			int count = init.getM();
			for(int j =0; j<count; j++){
				if(i!=j){
					BigInteger int1 = (xValues[j].subtract(xValues[i])).mod(inst.q);
					BigInteger int2 = int1.modInverse(inst.q);
					BigInteger int3 = (xValues[j].multiply(int2)).mod(inst.q);
					lamda = (lamda.multiply(int3)).mod(inst.q);
//					lamda = lamda.multiply(xValues[j].multiply((xValues[j].subtract(xValues[i]).mod(inst.q)).modInverse(inst.q)));
				}
			}
			//System.out.println("The value of lamda:  "+ lamda);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return lamda;
	}

	//getter for candidate password
	public BigInteger getCandidateHpwd() {
		return candidateHpwd;
	}

}
