package security;

import static Driver.Main.vaultManager;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public static char[] charPwd = new char[]{'M', 'e', 'r', 'r', 'i', 'm', 'a', 'c', 'k', '2', '0', '2', '2', '!'};

    public static SecretKey scrypt(char[] authentificationPass, byte[] salt) {
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
        SecretKey key = null;
        try {
            key = SecretKeyFactory.getInstance("SCRYPT").generateSecret(scryptSpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(Scrypt_Encrypt_Decrypt.class.getName()).log(Level.SEVERE, null, ex);
        }

        return key;

    } // Enm 'scrypt' method

    public static ArrayList encrypt(String newURL, String newUser, char[] newPassword) {

        // Call scrypt function
        SecretKey key = scrypt(charPwd, globalSalt);

        // Encrypt using GCM
        Cipher aesCipher = null;
        try {
            aesCipher = Cipher.getInstance("AES/GCM/NoPadding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(Scrypt_Encrypt_Decrypt.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Generate the IV.
        SecureRandom rand = new SecureRandom();
        byte[] rawIv = new byte[16];		// Block size of AES.
        rand.nextBytes(rawIv);					// Fill the array with random bytes.
        GCMParameterSpec gcmParams = new GCMParameterSpec(128, rawIv);

        try {
            // Encrypt mode, passes in aes(salt gen.) key + (tag size, rawIV)
            aesCipher.init(Cipher.ENCRYPT_MODE, key, gcmParams);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Scrypt_Encrypt_Decrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        char[] msg = newPassword;
        // msg = new String(newPassword);

        byte[] passwordBytes = new String(msg).getBytes(StandardCharsets.US_ASCII);
        byte[] ciphertext = null;
        try {
            ciphertext = aesCipher.doFinal(passwordBytes);
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Scrypt_Encrypt_Decrypt.class.getName()).log(Level.SEVERE, null, ex);
        }

        vaultManager.addAccountToVault(Base64.getEncoder().encodeToString(globalSalt), newUser, Base64.getEncoder().encodeToString(ciphertext), Base64.getEncoder().encodeToString(rawIv), newURL);

        return encryptedValues;

    } // End 'main' method

    public static byte[] decrypt(String ct, String IV) {

        int tagSize = 128; // 128-bit authentication tag.
        SecretKey key = scrypt(charPwd, globalSalt);
        byte[] keyBytes = key.getEncoded();

        // Set up an AES cipher object.
        Cipher aesCipher = null;
        try {
            aesCipher = Cipher.getInstance("AES/GCM/NoPadding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(Scrypt_Encrypt_Decrypt.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Setup the key.
        SecretKeySpec aesKey = new SecretKeySpec(keyBytes, "AES");

        try {
            // Put the cipher in encrypt mode with the specified key.
            aesCipher.init(Cipher.DECRYPT_MODE, aesKey, new GCMParameterSpec(tagSize, Base64.getDecoder().decode(IV)));
        } catch (InvalidKeyException | InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Scrypt_Encrypt_Decrypt.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Finalize the message.
        byte[] plaintext = null;
        try {
            plaintext = aesCipher.doFinal(Base64.getDecoder().decode(ct));
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Scrypt_Encrypt_Decrypt.class.getName()).log(Level.SEVERE, null, ex);
        }

        return plaintext;

    } // End 'decrypt' method

    public static char[] getCharPwd() {
        return charPwd;
    }

} // End 'Scrypt_Encrypt_Decrypt' class
