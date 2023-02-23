package data.objects;

import csc3055.json.types.JSONObject;
import data.Accounts;
import lombok.Data;

/**
 * Holds a salt and accounts object which is a JSONArray of AccountValue objects
 */
@Data
public class VaultValue extends JSONObject {

    private final String accounts;
    private final Accounts salt;

    /**
     * Initialization Constructor
     *
     * @param accounts
     * @param salt
     */
    public VaultValue(String accounts, Accounts salt) {
        this.accounts = accounts;
        this.salt = salt;

        this.put("salt", accounts);
        this.put("accounts", salt);
    } // End contructor 'VaultValue'

} // End class 'VaultValue'  
