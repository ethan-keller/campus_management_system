package nl.tudelft.oopp.demo.encryption;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class CommunicationMethods {
    public static String decodeCommunication(String input) throws UnsupportedEncodingException {
        String res = URLDecoder.decode(input, StandardCharsets.UTF_8.toString());
        return res;
    }
}
