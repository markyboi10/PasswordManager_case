package data.managers;

import csc3055.json.parser.JSONParser;
import csc3055.json.parser.ast.SyntaxTree;
import csc3055.json.parser.ast.nodes.SyntaxNode;
import csc3055.json.types.JSONArray;
import csc3055.json.types.JSONObject;
import data.Vault;
import data.Accounts;
import data.objects.VaultValue;
import data.objects.AccountValue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

public class VaultManager implements Manager {

    // Objects
    public Vault vault;
    @Getter
    public static String FILE_NAME = "vault.json";

    // Constructor
    public VaultManager() {

    }

    @Override
    public void init() throws IOException {

        // Initialize vault
        vault = new Vault();

        // Determine if file exists
        File file = new File(FILE_NAME);

        // If not, create file
        if (!file.exists() || !file.isFile()) {
            file.createNewFile();
        } // Else load file into publicKeyRing
        else {
            loadJSON(file);
        }
    } // End 'init' method

    /**
     * Adds an account to the vault
     *
     * @param salt
     * @param username
     * @param password
     * @param iv
     * @param url
     */
    public void addAccountToVault(String salt, String username, String password, String iv, String url) {

        // Check for a vault already existing
        VaultValue vaultValue = getVault(salt);
        if (vaultValue == null) { // If the vault does not exist, create one
            vaultValue = new VaultValue(salt, new Accounts());
            vault.add(vaultValue); // Add the new vault to vaults
        }

        // Create the account
        AccountValue photo = new AccountValue(
                username, password, iv, url
        );

        // Add the account to vault.accounts()
        vaultValue.getSalt().add(photo);
        sendJSON(new File(FILE_NAME));
    }
    
    /**
     * Finds a photo from a collection & photo_name
     *
     * @param vault
     * @param url_name
     * @return
     */
    public AccountValue getAccountFromVault(VaultValue vault, String url_name) {
        for (Object n : vault.getSalt()) {
            if (n instanceof AccountValue m) {
                if (m.getUrl().equalsIgnoreCase(url_name)) { // If the url is found, break
                    return m;
                }
            }
        }
        System.out.println("URL not found in JSON");
        return null; // If no url is found, return null
    }

    /**
     *
     * @param salt
     * @return
     */
    public VaultValue getVault(String salt) {
        for (Object n : vault) {
            if (n instanceof VaultValue m) {
                if (m.getString("salt").equalsIgnoreCase(salt)) {
                    return m;
                }
            }
        }
        return null; // If no vault is found return null
    }

        /**
     * Get all accounts from a vault with salt name
     * {@code collection_name}
     *
     * @param salt_name
     * @return
     */
    public  List<AccountValue> getAccountsFromVault(String salt_name) {
        
        VaultValue collectionValue = getVault(salt_name);

        if (collectionValue == null) {
            System.out.println("Collection with the name " + salt_name + " not found. May be due to a new collection added. Try a new launch.");
            return null;
        }

        // Return a collection of mapped PhotoValues.
        return collectionValue.getSalt().stream().filter(n -> n instanceof AccountValue).map(n -> (AccountValue) n).collect(Collectors.toList());
    }
    
    @Override
    public String getJSON() {
        return vault.getFormattedJSON();
    }

    /*
    Loads JSON file
     */
    @Override
    public void loadJSON(File file) {

        try {
            if (!file.exists() || !file.isFile()) {
                file.createNewFile();
            }

            // Read JSON into the vault
            JSONParser jsonParser = new JSONParser(file);
            SyntaxTree tree = jsonParser.parse();

            // Root is a JSONArray vault
            SyntaxNode root = tree.getRootNode();

            // Tree did not load, file has not been constructed yet.
            if (root == null) {
                return;
            }

            JSONArray collections = (JSONArray) root.evaluate();

            // Load in all vaults
            for (Object n : collections) {
                if (n instanceof JSONObject m) { // Create collection m
                    Accounts photos = new Accounts();
                    VaultValue collection = new VaultValue(m.getString("salt"), photos);

                    // Load in all accounts
                    JSONArray photosArray = m.getArray("accounts");
                    for (Object q : photosArray) {
                        if (q instanceof JSONObject pObj) { // Create an account

                            AccountValue photo = new AccountValue(
                                    pObj.getString("username"),
                                    pObj.getString("password"),
                                    pObj.getString("iv"),
                                    pObj.getString("url")
                            );

                            photos.add(photo); // Add the account to accounts

                        } // End inner-if

                    } // End inner-for

                    this.vault.add(collection); // Add the vault to gloabl vaults

                } // End outer-if

            } // End outer-for

        } catch (IOException e) {
            System.out.println("IOException, line 149");
        } // End try-catch

    } // End 'loadJSON' method

    /*
    Writes JSON out to project
    directory
     */
    @Override
    public void sendJSON(File file) {
        try {
            if (!file.exists() || !file.isFile()) {
                file.createNewFile();
            }
            // Write formatted json to FILE
            try ( BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(getJSON());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } // End try-catch

    } // End 'sendJSON' method

} // End class 'VaultManager'
