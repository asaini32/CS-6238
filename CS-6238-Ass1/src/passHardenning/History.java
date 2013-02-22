package passHardenning;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
	private String keyString = "averylongtext!@$@#$#@$#*&(*&}{23432432432dsfsdf";
    private String input = "john doe";
	
	public History(){
		
	}
	
	private void decrypt(){

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
	        digest.update(keyString.getBytes());
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
	        byte[] encrypted = null;
			try {
				encrypted = cipher.doFinal(input.getBytes("UTF-8"));
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("encrypted: " + new String(encrypted));

	        // include the IV with the encrypted bytes for transport, you'll
	        // need the same IV when decrypting (it's safe to send unencrypted)

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
	        byte[] decrypted = null;
			try {
				decrypted = cipher.doFinal(encrypted);
			} catch (IllegalBlockSizeException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (BadPaddingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        try {
				System.out.println("decrypted: " + new String(decrypted, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private void checkDecryption(){
		
	}
	
	private void update(){
		try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter(FILE_NAME);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

            bufferedWriter.write("Hello there,");

            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + FILE_NAME + "'");
        }
	}
	
	private void encrypt(){
		
	}
}
