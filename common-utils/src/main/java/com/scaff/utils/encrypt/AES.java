package com.scaff.utils.encrypt;

/**
 * Created by xyl on 12/29/17.
 */
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import jodd.util.Base64;


public class AES {

    private static final String AESTYPE = "AES/ECB/PKCS5Padding";

    public static String AES_Encrypt(String keyStr, String plainText) throws Exception {
        Key key = generateKey(keyStr);
        Cipher cipher = Cipher.getInstance(AESTYPE);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypt = cipher.doFinal(plainText.getBytes());
        return new String(Base64.encodeToByte(encrypt));
    }

    public static String AES_Decrypt(String keyStr, String encryptData) throws Exception {
        Key key = generateKey(keyStr);
        Cipher cipher = Cipher.getInstance(AESTYPE);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypt = cipher.doFinal(Base64.decode(encryptData));
        return new String(decrypt).trim();
    }

    private static Key generateKey(String key) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        return keySpec;
    }

    public static void main(String[] args) throws Exception {

//        String keyStr = "UITN25LMUQC436IM";
        String keyStr = "170EA05A020A877B";

        String plainText = "this is a string will be AES_Encrypt";

        String encText = AES_Encrypt(keyStr, plainText);
        String decString = AES_Decrypt(keyStr, encText);

        System.out.println(encText);
        System.out.println(decString);

    }

}
