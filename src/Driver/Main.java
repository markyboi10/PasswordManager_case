package Driver;

import data.managers.VaultManager;

import lombok.Getter;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import security.Scrypt_And_Encrypt;

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

            vaultManager.addAccountToVault("evk+aFczU8DQAyYrDYrX+w==", "bob", "thENIJTkCwZ3B5oNLomJQgLMllqGrnI=",
                    "Peuslavh1IPQNUtMSFTcug==",
                    "merrimack.edu");
            vaultManager.addAccountToVault("evk+aFczU8DQAyYrDYrX+w==", "max", "thENIJTkCwZ3B5oNLomJQgLMllqGrnI=",
                    "Peuslavh1IPQNUtMSFTcug==",
                    "google.com");

            System.out.println(vaultManager.getJSON());
            try {
                System.out.println(Scrypt_And_Encrypt.scrypt_and_encrypt());
            } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException e) {
            System.out.println("IOException, line 36");
        } // End try-catch

    } // End 'main' method

} // End 'Main' class
