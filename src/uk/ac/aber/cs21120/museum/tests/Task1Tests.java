package uk.ac.aber.cs21120.museum.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs21120.museum.interfaces.IMuseum;
import uk.ac.aber.cs21120.museum.solution.Museum;

/**
 * This code tests the basics of the room layout and connection code.
 *
 * Note - it does NOT test the actual layout of the museum, just that the code works.
 * You will need to check the layout of the museum yourself. I would strongly recommend
 * writing your own tests to check that the layout is correct.
 */

public class Task1Tests {
    @Test
    /**
     * Test that we can create a Museum object without crashing
     */
    void testConstruction() {
        IMuseum museum = new Museum();
        // this should never happen - a constructor will always return an object or throw an exception.
        // If it throws an exception, your code has a bug somewhere in the constructor.
        Assertions.assertNotNull(museum);
    }

    /**
     * Test that at least one room has been loaded.
     */
    @Test
    void testRoomsExist(){
        IMuseum museum = new Museum();
        Assertions.assertNotNull(museum.getRoomNames(), "getRoomNames() returned null");
        Assertions.assertFalse(museum.getRoomNames().isEmpty(), "getRoomNames() returned an empty set" );
    }

    /**
     * Test that room E1 has the correct details - this is a room with a simple name; it's two letters.
     * Room E1 should exist, and have max size 5 and max visitors 5.
     * Of course, this test will pass even if max size and visitors are the wrong way round!
     */
    @Test
    void testOneRoomDetails(){
        IMuseum m = new Museum();
        Assertions.assertTrue(m.getRoomNames().contains("E1"), "Room E1 does not exist");
        Assertions.assertEquals(5, m.getTotalMaxSize("E1"), "Room E1 has wrong max size");
        Assertions.assertEquals(5, m.getTotalMaxVisitors("E1"), "Room E1 has wrong max visitors");
    }

    /**
     * Test that the atrium has the correct details.
     * This will show up more errors in the room details - this room has a multi-letter name, and has different
     * max size and max visitors values.
     */
    @Test
    void testAtriumDetails(){
        IMuseum m = new Museum();
        Assertions.assertTrue(m.getRoomNames().contains("Atrium"), "Room Atrium does not exist");
        Assertions.assertEquals(0, m.getTotalMaxSize("Atrium"), "Room Atrium has wrong max size");
        Assertions.assertEquals(10, m.getTotalMaxVisitors("Atrium"), "Room Atrium has wrong max visitors");
    }

    /**
     * Test that the main hall has the correct details. This room has a space in the name.
     */
    @Test
    void testMainHallDetails(){
        IMuseum m = new Museum();
        Assertions.assertTrue(m.getRoomNames().contains("Main Hall"), "Room Main Hall does not exist");
        Assertions.assertEquals(20, m.getTotalMaxSize("Main Hall"), "Room Main Hall has wrong max size");
        Assertions.assertEquals(10, m.getTotalMaxVisitors("Main Hall"), "Room Main Hall has wrong max visitors");
    }

    /**
     * We access directions by strings - test that an invalid direction throws an exception.
     */
    @Test
    void testInvalidDirection(){
        IMuseum m = new Museum();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            m.getConnection("Main Hall", "Atrium", "invalid direction");
        });
    }

    /**
     * Test that getting directions by strings works for valid directions.
     * This should work in both upper and lower case! Remember, you can change
     * the case of a string using toUpperCase() and toLowerCase() methods.
     */
    @Test
    void testValidDirections(){
        IMuseum m = new Museum();
        m.getConnection("Main Hall","Atrium", "east");
        m.getConnection("Main Hall","Atrium", "west");
        m.getConnection("Main Hall","Atrium", "north");
        m.getConnection("Main Hall","Atrium", "south");

        m.getConnection("Main Hall","Atrium", "EAST");
        m.getConnection("Main Hall","Atrium", "WEST");
        m.getConnection("Main Hall","Atrium", "NORTH");
        m.getConnection("Main Hall","Atrium", "SOUTH");
    }

    /**
     * Test that asking for a non-existent room throws the correct exception with those methods that
     * take a room name as a parameter.
     */
    @Test
    void testNonExistentRoom(){
        IMuseum m = new Museum();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            m.getTotalMaxSize("This room does not exist");
        }, "getTotalMaxSize() did not throw IllegalArgumentException for non-existent room");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            m.getTotalMaxVisitors("This room does not exist");
        }, "getTotalMaxVisitors() did not throw IllegalArgumentException for non-existent room");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            m.getConnection("Main Hall", "This room does not exist", "north");
        }, "getConnection() did not throw IllegalArgumentException for non-existent 'from' room");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            m.getConnection("This room does not exist", "Main Hall", "north");
        }, "getConnection() did not throw IllegalArgumentException for non-existent 'to' room");
    }

    /**
     * Test that the connection between Main Hall and Atrium is correct.
     * This is a unidirection test, we'll test the reverse direction in another test.
     */
    @Test
    void testConnectionSingle() {
        IMuseum m = new Museum();
        int width = m.getConnection("Main Hall", "Atrium", "east");
        Assertions.assertEquals(4, width, "Connection width between Main Hall and Atrium is wrong");
    }

    /**
     * Now test the reverse direction - there should be a connection both ways.
     */
    @Test
    void testConnectionReverse() {
        IMuseum m = new Museum();
        int width = m.getConnection("Atrium", "Main Hall", "west");
        Assertions.assertEquals(4, width, "Connection width between Atrium and Main Hall is wrong");
    }
}
