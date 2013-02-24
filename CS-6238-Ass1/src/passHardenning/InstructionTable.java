package passHardenning;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;

public class InstructionTable {
	Utilities util;
	BigInteger[] polynomial; 
	BigInteger q; //the modulo group
	BigInteger[] alpha;
	BigInteger[] beta;
	char[] pwd;
	BigInteger hpwd; //hardened password
	int m;
	BigInteger r;
	double[] threshold;
	Initialization init;
	private String FILE_NAME = "_inst.txt";

	public BigInteger getR(){
		return r;
	}
	public InstructionTable(Initialization init){
		this.init = init;
		this.m = init.getM();
		this.util = init.getUtil();
		this.pwd = init.getPwd();

		alpha = new BigInteger[m];
		beta = new BigInteger[m];
		threshold = new double[m];		
	}

	//this is run once for a new user or when existing user 
	//needs to change his polynomial
	public void buildInstrTable(){
		
		q = init.getQ();
		r = util.getRandomH(q);
		hpwd = init.getHpwd();
		polynomial = init.getPolynomial();

		for(int i = 0; i < m; i++){
			alpha[i] = calculateAlpha(r, i, pwd);
			beta[i] = calculateBeta(r, i, pwd);
			//Threshold can be arbitrary for the 1st h-1 times
			//Threshold is only calculated if there has been h logins
			threshold[i] = 0.0;
		}		
	}


	public void readInstrTable(){
		//get userName from this.init.userName
		//open the file with filename of userName + "_inst.txt"
		//read q from file
		//read r from file
		//for i = 0 to m
		//read obj = alpha[i] from file
		//read obj = beta[i] from file
		//read obj/float = threshold[i] from file (not sure if you can write float directly)
		//close file

		File file = new File(FILE_NAME);
		try{
			ObjectInputStream obj;

			obj = new ObjectInputStream(new FileInputStream(file));
			q = (BigInteger) obj.readObject(); 
			r = (BigInteger) obj.readObject();
			int count = init.getM();
			for(int i=0; i<count; i++){
				alpha[i] = (BigInteger) obj.readObject();
				beta[i] = (BigInteger) obj.readObject();
				threshold[i] = (Double) obj.readObject();
			}
			obj.close();
		}
		catch(ClassNotFoundException e){
			System.out.println(e.getMessage());
		}
		catch(FileNotFoundException fe){
			System.out.println("File not found ");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeInstrTable(){
		//get user name from this.init.userName
		//file name is userName + "_inst.txt"
		//write q to file
		//write r to file
		//for i = 0 to m
		//write obj = alpha[i] to file
		//write obj = beta[i] to file
		//write obj/float = threshold[i] to file
		//close file

		// The name of the file to open.

		File file = new File(FILE_NAME);
		try{

			ObjectOutputStream obj;

			obj = new ObjectOutputStream(new FileOutputStream(file));
			obj.writeObject(q);
			obj.writeObject(r);

			int count = init.getM();
			for(int i=0; i<count; i++){
				obj.writeObject(alpha[i]); 
				obj.writeObject(beta[i]);
				obj.writeObject(threshold[i]);
			}

			obj.close();
		}
		catch(FileNotFoundException fe){

			System.out.println("File not found ");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
