package nl.tudelft.oopp.demo.encodehash;

import java.security.MessageDigest;

public class Hashing {
    /**
     * Hashes the string using the SHA-256 algorithm.
     *
     * @param input The String you want to hash.
     * @return A String containing the hashed result.
     */
    public static String hashIt(String input) {
        try {
            String algorithm = "SHA-256";
            String res = "";
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.reset();
            byte[] hash = digest.digest(input.getBytes());
            res = new String(hash);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {        //changes bytes to a String
                sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
            }

            res = sb.toString();

            return res;
        } catch (Exception e) {
            System.out.println("Error while hashing: " + e.toString());
        }
        return null;
    }


}
