package uk.ac.aber.cs21120.museum.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs21120.museum.interfaces.IMuseum;
import uk.ac.aber.cs21120.museum.solution.Museum;

public class Task3Tests {
    @Test
    public void testAdjacentRoomsFits(){
        IMuseum m = new Museum();
        Assertions.assertTrue(m.hasValidPath("North Room", "Exhibition Hall", 1));
        Assertions.assertTrue(m.hasValidPath("Exhibition Hall", "North Room", 2));

        Assertions.assertTrue(m.hasValidPath("Main Hall", "Second Hall", 4));
        Assertions.assertTrue(m.hasValidPath("Second Hall", "South Room", 4));

        Assertions.assertTrue(m.hasValidPath("Atrium", "Long Room", 1));

        // this is an important test - while E2 is next to E1, the door between them is only 1 wide.
        // However, things of width 2 will fit going the long way (via the Long Room and Atrium)

        Assertions.assertTrue(m.hasValidPath("E1", "E2", 2)); // the long way!

    }

    @Test
    public void testAdjacentRoomNoFit(){
        IMuseum m = new Museum();
        Assertions.assertFalse(m.hasValidPath("North Room", "Exhibition Hall", 3));
        Assertions.assertFalse(m.hasValidPath("Exhibition Hall", "North Room", 4));

        Assertions.assertFalse(m.hasValidPath("Main Hall", "Second Hall", 5));
        Assertions.assertFalse(m.hasValidPath("Second Hall", "South Room", 5));
        Assertions.assertFalse(m.hasValidPath("Atrium", "Long Room", 10));
        // see above - while E1 and E2 are next to each other, items of width 2 have to go via the Long Room and
        // Atrium. Bigger items have no valid route.
        Assertions.assertFalse(m.hasValidPath("E1", "E2", 3));
    }

    @Test
    public void testNonAdjacentRoomsFit(){
        IMuseum m = new Museum();

        Assertions.assertTrue(m.hasValidPath("E2", "South Room", 1));
        Assertions.assertTrue(m.hasValidPath("South Room", "E2", 2));

        Assertions.assertTrue(m.hasValidPath("E1", "Exhibition Hall", 1));
        Assertions.assertTrue(m.hasValidPath("Exhibition Hall", "E1", 2));
    }

    @Test
    public void testNonAdjacentRoomsNoFit(){
        IMuseum m = new Museum();

        Assertions.assertFalse(m.hasValidPath("E3", "South Room", 3));
        Assertions.assertFalse(m.hasValidPath("South Room", "E3", 2));

        Assertions.assertFalse(m.hasValidPath("E1", "Exhibition Hall", 3));
        Assertions.assertFalse(m.hasValidPath("Exhibition Hall", "E1", 10));
    }
}
