package uk.ac.aber.cs21120.museum.interfaces;

import java.util.Set;

/**
 * This interface represents the room layout of a museum.
 */
public interface IMuseum {

    // task 1

    /**
     * Return a set of the names of all rooms in the museum
     * @return set of room names
     */
    public Set<String> getRoomNames();

    /**
     * Return the maximum size of the room with the given name.
     * @param roomName
     * @return maximum size of the room
     * @throws IllegalArgumentException if there is no room with the given name
     */
    public int getTotalMaxSize(String roomName);

    /**
     * Return the total visitor count for the room with the given name.
     * @param roomName
     * @return total visitor count for the room
     * @throws IllegalArgumentException if there is no room with the given name
     */
    public int getTotalMaxVisitors(String roomName);

    /**
     * Return the width of the connection between two rooms in the given direction.
     * If there is no connection, return zero.
     * @param room1
     * @param room2
     * @param direction - e.g. "north", "south", "east", "west" but should also accept upper case e.g. "NORTH"
     * @return width of the connection, or zero if there is no connection
     * @throws IllegalArgumentException if either room does not exist
     */
    int getConnection(String room1, String room2, String direction);

    /**
     * See the assignment brief for more details of this task.
     */
    void walk();





    // task 2 - see the assignment brief for more details
    String getMermaid();




    // task 3 - see the assignment brief for more details
    boolean hasValidPath(String room1, String room2, int maxSize);




    // task 4 - see the assignment brief for more details
    boolean allocateExhibits();
    String getAllocation(String roomName);
}
