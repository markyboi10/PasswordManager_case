package Driver;

import data.managers.VaultManager;

import lombok.Getter;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

    public static void main(String[] args) {
        /**
         * Initialize manager
         */
        try {

            vaultManager = new VaultManager();
            vaultManager.init();

    
            //String salt, String username, String password, String iv, String url

            System.out.println(vaultManager.getJSON());
            try {
                Scrypt_And_Encrypt.scrypt_and_encrypt(); // Call encryption
                
               // byte[] globalSalt = Scrypt_And_Encrypt.getGlobalSalt(); // Grab salt from file
                //String salt = Base64.getEncoder().encodeToString(globalSalt); // Convert to string
                
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
