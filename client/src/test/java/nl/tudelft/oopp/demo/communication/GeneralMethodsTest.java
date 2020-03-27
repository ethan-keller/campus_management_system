package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.Test;

class GeneralMethodsTest {

    @Test
    void encodeCommunicationTest() throws UnsupportedEncodingException {
        String input1 = "er staat een paard in de gang?!";
        String input2 = "It = a song & it is good";
        String res1 = "er+staat+een+paard+in+de+gang%3F%21";
        String res2 = "It+=+a+song+&+it+is+good";

        assertEquals(res1, GeneralMethods.encodeCommunication(input1));
        assertEquals(res2, GeneralMethods.encodeCommunication(input2));
    }

    /*
    @Test
    void createAlertTest(){
        Stage stage = new Stage();

        Alert alert1 = new Alert(Alert.AlertType.WARNING);
        alert1.setTitle("testTitle");
        alert1.setContentText("This is a content text");
        alert1.initOwner(stage);
        alert1.initModality(Modality.WINDOW_MODAL);

        Alert alert2 = GeneralMethods.createAlert("testTitle", "This is a content text",
                stage, Alert.AlertType.WARNING);

        assertEquals(alert1, alert2);
    }
    */
}