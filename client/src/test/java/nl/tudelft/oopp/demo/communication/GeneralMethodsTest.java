package nl.tudelft.oopp.demo.communication;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

class GeneralMethodsTest {

    @org.junit.jupiter.api.Test
    void encodeCommunication() throws UnsupportedEncodingException {
        String input1 = "er staat een paard in de gang?!";
        String input2 = "It = a song & it is good";
        String res1 = "er+staat+een+paard+in+de+gang%3F%21";
        String res2 = "It+=+a+song+&+it+is+good";

        assertEquals(res1, GeneralMethods.encodeCommunication(input1));
        assertEquals(res2, GeneralMethods.encodeCommunication(input2));
    }
}