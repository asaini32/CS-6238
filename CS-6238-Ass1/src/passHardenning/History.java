package passHardenning;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class History {

	final static String FILE_NAME = "history.txt";
	private Initialization init;
	private byte[] encryptedTextWriting = null;
	private byte[] encryptedTextReading = null;
	private byte[] decryptedText = null;
	private String historyFile;
	private String getHistoryFile;

	public History(Initialization init){
		this.init = init;
	}

	//decryption method for history file
	public void decrypt(BigInteger candidateHpwd){

		try {
			deserializeObejct(FILE_NAME);    //deserializing the history file
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// setup AES cipher in CBC mode with PKCS #5 padding
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// setup an IV (initialization vector) that should be
		// randomly generated for each input that's encrypted
		byte[] iv = new byte[cipher.getBlockSize()];
		new SecureRandom().nextBytes(iv);
		IvParameterSpec ivSpec = new IvParameterSpec(iv);

		// hash keyString with SHA-256 and crop the output to 128-bit for key
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		digest.update(candidateHpwd.toByteArray());         //check if this is going to work (keystring.tobytes())
		byte[] key = new byte[16];
		System.arraycopy(digest.digest(), 0, key, 0, key.length);
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

		// decrypt
		try {
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//	        byte[] decrypted = null;
		try {
			decryptedText = cipher.doFinal(encryptedTextReading);
		} catch (IllegalBlockSizeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BadPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}try {
			getHistoryFile = new String(decryptedText, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//this method deserializes the history file
	private void deserializeObejct(String fileName)throws IOException{
		File file = new File(FILE_NAME);
		try{
			ObjectInputStream obj = new ObjectInputStream(new FileInputStream(file));
			encryptedTextReading = (byte[]) obj.readObject(); 
			obj.close();
		}
		catch(ClassNotFoundException e){
			System.out.println(e.getMessage());
		}
		catch(FileNotFoundException fe){
			System.out.println("File not found ");
		}

	}

	//verifies the history file
	public boolean checkDecryption(){
		try{
			if(getHistoryFile.substring(0,7).equals("Feature"))
				return true;
			else
				return false;
		}
		catch(Exception e){

		}
		return false;
	}

	//makes the new history file
	public void update(){
		try {
			BuildHistoryFile();
			encrypt();
			serializeObejct(FILE_NAME);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	//builds the history file for a particular user
	private void BuildHistoryFile(){
		try{
			StringBuilder strBuilder = new StringBuilder();
			int count = init.getM();
			for(int i=0; i<count; i++){
				strBuilder.append("Feature");
				strBuilder.append(i);
				strBuilder.append(":");
				strBuilder.append("featureVectorvalue");				//this needs to be upadted with the feature value.
				historyFile = strBuilder.toString();
			}
		}
		catch(Exception e){

		}

	}

	//method to encrypt the history file
	private void encrypt(){
		BigInteger keyString = init.getHpwd();

		// setup AES cipher in CBC mode with PKCS #5 padding
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// setup an IV (initialization vector) that should be
		// randomly generated for each input that's encrypted
		byte[] iv = new byte[cipher.getBlockSize()];
		new SecureRandom().nextBytes(iv);
		IvParameterSpec ivSpec = new IvParameterSpec(iv);

		// hash keyString with SHA-256 and crop the output to 128-bit for key
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		digest.update(keyString.toByteArray());
		byte[] key = new byte[16];
		System.arraycopy(digest.digest(), 0, key, 0, key.length);
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

		// encrypt
		try {
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		byte[] encrypted = null;
		try {
			encryptedTextWriting = cipher.doFinal(historyFile.getBytes("UTF-8"));
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//this method serializes the history file
	private void serializeObejct(String fileName)throws IOException{
		File file = new File(FILE_NAME);
		try{

			ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(file));
			obj.writeObject(encryptedTextWriting); 
			obj.close();
		}
		catch(FileNotFoundException fe){

			System.out.println("File not found ");

		}

	}
}
