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
    
     public static char[] charPwd = new char[] {'p','a','s','s','w','o','r','d'};

    public static SecretKey scrypt(char[]authentificationPass , byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, IOException {
        char[] authPass = authentificationPass;
        
        /* 
        Objects
         */


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
        Derive the AES key from password using the password
        Memory required to run, in bytes:
        128 * cost * blockSize * parallelization
        the password arg expects array of chars NOT bytes
         */
        scryptSpec = new ScryptKeySpec(authPass, globalSalt, cost, blockSize, parallelization, keySize);
        // Generate the key
        SecretKey key = SecretKeyFactory.getInstance("SCRYPT").generateSecret(scryptSpec);
    return key;
    
    }
    
    public static ArrayList encrypt(String newURL, String newUser, String newPassword) throws NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, IOException {
        
            url = newURL;
       username = newUser;
       
       password = newPassword;
       
        SecretKey key = scrypt(charPwd,globalSalt);
       
        // Encrypt using GCM
        Cipher aesCipher = Cipher.getInstance("AES/GCM/NoPadding");

       
        
        // Generate the IV.
        SecureRandom rand = new SecureRandom();
        byte[] rawIv = new byte[16];		// Block size of AES.
        rand.nextBytes(rawIv);					// Fill the array with random bytes.
        GCMParameterSpec gcmParams = new GCMParameterSpec(128, rawIv);

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
        System.out.println("Tag Size: " + 128 + " bits.");

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

    public static void decrypt(String ct, String IV) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, AEADBadTagException, InvalidKeySpecException, IOException {
//		String key; // The Base64 encoded key.
//		String ciphertext; // The Base64 encoded ciphertext.
//		String iv; // The initialization vector.
        
        int tagSize = 128; // 128-bit authentication tag.
        SecretKey key= scrypt(charPwd,globalSalt);
        byte[] keyBytes = key.getEncoded();

        // Set up an AES cipher object.
        Cipher aesCipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Setup the key.
        SecretKeySpec aesKey = new SecretKeySpec(keyBytes,
                "AES");

        // Put the cipher in encrypt mode with the specified key.
        aesCipher.init(Cipher.DECRYPT_MODE, aesKey,
                new GCMParameterSpec(tagSize, Base64.getDecoder().decode(IV)));

        // Finalize the message.
        byte[] plaintext = aesCipher.doFinal(Base64.getDecoder().decode(ct));

        System.out.println("Original password: " + new String(plaintext));

    } // End 'decrypt' method

    public static ArrayList getEncryptedValues() {
        return encryptedValues;
    }

    
} // End 'Scrypt_And_Encrypt' class
