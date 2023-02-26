package Driver;

import UI.DialogBox;
import UI.myGUI_1;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.managers.VaultManager;
import data.objects.VaultValue;
import java.io.File;
import java.io.FileNotFoundException;
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
import security.Scrypt_Encrypt_Decrypt;
import static security.Scrypt_Encrypt_Decrypt.globalSalt;

/**
 *
 * @author Mark Case
 */
public class Main {

    @Getter
    // Vault Manager
    public static VaultManager vaultManager;
    public static VaultValue vault;
    //private static final String SALT_FILE = "C:\\Users\\Mark Case\\Documents\\NetBeansProjects\\PasswordManager\\test\\salt.txt";
    public static String saltString = null;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        /**
         * Initialize manager
         */
        vaultManager = new VaultManager();
        vaultManager.init();
        // Get vault json file
        File vaultFile = new File(VaultManager.getFILE_NAME());

        // If vault is empty, the salt doesn't exist. So we will create one
        if (vaultFile.length() == 0) {
            System.out.println("JSON is empty, creating salt . . .");
            globalSalt = new byte[16]; // 16 byte IV for an AES key
            SecureRandom rand = new SecureRandom();
            rand.nextBytes(globalSalt);
            System.out.println("Salt is: " + Base64.getEncoder().encodeToString(globalSalt));

        } else { // If vault ISN'T empty, use jackson libraries to read JSON and grab the string where  = "Salt:"
            System.out.println("JSON contains values, locating salt . . .");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(VaultManager.getFILE_NAME()));
            saltString = rootNode.get(0).get("salt").asText();
            System.out.println("Salt is : " + saltString);
            globalSalt = Base64.getDecoder().decode(saltString);

        } // End if
        

                /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(myGUI_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(myGUI_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(myGUI_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(myGUI_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
             myGUI_1 myGUI = new myGUI_1();
             
            myGUI.setFocusableWindowState(false);
            myGUI.setVisible(true);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
myGUI.setFocusableWindowState(true);
        
        });


        /*
        Encryption and decryptin console calls
        */
//        try {
////            Scrypt_Encrypt_Decrypt.scrypt_and_encrypt(); // Call encryption
//
////            ArrayList encryptedValues = Scrypt_Encrypt_Decrypt.getEncryptedValues(); // Grab return array from scrypt_and_encrypt
////
////            /*
////            Parse the returned array
////            */
////            String joinedString = String.join(",", encryptedValues);
////            // Remove the brackets and split the string by comma
////            String[] values = joinedString.substring(1, joinedString.length()).split(",");
////            // Extract the values by splitting each string by colon and stripping the white spaces
////            String url = values[0].split(":")[1].trim();
////            String username = values[1].split(":")[1].trim();
////            String cipherText = values[2].split(":")[1].trim();
////            String key = values[3].split(":")[1].trim(); // Not used rn
////            String iv = values[4].split(":")[1].trim();
////            String salt = values[5].split(":")[1].trim();
////            System.out.println("");
////            System.out.println("```");
////            System.out.println("The return array: " + encryptedValues);
////            System.out.println("```");
////            System.out.println("");
////            /*
////             Pass parameters from returned array, this updates the vault with new account info
////             */
////            vaultManager.addAccountToVault(salt, username, cipherText, iv, url); 
//              vaultManager.getAccountFromVault(vaultManager.getVault(saltString), saltString);
//            Scrypt_Encrypt_Decrypt.decrypt(cipherText, key, iv); // Test decryption call, currently works through console and user input
//
//        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }

        //}
    } // End 'main' method

    public static String getSaltString() {
        return saltString;
    }

} // End 'Main' class
