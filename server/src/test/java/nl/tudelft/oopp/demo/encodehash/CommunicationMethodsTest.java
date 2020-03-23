package nl.tudelft.oopp.demo.encodehash;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

class CommunicationMethodsTest {

    @Test
    void decodeCommunication() throws UnsupportedEncodingException {
        String encoded = "drebbelweg+aan+zee%21";
        String expected = "drebbelweg aan zee!";
        assertEquals(expected, CommunicationMethods.decodeCommunication(encoded));
    }
}