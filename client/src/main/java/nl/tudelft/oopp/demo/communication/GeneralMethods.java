package nl.tudelft.oopp.demo.communication;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * This method encodes all communication that occurs between the server and the client.
 * This method is used in all the communication classes that need to send or receive information
 * from the server.
 *
 * @return the body of a get request to the server.
 * @throws Exception if communication has unsupported encoding mechanism.
 */
public class GeneralMethods {

    /**
     * This method is to encode all communication across the data stream.
     *
     * @param params
     * @return Encoded parameters as string
     * @throws UnsupportedEncodingException
     */
    public static String encodeCommunication(String params) throws UnsupportedEncodingException {
        params = URLEncoder.encode(params, StandardCharsets.UTF_8.toString());
        params = params.replaceAll("%26", "&");
        params = params.replaceAll("%3D", "=");
        return params;
    }

    /**
     * creates a custom alert message.
     *
     * @param title
     * @param content
     * @param owner
     * @param type
     * @return
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
