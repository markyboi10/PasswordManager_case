package UI;

import static Driver.Main.vaultManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.managers.VaultManager;
import data.objects.AccountValue;
import data.objects.VaultValue;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.UrlValidator;
import security.Scrypt_Encrypt_Decrypt;

/**
 *
 * @author Mark Case
 */
public class myGUI extends javax.swing.JFrame {

    /*
    Objects
    */
    private static String newURL = null;
    private static String newUser = null;
    private static char[] newPassword = null;
    private static char[] genPassword = null;
    public static byte[] globalSalt = null;
    public static String saltString = null;
    public static byte[] finalPasswordByteArray =  null; 
    List<AccountValue> users = new ArrayList<>();
    File vaultFile = new File(VaultManager.getFILE_NAME());
    DialogBox db = new DialogBox();
        public static String finalURL = null;
    public static String finalUser = null;
    DefaultListModel defaultListModel = new DefaultListModel();
    
    /**
     * Creates new form myGUI
     */
    public myGUI() {
        initComponents(); // Sets gui components
 
        try {
            // If file contains data, read it and set salt
            if (vaultFile.length() != 0) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(new File(VaultManager.getFILE_NAME()));
                saltString = rootNode.get(0).get("salt").asText();
                globalSalt = Base64.getDecoder().decode(saltString);
                this.bindData();
            }
        } catch (IOException ex) {
            Logger.getLogger(myGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    } // End contructor 'myGUI'

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jDialog2 = new javax.swing.JDialog();
        jDialog3 = new javax.swing.JDialog();
        root_pane = new keeptoo.KGradientPanel();
        passwordManager_label = new javax.swing.JLabel();
        newAccount_label = new javax.swing.JLabel();
        newUser_textField = new javax.swing.JTextField();
        username_label = new javax.swing.JLabel();
        password_label = new javax.swing.JLabel();
        website_label = new javax.swing.JLabel();
        newWebsite_textField = new javax.swing.JTextField();
        generatePassword_btn = new javax.swing.JButton();
        addToManager_btn = new javax.swing.JButton();
        existingWebsite_textField = new javax.swing.JTextField();
        findExistingAccountDetails_label = new javax.swing.JLabel();
        existingWebsite_label = new javax.swing.JLabel();
        passwordField_hiddenField = new javax.swing.JPasswordField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog3Layout = new javax.swing.GroupLayout(jDialog3.getContentPane());
        jDialog3.getContentPane().setLayout(jDialog3Layout);
        jDialog3Layout.setHorizontalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog3Layout.setVerticalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        root_pane.setkEndColor(new java.awt.Color(176, 106, 179));
        root_pane.setkGradientFocus(850);
        root_pane.setkStartColor(new java.awt.Color(69, 104, 220));

        passwordManager_label.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        passwordManager_label.setForeground(new java.awt.Color(204, 204, 204));
        passwordManager_label.setText("Password Manager");

        newAccount_label.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        newAccount_label.setForeground(new java.awt.Color(204, 204, 204));
        newAccount_label.setText("Add New Account:");

        newUser_textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                newUser_textFieldFocusLost(evt);
            }
        });
        newUser_textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newUser_textFieldActionPerformed(evt);
            }
        });

        username_label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        username_label.setForeground(new java.awt.Color(204, 204, 204));
        username_label.setText("Username:");

        password_label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        password_label.setForeground(new java.awt.Color(204, 204, 204));
        password_label.setText("Password:");

        website_label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        website_label.setForeground(new java.awt.Color(204, 204, 204));
        website_label.setText("Website:");

        newWebsite_textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                newWebsite_textFieldFocusLost(evt);
            }
        });
        newWebsite_textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newWebsite_textFieldActionPerformed(evt);
            }
        });

        generatePassword_btn.setText("Generate Secure Password");
        generatePassword_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatePassword_btnActionPerformed(evt);
            }
        });

        addToManager_btn.setText("Add to Manager");
        addToManager_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToManager_btnActionPerformed(evt);
            }
        });

        existingWebsite_textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existingWebsite_textFieldActionPerformed(evt);
            }
        });
        existingWebsite_textField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                existingWebsite_textFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                existingWebsite_textFieldKeyReleased(evt);
            }
        });

        findExistingAccountDetails_label.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        findExistingAccountDetails_label.setForeground(new java.awt.Color(204, 204, 204));
        findExistingAccountDetails_label.setText("Search Existing Account:");

        existingWebsite_label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        existingWebsite_label.setForeground(new java.awt.Color(204, 204, 204));
        existingWebsite_label.setText("Website:");

        passwordField_hiddenField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwordField_hiddenFieldFocusLost(evt);
            }
        });

        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/macEnterBtn.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout root_paneLayout = new javax.swing.GroupLayout(root_pane);
        root_pane.setLayout(root_paneLayout);
        root_paneLayout.setHorizontalGroup(
            root_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(root_paneLayout.createSequentialGroup()
                .addGroup(root_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(root_paneLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(root_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(root_paneLayout.createSequentialGroup()
                                .addGroup(root_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(root_paneLayout.createSequentialGroup()
                                        .addGroup(root_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(root_paneLayout.createSequentialGroup()
                                                .addGroup(root_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(username_label, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                                                    .addComponent(password_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(root_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(root_paneLayout.createSequentialGroup()
                                                        .addGroup(root_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(addToManager_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(passwordField_hiddenField, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(generatePassword_btn))
                                                    .addComponent(newUser_textField, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(154, 154, 154))
                                            .addGroup(root_paneLayout.createSequentialGroup()
                                                .addComponent(website_label, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(newWebsite_textField, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(existingWebsite_label, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                        .addGroup(root_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(existingWebsite_textField, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(findExistingAccountDetails_label, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1))
                            .addComponent(newAccount_label, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(root_paneLayout.createSequentialGroup()
                        .addGap(251, 251, 251)
                        .addComponent(passwordManager_label)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        root_paneLayout.setVerticalGroup(
            root_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(root_paneLayout.createSequentialGroup()
                .addComponent(passwordManager_label)
                .addGroup(root_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(root_paneLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(root_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(newAccount_label)
                            .addComponent(findExistingAccountDetails_label))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(root_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(website_label, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(newWebsite_textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(76, 76, 76)
                        .addGroup(root_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(newUser_textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(username_label, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addGroup(root_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(password_label, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(generatePassword_btn)
                            .addComponent(passwordField_hiddenField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addComponent(addToManager_btn))
                    .addGroup(root_paneLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(root_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(root_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(existingWebsite_textField)
                                .addComponent(existingWebsite_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)
                        .addGap(14, 14, 14)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(root_pane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(root_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Deprecated
    private void newWebsite_textFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newWebsite_textFieldActionPerformed

    }//GEN-LAST:event_newWebsite_textFieldActionPerformed

    @Deprecated
    private void newUser_textFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newUser_textFieldActionPerformed

    }//GEN-LAST:event_newUser_textFieldActionPerformed

    /*
    Adds a new account to the vault,
    will not allow duplicate urls for 
    simplicity.
    */
    private void addToManager_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addToManager_btnActionPerformed

        if (newUser_textField.getText().equals("")|| newWebsite_textField.getText().equals("") || passwordField_hiddenField.getPassword().equals("")) {
             // do not initiate action performed, break out of it
            JOptionPane.showMessageDialog(rootPane, "Missing information, please fill out all fields", "EEROR", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (passwordField_hiddenField.getPassword().length > 0 && newPassword != null && newPassword.length >= 1 && newPassword.length <= 7) {   
            int res = JOptionPane.showConfirmDialog(rootPane, "Weak password. It is highly recommended have AT LEAST 8 characters!", "WARNING", JOptionPane.OK_CANCEL_OPTION);
            if(res == JOptionPane.OK_OPTION) {
                //continue
            } else {
                return;
            }
        } else {
            // continue
        }

        boolean urlExists = false;
        if (vaultFile.length() != 0) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(new File(VaultManager.getFILE_NAME()));
                saltString = rootNode.get(0).get("salt").asText();
                globalSalt = Base64.getDecoder().decode(saltString);
                List<AccountValue> accounts = vaultManager.getAccountsFromVault(saltString); // Call getAccountsFromVault
                // Loop through the list
                for (AccountValue account : accounts) {
                    // If URL already exists, set boolean true and break
                    if (account.getUrl().equals(newURL)) {
                        urlExists = true;
                        break;
                    } // End inner-if
                } // End for
            } // End outer-if
            catch (IOException ex) {
                Logger.getLogger(myGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // If url exists, error message sends
        if (urlExists) {
            JOptionPane.showMessageDialog(rootPane, "URL already contains an account!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else { // Else, add account to vault
            try {
                // Encrypt generated password
                if (passwordField_hiddenField.getPassword().length == 0 && genPassword != null) {
                    System.out.println("gen" + Arrays.toString(genPassword));
                    Scrypt_Encrypt_Decrypt.encrypt(newURL, newUser, genPassword);
                    genPassword = null;
                } else if (passwordField_hiddenField.getPassword().length != 0 && newPassword != null){ // Encrypt user's password
                    Scrypt_Encrypt_Decrypt.encrypt(newURL, newUser, newPassword);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Missing information, please fill out all fields", "EEROR", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | IOException ex) {
                Logger.getLogger(myGUI.class.getName()).log(Level.SEVERE, null, ex);
            } // End try-catch
            
        } // End if-else
        
    }//GEN-LAST:event_addToManager_btnActionPerformed
    
    private void newUser_textFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_newUser_textFieldFocusLost
        
        newUser = newUser_textField.getText();

    }//GEN-LAST:event_newUser_textFieldFocusLost

    private void newWebsite_textFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_newWebsite_textFieldFocusLost
        newURL = newWebsite_textField.getText();
        
        UrlValidator urlValidator = new UrlValidator();
        if (newURL.length() > 0) {
        if (urlValidator.isValid(newURL)) {
             newWebsite_textField.setBorder(new JTextField().getBorder());

        } else {
            newWebsite_textField.setText("");
             newWebsite_textField.setBorder(new LineBorder(Color.RED));
            JOptionPane.showMessageDialog(rootPane, "Invalid URL", "EEROR", JOptionPane.ERROR_MESSAGE);
           
        }
        }
    }//GEN-LAST:event_newWebsite_textFieldFocusLost

    /*
    If generated password button is hit,
    use secure random instance to create a password
    from the possible characters. Uses char array s.t.
    to avoid garbage collection type attacks
    */
    private void generatePassword_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatePassword_btnActionPerformed
               if (newUser_textField.getText().equals("")|| newWebsite_textField.getText().equals("") || passwordField_hiddenField.getPassword().equals("")){
             JOptionPane.showMessageDialog(rootPane, "Missing information, please fill out all fields", "EEROR", JOptionPane.ERROR_MESSAGE);
                   return; 
        } else {
        passwordField_hiddenField.setText("");
        char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?").toCharArray();
        String randomStr = RandomStringUtils.random(70, 0, possibleCharacters.length - 1, false, false, possibleCharacters, new SecureRandom());
        System.out.println(randomStr);
        genPassword = randomStr.toCharArray();
        
 
            int res = JOptionPane.showConfirmDialog(rootPane, "Password generated, hit ok to add to manager OR cancel to edit", "INFO", JOptionPane.OK_CANCEL_OPTION);
                    if(res == JOptionPane.OK_OPTION) {
                ActionEvent actionEvent = new ActionEvent(evt.getSource(), evt.getID(), "ok");
        addToManager_btnActionPerformed(actionEvent);
            } else {
            }
        }
    }//GEN-LAST:event_generatePassword_btnActionPerformed

    @Deprecated
    private void existingWebsite_textFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingWebsite_textFieldActionPerformed

    }//GEN-LAST:event_existingWebsite_textFieldActionPerformed

    /*
    Used to search for account details, given
    the url.
    */
    private void existingWebsite_textFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_existingWebsite_textFieldKeyPressed
        try {
            // If enter is pressed
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(VaultManager.getFILE_NAME()));
            saltString = rootNode.get(0).get("salt").asText();
            globalSalt = Base64.getDecoder().decode(saltString);
            searchFilter(existingWebsite_textField.getText());
            if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                
                
                //            try {
//                /*
//                Grab salt from file
//                */


test();
//
//
//                // Grab account values given salt and url
//                AccountValue accountValues = vaultManager.getAccountFromVault(vaultManager.getVault(saltString), existingWebsite_textField.getText());
//
//                 if (accountValues !=  null) {
//
//
//                
//                System.out.println(accountValues);
//                System.out.println(saltString);
//                System.out.println(existingWebsite_textField.getText());
//                // Parameters to be passed in
//                finalURL = accountValues.getUrl();
//                finalUser = accountValues.getUsername();
//                String password = accountValues.getPassword();
//                String iv = accountValues.getIv();
//
//                                        getURL();
//
//                
//                try {
//                    // Decrypt with parameters
//                    Scrypt_Encrypt_Decrypt.decrypt(password, iv);
//                    finalPasswordByteArray = Scrypt_Encrypt_Decrypt.decrypt(password, iv);
//                } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | InvalidKeySpecException | IOException ex) {
//                    Logger.getLogger(myGUI.class.getName()).log(Level.SEVERE, null, ex);
//                } // End inner try-catch
//                 
//               } else {
//                     System.out.println("URL NOT FOUND");
//                     return;
//                 }
//            } catch (IOException ex) {
//                Logger.getLogger(myGUI.class.getName()).log(Level.SEVERE, null, ex);
//            } // End outer try-catch
//
//         
//        if (db != null) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(DialogBox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//            //</editor-fold>
//            
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(() -> {
//            db.setVisible(true);
//            setDefaultCloseOperation(DialogBox.DISPOSE_ON_CLOSE);
//        });
//        } else {
//            JOptionPane.showMessageDialog(rootPane, "Exit the current 'Dialog Box' before loading a new one", "WARNING", JOptionPane.WARNING_MESSAGE);
//        }
                
            }
        } catch (IOException ex) {
            Logger.getLogger(myGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_existingWebsite_textFieldKeyPressed

    private void passwordField_hiddenFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordField_hiddenFieldFocusLost
        if(passwordField_hiddenField.getPassword().length != 0) {    
            newPassword = passwordField_hiddenField.getPassword();
        }
        
        
    }//GEN-LAST:event_passwordField_hiddenFieldFocusLost

    private void existingWebsite_textFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_existingWebsite_textFieldKeyReleased
       //searchFilter(existingWebsite_textField.getText());
    }//GEN-LAST:event_existingWebsite_textFieldKeyReleased

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        if(evt.getClickCount() == 2) {
           
           
             
              // Call existingWebsite_textFieldKeyPressed with the appropriate KeyEvent
        KeyEvent keyEvent = new KeyEvent(existingWebsite_textField, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
        existingWebsite_textFieldKeyPressed(keyEvent);
        } else if (evt.getClickCount() == 1) {
             String index = jList1.getSelectedValue();
            existingWebsite_textField.setText(index);
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
       System.out.println("Hello");
       test();
    }//GEN-LAST:event_jLabel1MouseClicked

    public static byte[] getFinalPasswordByteArray() {
        return finalPasswordByteArray;
    }

    public static String getFinalURL() {
        return finalURL;
    }

    public static String getFinalUser() {
        return finalUser;
    }

    private ArrayList getURL() {
        ArrayList urls = new ArrayList();
        
        List<AccountValue> accounts = vaultManager.getAccountsFromVault(saltString); // Call getAccountsFromVault 
        System.out.println("Accounts:" + saltString);
        // Loop through the list
        for (AccountValue account : accounts) {
            urls.add(account.getUrl());
        } // End for
        
        return urls;
    }

    private void bindData () {
        getURL().stream().forEach((urls) -> {
            defaultListModel.addElement(urls);
        });
        jList1.setModel(defaultListModel);
        jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void searchFilter(String searchTerm) {
       DefaultListModel def = new DefaultListModel();
        ArrayList urls = getURL();
        
        urls.stream().forEach((url)-> {
          String urlName = url.toString();
          if(urlName.contains(searchTerm)) {
              def.addElement(url);
          }
        });
        defaultListModel=def;
        jList1.setModel(defaultListModel);
    }
    
    private void test() {
                   try {
                /*
                Grab salt from file
                */
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(new File(VaultManager.getFILE_NAME()));
                saltString = rootNode.get(0).get("salt").asText();
                globalSalt = Base64.getDecoder().decode(saltString);
                

                // Grab account values given salt and url
                AccountValue accountValues = vaultManager.getAccountFromVault(vaultManager.getVault(saltString), existingWebsite_textField.getText());

                 if (accountValues !=  null) {
                        
                        
                
                System.out.println(accountValues);
                System.out.println(saltString);
                System.out.println(existingWebsite_textField.getText());
                // Parameters to be passed in
                finalURL = accountValues.getUrl();
                finalUser = accountValues.getUsername();
                String password = accountValues.getPassword();
                String iv = accountValues.getIv();

                                        getURL();
               
                
                try {
                    // Decrypt with parameters
                    Scrypt_Encrypt_Decrypt.decrypt(password, iv);
                    finalPasswordByteArray = Scrypt_Encrypt_Decrypt.decrypt(password, iv);
                } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | InvalidKeySpecException | IOException ex) {
                    Logger.getLogger(myGUI.class.getName()).log(Level.SEVERE, null, ex);
                } // End inner try-catch
                 
               } else {
                     System.out.println("URL NOT FOUND");
                     return;
                 }
            } catch (IOException ex) {
                Logger.getLogger(myGUI.class.getName()).log(Level.SEVERE, null, ex);
            } // End outer try-catch
            
         
        if (db != null) {
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogBox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
            //</editor-fold>
            
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            db.setVisible(true);
            setDefaultCloseOperation(DialogBox.DISPOSE_ON_CLOSE);
        });
        } else {
            JOptionPane.showMessageDialog(rootPane, "Exit the current 'Dialog Box' before loading a new one", "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addToManager_btn;
    private javax.swing.JLabel existingWebsite_label;
    private javax.swing.JTextField existingWebsite_textField;
    private javax.swing.JLabel findExistingAccountDetails_label;
    private javax.swing.JButton generatePassword_btn;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JDialog jDialog3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel newAccount_label;
    private static javax.swing.JTextField newUser_textField;
    private static javax.swing.JTextField newWebsite_textField;
    private javax.swing.JPasswordField passwordField_hiddenField;
    private javax.swing.JLabel passwordManager_label;
    private javax.swing.JLabel password_label;
    private keeptoo.KGradientPanel root_pane;
    private javax.swing.JLabel username_label;
    private javax.swing.JLabel website_label;
    // End of variables declaration//GEN-END:variables
} // End class 'myGUI'
