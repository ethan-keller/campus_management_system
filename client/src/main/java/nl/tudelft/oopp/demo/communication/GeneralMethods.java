package nl.tudelft.oopp.demo.communication;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Window;
import org.controlsfx.control.RangeSlider;

import java.io.BufferedWriter;
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

    public static void setSliderDefaultCSS(RangeSlider rs, BufferedWriter bw, String css) {
        try {
            rs.getStylesheets().add(css);
            bw.write(".track {\n" +
                    "\t-fx-background-color: linear-gradient(to right, #f5fdff 0%, #f5fdff 100%);\n" +
                    "    -fx-background-insets: 0 0 -1 0, 0, 1;\n" +
                    "    -fx-background-radius: 0.25em, 0.25em, 0.166667em; /* 3 3 2 */\n" +
                    "    -fx-padding: 0.25em; /* 3 */\n" +
                    "}\n" +
                    "\n" +
                    ".range-bar {\n" +
                    "    -fx-background-color: rgba(0,0,0,0.5);\n" +
                    "}");
            // flush and close writer
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
