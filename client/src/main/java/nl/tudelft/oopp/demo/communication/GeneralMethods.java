package nl.tudelft.oopp.demo.communication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GeneralMethods {
    public static String encodeCommunication(String params) throws UnsupportedEncodingException {
        params = URLEncoder.encode(params, StandardCharsets.UTF_8.toString());
        params = params.replaceAll("%26", "&");
        params = params.replaceAll("%3D", "=");
        return params;
    }
}
