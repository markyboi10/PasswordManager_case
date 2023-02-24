package Driver;

import data.managers.VaultManager;
import data.objects.VaultValue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import lombok.Getter;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import security.Scrypt_And_Encrypt;
import static security.Scrypt_And_Encrypt.globalSalt;

/**
 *
 * @author Mark Case
 */
public class Main {

    @Getter
    // Vault Manager
    public static VaultManager vaultManager;
    public static VaultValue vault;
     private static final String SALT_FILE = "C:\\Users\\Mark Case\\Documents\\NetBeansProjects\\PasswordManager\\test\\salt.txt";
     public static String saltString = null;
    public static void main(String[] args) {
        /**
         * Initialize manager
         */
        try {

            vaultManager = new VaultManager();
            vaultManager.init();

            // Load the salt(iv) if the file exits
            File saltFile = new File(SALT_FILE);
            if (saltFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(saltFile));
                saltString = reader.readLine();
                //globalSalt = Base64.getDecoder().decode(saltString);
            }
            
            System.out.println(vaultManager.getJSON());
            String url_name = "google.com";

           System.out.println("Values from url:" + vaultManager.getAccountFromVault(vaultManager.getVault(saltString), url_name));
            try {
                Scrypt_And_Encrypt.scrypt_and_encrypt(); // Call encryption
                

                
                ArrayList encryptedValues = Scrypt_And_Encrypt.getEncryptedValues();

                String joinedString = String.join(",", encryptedValues);

                // Remove the brackets and split the string by comma
                String[] values = joinedString.substring(1, joinedString.length() - 1).split(",");

                // Extract the values by splitting each string by colon and stripping the white spaces
                String url = values[0].split(":")[1].trim();
                String username = values[1].split(":")[1].trim();
                String cipherText = values[2].split(":")[1].trim();
                String key = values[3].split(":")[1].trim();
                String iv = values[4].split(":")[1].trim();
                String salt = values[5].split(":")[1].trim();

                    
                
                vaultManager.addAccountToVault(salt, username, cipherText, iv, url);
                
                   
                Scrypt_And_Encrypt.decrypt();
          
            } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException e) {
            System.out.println("IOException, line 36");
        } // End try-catch

    } // End 'main' method

} // End 'Main' class
