package security;

import java.io.Console;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
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
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

/*
Bouncy castle imports
 */
import org.bouncycastle.jcajce.spec.ScryptKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Scrypt_And_Encrypt {

    @Getter
    private static final ArrayList encryptedValues = new ArrayList();
    public static byte[] globalSalt = null; // Global variable for salt val
    private static String username = null;
    private static String url = null;
      private static String password = null;
    private static String res = null;

    public static ArrayList scrypt_and_encrypt(String newURL, String newUser, String newPassword) throws NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, IOException {
        url = newURL;
        username = newUser;
        password = newPassword;
        /* 
        Objects
         */
        Scanner in = new Scanner(System.in); // Scanner obj for salt.txt
        Console cons = System.console(); // Used to get copy of console
        //String password; // password input intiialization
        //byte[] salt; // salt(iv) initialization

        // Default values given by project description
        final int cost = 2048; // Iterations
        final int blockSize = 8;
        final int parallelization = 1; // Num of parallel threads to use
        final int keySize = 128;
        final int tagSize = 128;
        ScryptKeySpec scryptSpec;

        // Register bouncy castle provider.
        Security.addProvider(new BouncyCastleProvider());
//
//        /*
//        Prompt user for a password
//         */
//        if (cons != null) {
//            System.out.println("Enter a URL: ");
//            url = cons.readLine();
//            //cons.readLine(); // consume the newline character
//            System.out.println("Enter a username: ");
//            username = cons.readLine();
//            System.out.println("Do you want to randomly generate a password? (Y/N)");
//            res = cons.readLine();
//            if (res.toUpperCase().equals("Y")) {
//                char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?").toCharArray();
//                String randomStr = RandomStringUtils.random(70, 0, possibleCharacters.length - 1, false, false, possibleCharacters, new SecureRandom());
//                System.out.println(randomStr);
//                password = randomStr;
//            } else {
//                // Password doesn't not echo to console so user must input right next to print statment. (Extra security)
//                char[] passwordChars = cons.readPassword("Enter a password: ");
//                password = new String(passwordChars);
//            }
//        } else {
//            System.out.println("Enter a URL: ");
//            url = in.next();
//            System.out.println("Enter a username: ");
//            username = in.next();
//            System.out.println("Do you want to randomly generate a password? (Y/N)");
//            res = in.next();
//            if (res.toUpperCase().equals("Y")) {
//                char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?").toCharArray();
//                String randomStr = RandomStringUtils.random(70, 0, possibleCharacters.length - 1, false, false, possibleCharacters, new SecureRandom());
//                System.out.println(randomStr);
//                password = randomStr;
//            } else {
//                System.out.print("Enter a password: ");
//                password = in.next();
//                System.out.print("");
//            }
//        }

        /*
        Derive the AES key from password using the password
        Memory required to run, in bytes:
        128 * cost * blockSize * parallelization
        the password arg expects array of chars NOT bytes
         */
        scryptSpec = new ScryptKeySpec(password.toCharArray(), globalSalt, cost, blockSize, parallelization, keySize);
        // Generate the key
        SecretKey key = SecretKeyFactory.getInstance("SCRYPT").generateSecret(scryptSpec);

        // Encrypt using GCM
        Cipher aesCipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Generate the IV.
        SecureRandom rand = new SecureRandom();
        byte[] rawIv = new byte[16];		// Block size of AES.
        rand.nextBytes(rawIv);					// Fill the array with random bytes.
        GCMParameterSpec gcmParams = new GCMParameterSpec(tagSize, rawIv);

        // Encrypt mode, passes in aes(salt gen.) key + (tag size, rawIV)
        aesCipher.init(Cipher.ENCRYPT_MODE, key, gcmParams);
        String msg = password;
        byte[] ciphertext = aesCipher.doFinal(msg.getBytes(StandardCharsets.US_ASCII));

        // Output the result
        System.out.println("");
        System.out.println("Ciphertext: " + Base64.getEncoder().encodeToString(ciphertext));
        System.out.println("Key: " + Base64.getEncoder().encodeToString(key.getEncoded()));
        System.out.println("Salt: " + Base64.getEncoder().encodeToString(globalSalt));
        System.out.println("IV: " + Base64.getEncoder().encodeToString(rawIv));
        System.out.println("Tag Size: " + tagSize + " bits.");

        encryptedValues.add("URL: " + url);
        encryptedValues.add("Username: " + username);
        encryptedValues.add("Cipher text: " + Base64.getEncoder().encodeToString(ciphertext));
        encryptedValues.add("Key : " + Base64.getEncoder().encodeToString(key.getEncoded()));
        encryptedValues.add("IV: " + Base64.getEncoder().encodeToString(rawIv));
        encryptedValues.add("Salt: " + Base64.getEncoder().encodeToString(globalSalt));

        SecureKeyStore.putKey(url, key);
        System.out.println("The og url and key: " + Base64.getEncoder().encodeToString(key.getEncoded()) + " " + url);
        return encryptedValues;

    } // End 'main' method

    public static void decrypt(String ct, String key, String IV) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, AEADBadTagException {
//		String key; // The Base64 encoded key.
//		String ciphertext; // The Base64 encoded ciphertext.
//		String iv; // The initialization vector.
        
        int tagSize = 128; // 128-bit authentication tag.

        // Set up an AES cipher object.
        Cipher aesCipher = Cipher.getInstance("AES/GCM/NoPadding");

//		// Setup the input scanner.
//		Scanner input = new Scanner(System.in);
//
//		// Prompt for the ciphertext.
//		System.out.print("Please enter ciphertext: ");
//		ciphertext = input.nextLine();
//
//		// Prompt for the key.
//		System.out.print("Please enter the secret key: ");
//		key = input.nextLine();
//
//		// Prompt for the IV.
//		System.out.print("Please enter the IV: ");
//		iv = input.nextLine();
        // Setup the key.
        SecretKeySpec aesKey = new SecretKeySpec(Base64.getDecoder().decode(key),
                "AES");

        // Put the cipher in encrypt mode with the specified key.
        aesCipher.init(Cipher.DECRYPT_MODE, aesKey,
                new GCMParameterSpec(tagSize, Base64.getDecoder().decode(IV)));

        // Finalize the message.
        byte[] plaintext = aesCipher.doFinal(Base64.getDecoder().decode(ct));

        System.out.println("Original password: " + new String(plaintext));

    } // End 'decrypt' method

    
} // End 'Scrypt_And_Encrypt' class
