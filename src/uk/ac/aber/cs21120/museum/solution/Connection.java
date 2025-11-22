package uk.ac.aber.cs21120.museum.solution;
//represents connections with all properties and attributes
public class Connection {

    private String roomName;
    private int roomWidth;
    private String direction;

    /**
     * constructor
     * @param roomName name of neighbouring room
     * @param roomWidth of both room and neighboring room
     * @param direction of neighbouring room compared to room that it is being compared to
     */
    public Connection(String roomName, int roomWidth, String direction) {
        this.roomName = roomName;
        this.roomWidth = roomWidth;
        this.direction = direction;
    }

    /**
     *
     * @return room name
     */
    public String getRoomName() {
        return roomName;
    }


    /**
     *
     * @return room width
     */
    public int getRoomWidth() {
        return roomWidth;
    }

    /**
     *
     * @return room direction - north,east,south,west
     */
    public String getDirection() {
        return direction;
    }
}
