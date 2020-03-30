package nl.tudelft.oopp.demo.encryption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionManager {
    private static SecretKeySpec secretKey;
    private static byte[] key;

    private static Logger logger = LoggerFactory.getLogger("n.t.o.d.encryption.EncryptionManager");

    /**
     * Configures myKey to a SecretKeySpec and set's secretKey to that.
     *
     * @param myKey The unconfigured key you want to use.
     */
    public static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            logger.error("Encryption: -setKey- ERROR", e);
        }
    }

    /**
     * Encrypts a string using the provided secret key.
     *
     * @param strToEncrypt The String you want to encrypt
     * @param secret       The encryption-key
     * @return Returns the encrypted String.
     */
    public static String encrypt(String strToEncrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            logger.error("Encryption: -encrypt- ERROR", e);
        }
        return null;
    }

    /**
     * Decrypts a encrypted String.
     *
     * @param strToDecrypt The String you want to decrypt.
     * @param secret       The key used to encrypt.
     * @return The original String before encryption.
     */
    public static String decrypt(String strToDecrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            logger.error("Encryption: -decrypt- ERROR", e);
        }
        return null;
    }
}
