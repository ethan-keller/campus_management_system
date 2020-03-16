package nl.tudelft.oopp.demo.encode_hash;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class CommunicationMethods {
    /**
     *Decodes a String using UTF_8 encoding
     *
     * @param input The encoded input
     * @return Returns the decoded input
     * @throws UnsupportedEncodingException
     */
    public static String decodeCommunication(String input) throws UnsupportedEncodingException {
        String res = URLDecoder.decode(input, StandardCharsets.UTF_8.toString());
        return res;
    }
}
