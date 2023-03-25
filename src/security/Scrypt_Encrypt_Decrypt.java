package security;

import static Driver.Main.vaultManager;
import UI.myGUI_1;
import static UI.myGUI_1.finalURL;
import static UI.myGUI_1.finalUser;
import static UI.myGUI_1.saltString;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.managers.VaultManager;
import data.objects.AccountValue;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
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
    public static char[] charPwd = null;

    public static SecretKey scrypt(char[] authentificationPass, byte[] salt) {
        char[] authPass = authentificationPass;

        /* 
        Objects
         */
        // Default values given by project description
        final int cost = 32768; // Iterations
        final int blockSize = 32;
        final int parallelization = 4; // Num of parallel threads to use
        final int keySize = 256;
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

    public static ArrayList encrypt(String newURL, String newUser, char[] newPassword, char[] masterPass) {

        // Call scrypt function
        SecretKey key = scrypt(masterPass, globalSalt);

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
        
        aesCipher.updateAAD(newURL.getBytes(Charset.forName("UTF-8")));
        aesCipher.updateAAD(newUser.getBytes(Charset.forName("UTF-8")));

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

    public static byte[] decrypt(String ct, String IV, char[] masterPass) throws IOException {

        int tagSize = 128; // 128-bit authentication tag.
        SecretKey key = scrypt(masterPass, globalSalt);
        byte[] keyBytes = key.getEncoded();

        // Set up an AES cipher object.
        Cipher aesCipher = null;
        try {
            aesCipher = Cipher.getInstance("AES/GCM/NoPadding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(Scrypt_Encrypt_Decrypt.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Setup the key.
        SecretKeySpec aesKey = new SecretKeySpec(keyBytes, "AES/GCM/NoPadding");

        try {
            // Put the cipher in encrypt mode with the specified key.
            aesCipher.init(Cipher.DECRYPT_MODE, aesKey, new GCMParameterSpec(tagSize, Base64.getDecoder().decode(IV)));
        } catch (InvalidKeyException | InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Scrypt_Encrypt_Decrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        Grab salt from file
         */
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(VaultManager.getFILE_NAME()));
        saltString = rootNode.get(0).get("salt").asText();
        globalSalt = Base64.getDecoder().decode(saltString);

        // Grab account values given salt and url
        AccountValue accountValues = vaultManager.getAccountFromVault(vaultManager.getVault(saltString), myGUI_1.existingWebsite_textField.getText());

        if (accountValues != null) {
            // Parameters to be passed in
            finalURL = accountValues.getUrl();
            finalUser = accountValues.getUsername();
            aesCipher.updateAAD(finalURL.getBytes(Charset.forName("UTF-8")));
            aesCipher.updateAAD(finalUser.getBytes(Charset.forName("UTF-8")));
        }

        // Finalize the message.
        byte[] plaintext = null;
        try {
            plaintext = aesCipher.doFinal(Base64.getDecoder().decode(ct));
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            return null;
        }

        return plaintext;

    } // End 'decrypt' method

} // End 'Scrypt_Encrypt_Decrypt' class
