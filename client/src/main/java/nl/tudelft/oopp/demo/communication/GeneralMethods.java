package nl.tudelft.oopp.demo.communication;

import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * This method encodes all communication that occurs between the server and the client.
 * This method is used in all the communication classes that need to send or receive information
 * from the server.
 * @return the body of a get request to the server.
 * @throws Exception if communication has unsupported encoding mechanism.
 */
public class GeneralMethods {
    /**
     * This method is to encode all communication across the data stream.
     * @param params are passed
     * @return :Encoded parameters as string
     * @throws UnsupportedEncodingException is thrown
     */
    public static String encodeCommunication(String params) throws UnsupportedEncodingException {
        params = URLEncoder.encode(params, StandardCharsets.UTF_8.toString());
        params = params.replaceAll("%26", "&");
        params = params.replaceAll("%3D", "=");
        return params;
    }

    /**.
     * creates an alert message which ??
     * @param title
     * @param content
     * @param owner
     * @param type
     * @return Alert
     */
    public static Alert createAlert(String title, String content, Window owner,
                                    Alert.AlertType type) {
        try {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.initOwner(owner);
            alert.initModality(Modality.WINDOW_MODAL);
            return alert;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
