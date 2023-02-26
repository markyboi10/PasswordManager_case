package data.objects;

import csc3055.json.types.JSONObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AccountValue extends JSONObject {

    private final String username;
    private final String password;
    private final String iv;
    private final String url;

    /**
     * Initialization constructor
     *
     * @param username
     * @param password
     * @param iv
     * @param url
     * 
     */
    public AccountValue(String username, String password, String iv, String url) {
        this.username = username;
        this.password = password;
        this.iv = iv;
        this.url = url;

        this.put("username", username);
        this.put("password", password);
        this.put("iv", iv);
        this.put("url", url);

    } // End constructor 'AccountValue'

} // End class 'AccountValue'
