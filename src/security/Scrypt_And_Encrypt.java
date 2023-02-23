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
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;

/*
Bouncy castle imports
 */
import org.bouncycastle.jcajce.spec.ScryptKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Scrypt_And_Encrypt {

    // Name of file where salt(iv) is stored
    private static final String SALT_FILE = "C:\\Users\\Mark Case\\Documents\\NetBeansProjects\\PasswordManager\\test\\salt.txt";

    // Test main
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, IOException {

        /* 
        Objects
         */
        Scanner in = new Scanner(System.in); // Scanner obj for salt.txt
        Console cons = System.console(); // Used to get copy of console
        String password; // password input intiialization
        byte[] iv; // salt(iv) initialization

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
            String salt = reader.readLine();
            iv = Base64.getDecoder().decode(salt);
        } else { // If it doesn't exist, create one and populate salt.txt
            iv = new byte[16]; // 16 byte IV for an AES key
            SecureRandom rand = new SecureRandom();
            rand.nextBytes(iv);

            try ( FileOutputStream outStream = new FileOutputStream(saltFile)) {
                outStream.write(Base64.getEncoder().encodeToString(iv).getBytes()); // Write out as encoded string
            } // End try

        } // End if

        /*
        Derive the AES key from password using the password
        Memory required to run, in bytes:
        128 * cost * blockSize * parallelization
        the password arg expects array of chars NOT bytes
         */
        scryptSpec = new ScryptKeySpec(password.toCharArray(), iv, cost, blockSize, parallelization, keySize);
        // Generate the key
        SecretKey key = SecretKeyFactory.getInstance("SCRYPT").generateSecret(scryptSpec);

        // Encrypt using GCM
        Cipher aesCipher = Cipher.getInstance("AES/GCM/NoPadding");
        aesCipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(tagSize, iv));
        String msg = password;
        byte[] ciphertext = aesCipher.doFinal(msg.getBytes(StandardCharsets.US_ASCII));

        // Output the result
        System.out.println("Ciphertext: " + Base64.getEncoder().encodeToString(ciphertext));
        System.out.println("Key: " + Base64.getEncoder().encodeToString(key.getEncoded()));
        System.out.println("IV: " + Base64.getEncoder().encodeToString(iv));
        System.out.println("Tag Size: " + tagSize + " bits.");

    } // End 'main' method

} // End 'Scrypt_And_Encrypt' class
