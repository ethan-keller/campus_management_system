package nl.tudelft.oopp.demo.communication;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneralMethodsTest {

    @org.junit.jupiter.api.Test
    void encodeCommunication() {
        String input1 = "er staat een paard in de gang?!";
        String input2 = "It = a song & it is good";
        String res1 = "eer+staat+een+paard+in+gang%3F%21";
        String res2 = "It+=+a+song+&+it+is+good";
    }
}