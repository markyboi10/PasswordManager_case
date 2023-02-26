package security;

import static Driver.Main.vaultManager;
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

public class Scrypt_Encrypt_Decrypt {

    private static final ArrayList encryptedValues = new ArrayList();
    public static byte[] globalSalt = null; // Global variable for salt val
    // Char array of authentification password, should be set by user at start of app.
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
    
    } // Enm 'scrypt' method
    
    public static ArrayList encrypt(String newURL, String newUser, char[] newPassword) throws NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, IOException {
        
        // Call scrypt function
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
        System.out.println(newPassword);
        char[] msg = newPassword;
        // msg = new String(newPassword);
        
        byte[] passwordBytes = new String(msg).getBytes(StandardCharsets.US_ASCII);
byte[] ciphertext = aesCipher.doFinal(passwordBytes);
       // byte[] ciphertext = aesCipher.doFinal(msg.getBytes(StandardCharsets.US_ASCII));

        // Output the result
        System.out.println("");
        System.out.println("Ciphertext: " + Base64.getEncoder().encodeToString(ciphertext));
        System.out.println("Key: " + Base64.getEncoder().encodeToString(key.getEncoded()));
        System.out.println("Salt: " + Base64.getEncoder().encodeToString(globalSalt));
        System.out.println("IV: " + Base64.getEncoder().encodeToString(rawIv));
        System.out.println("Tag Size: " + 128 + " bits.");
       
        vaultManager.addAccountToVault(Base64.getEncoder().encodeToString(globalSalt), newUser, Base64.getEncoder().encodeToString(ciphertext), Base64.getEncoder().encodeToString(rawIv), newURL); 
        
        return encryptedValues;

    } // End 'main' method

    public static void decrypt(String ct, String IV) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, AEADBadTagException, InvalidKeySpecException, IOException {
 
        int tagSize = 128; // 128-bit authentication tag.
        SecretKey key= scrypt(charPwd,globalSalt);
        byte[] keyBytes = key.getEncoded();

        // Set up an AES cipher object.
        Cipher aesCipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Setup the key.
        SecretKeySpec aesKey = new SecretKeySpec(keyBytes, "AES");

        // Put the cipher in encrypt mode with the specified key.
        aesCipher.init(Cipher.DECRYPT_MODE, aesKey, new GCMParameterSpec(tagSize, Base64.getDecoder().decode(IV)));

        // Finalize the message.
        byte[] plaintext = aesCipher.doFinal(Base64.getDecoder().decode(ct));

        System.out.println("Original password: " + new String(plaintext));

    } // End 'decrypt' method



    
} // End 'Scrypt_Encrypt_Decrypt' class
