package uk.ac.aber.cs21120.museum.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs21120.museum.interfaces.IMuseum;
import uk.ac.aber.cs21120.museum.solution.Museum;

public class Task2Tests {
    @Test
    void testMermaidStringNotNullOrEmpty(){
        IMuseum m = new Museum();
        String s = m.getMermaid();
        Assertions.assertNotNull(s, "getMermaid() returned null");
        Assertions.assertFalse(s.isEmpty(), "getMermaid() returned an empty string");
    }

    @Test
    void testMermaidStringHasHeader(){
        IMuseum m = new Museum();
        String s = m.getMermaid().strip();
        Assertions.assertTrue(s.startsWith("graph LR") || s.startsWith("graph TD"),
                "Mermaid string must start with 'graph LR' or 'graph TD'");
    }



        @Test
    void testMermaidStringContainsRooms(){
        IMuseum m = new Museum();
        String s = m.getMermaid();
        for(String roomName : m.getRoomNames()){
            Assertions.assertTrue(s.contains(roomName), "getMermaid() output does not contain room name: " + roomName);
        }
    }
}
