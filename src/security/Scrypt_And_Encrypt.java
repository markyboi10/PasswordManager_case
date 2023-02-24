package security;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/*
Bouncy castle imports
 */
import org.bouncycastle.jcajce.spec.ScryptKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Scrypt_And_Encrypt {

    // Name of file where salt(iv) is stored
    private static final String SALT_FILE = "C:\\Users\\Mark Case\\Documents\\NetBeansProjects\\PasswordManager\\test\\salt.txt";
    private static ArrayList encryptedValues = new ArrayList();
    public static String c;
    public static String k;
    public static String i;
    public static String s;
    // Test main
    public static ArrayList scrypt_and_encrypt() throws NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, IOException {

        /* 
        Objects
         */
        Scanner in = new Scanner(System.in); // Scanner obj for salt.txt
        Console cons = System.console(); // Used to get copy of console
        String password; // password input intiialization
        byte[] salt; // salt(iv) initialization

        // Default values given by project description
        final int cost = 2048; // Iterations
        final int blockSize = 8;
        final int parallelization = 1; // Num of parallel threads to use
        final int keySize = 128;
        final int tagSize = 128;
        ScryptKeySpec scryptSpec;

        // Register bouncy castle provider.
        Security.addProvider(new BouncyCastleProvider());

        /*
        Prompt user for a password
         */
        if (cons != null) {
            password = new String(cons.readPassword("Enter a password: "));
        } else {
            System.out.print("Enter a password: ");
            password = in.next();
        } // End if

        // Load the salt(iv) if the file exits
        File saltFile = new File(SALT_FILE);
        if (saltFile.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(saltFile));
            String saltString = reader.readLine();
            salt = Base64.getDecoder().decode(saltString);
        } else { // If it doesn't exist, create one and populate salt.txt
            salt = new byte[16]; // 16 byte IV for an AES key
            SecureRandom rand = new SecureRandom();
            rand.nextBytes(salt);

            try ( FileOutputStream outStream = new FileOutputStream(saltFile)) {
                outStream.write(Base64.getEncoder().encodeToString(salt).getBytes()); // Write out as encoded string
            } // End try

        } // End if

        /*
        Derive the AES key from password using the password
        Memory required to run, in bytes:
        128 * cost * blockSize * parallelization
        the password arg expects array of chars NOT bytes
         */
        scryptSpec = new ScryptKeySpec(password.toCharArray(), salt, cost, blockSize, parallelization, keySize);
        // Generate the key
        SecretKey key = SecretKeyFactory.getInstance("SCRYPT").generateSecret(scryptSpec);

        // Encrypt using GCM
        Cipher aesCipher = Cipher.getInstance("AES/GCM/NoPadding");
        
        
        // Generate the IV.
		SecureRandom rand = new SecureRandom();
		byte[] rawIv = new byte[16];		// Block size of AES.
		rand.nextBytes(rawIv);					// Fill the array with random bytes.
		GCMParameterSpec gcmParams = new GCMParameterSpec(tagSize, rawIv);
        
        
        
        aesCipher.init(Cipher.ENCRYPT_MODE, key, gcmParams);
        String msg = password;
        byte[] ciphertext = aesCipher.doFinal(msg.getBytes(StandardCharsets.US_ASCII));

        // Output the result
        System.out.println("Ciphertext: " + Base64.getEncoder().encodeToString(ciphertext));
        System.out.println("Key: " + Base64.getEncoder().encodeToString(key.getEncoded()));
        System.out.println("Salt: " + Base64.getEncoder().encodeToString(salt));
        System.out.println("IV: " + Base64.getEncoder().encodeToString(rawIv));
        System.out.println("Tag Size: " + tagSize + " bits.");
        
        encryptedValues.add("Cipher text: " + Base64.getEncoder().encodeToString(ciphertext));
        encryptedValues.add("Key : " + Base64.getEncoder().encodeToString(key.getEncoded()));
        encryptedValues.add("IV: " + Base64.getEncoder().encodeToString(rawIv));
        
        c = Base64.getEncoder().encodeToString(ciphertext); // ciphertext
        k = Base64.getEncoder().encodeToString(key.getEncoded()); // aes key
        i = Base64.getEncoder().encodeToString(rawIv); // raw iv
        s = Base64.getEncoder().encodeToString(salt); //  salt
        
        return encryptedValues;

    } // End 'main' method


    public static ArrayList getEncryptedValues() {
        return encryptedValues;
    }

    
    

	public static void decrypt() throws
	    NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
      IllegalBlockSizeException, BadPaddingException,
			InvalidAlgorithmParameterException, AEADBadTagException
	{
		String key; // The Base64 encoded key.
		String ciphertext; // The Base64 encoded ciphertext.
		String iv;	// The initialization vector.
		int tagSize = 128;		// 128-bit authentication tag.

		// Set up an AES cipher object.
		Cipher aesCipher = Cipher.getInstance("AES/GCM/NoPadding");

		// Setup the input scanner.
		Scanner input = new Scanner(System.in);

		// Prompt for the ciphertext.
		System.out.print("Please enter ciphertext: ");
		ciphertext = input.nextLine();

		// Prompt for the key.
		System.out.print("Please enter the secret key: ");
		key = input.nextLine();

		// Prompt for the IV.
		System.out.print("Please enter the IV: ");
		iv = input.nextLine();

		// Setup the key.
		SecretKeySpec aesKey = new SecretKeySpec(Base64.getDecoder().decode(key),
		    "AES");

		// Put the cipher in encrypt mode with the specified key.
		aesCipher.init(Cipher.DECRYPT_MODE, aesKey,
		    new GCMParameterSpec(tagSize, Base64.getDecoder().decode(iv)));

		// Finalize the message.
		byte[] plaintext = aesCipher.doFinal(Base64.getDecoder().decode(ciphertext));

    System.out.println("Plaintext: " + new String(plaintext));

	}


    
} // End 'Scrypt_And_Encrypt' class
