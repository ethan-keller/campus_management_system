package nl.tudelft.oopp.demo.encode_hash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashingTest {

    @org.junit.jupiter.api.Test
    void hashIt() {
        String password1 = "Hello_World!";
        String wanted1 = "8bd925676ad5b48cf7a48d028584852b2d17f2a5d490e9de14af80f01cbfd88a";
        String password2 = "Pizza";
        String password3 = "pizza";
        String wanted2 = "f12958816a49adfa2c6c8de8dd2144c163e92c5e375de964d533187c7d236c36";
        assertEquals(wanted1, Hashing.hashIt(password1));
        assertEquals(wanted2, Hashing.hashIt(password2));
        assertNotEquals(wanted2, Hashing.hashIt(password3));
    }
}