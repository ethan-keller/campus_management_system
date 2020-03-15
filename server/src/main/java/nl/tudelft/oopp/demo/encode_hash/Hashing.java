package nl.tudelft.oopp.demo.encode_hash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Hashing {

    public static String hashIt(String input)
    {
        try
        {
            String algorithm = "SHA-256";
            String res = "";
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.reset();
            byte[] hash = digest.digest(input.getBytes());
            res = new String(hash);

            StringBuilder sb = new StringBuilder();
            for(int i=0; i< hash.length ;i++) //changes bytes to a String
            {
                sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
            }

            res = sb.toString();

            return res;
        }
        catch (Exception e)
        {
            System.out.println("Error while hashing: " + e.toString());
        }
        return null;
    }


}
