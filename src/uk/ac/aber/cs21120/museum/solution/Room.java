package uk.ac.aber.cs21120.museum.solution;

import java.util.HashMap;

//represents a room and its attributes
public class Room {

    private String name;
    private int maxSize;
    private int maxVisitors;
    private HashMap<String, Connection> connections = new HashMap<>();    //map of connections to the neighbouring rooms of current room

    /**
     * constructur
     * @param name - name of room
     * @param maxSize - the maxiimum exhibit size the room can contain
     * @param maxVisitors - the maximum visitor size the room can contain
     */
    public Room(String name, int maxSize, int maxVisitors) {
        this.name = name;
        this.maxSize = maxSize;
        this.maxVisitors = maxVisitors;
    }

    /**
     * connections from current room to neighbouring
     * @param neighbourName - name of neighbouring room
     * @param doorWidth - width between doors of neighbouring room and current room
     * @param direction - if neighboruing room is North,East,South,West compared to current room
     */
    public void addConnection (String neighbourName, int doorWidth, String direction) {
        this.connections.put(neighbourName, new Connection(neighbourName,doorWidth,direction));
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return size
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     *
     * @return max visitors
     */
    public int getMaxVisitors() {
        return maxVisitors;
    }

    /**
     *
     * @return connections to neighbouring rooms
     */
    public HashMap<String, Connection> getConnection() {
        return connections;
    }
}
