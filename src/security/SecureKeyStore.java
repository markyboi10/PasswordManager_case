/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package security;

import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;

/**
 *
 * @author Mark Case
 */
public class SecureKeyStore {

    private static Map<String, SecretKey> keyMap = new HashMap<String, SecretKey>();

    public static void putKey(String url, SecretKey key) {
        keyMap.put(url, key);
    }

    public static SecretKey getKey(String url) {
        return keyMap.get(url);
    }

}
