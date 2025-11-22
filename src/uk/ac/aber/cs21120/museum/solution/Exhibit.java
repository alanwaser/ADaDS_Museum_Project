package uk.ac.aber.cs21120.museum.solution;
//represent exhibits and all attributes
public class Exhibit {

    private String name;
    private String theme;
    private int size;
    private int visitors;

    /**
     * Constructor
     * @param name of exhibit
     * @param theme of exhibit
     * @param size of the exhibit when placed in a room
     * @param visitors expected in room
     */
    Exhibit(String name, String theme, int size, int visitors) {
        this.name = name;
        this.theme = theme;
        this.size = size;
        this.visitors = visitors;
    }

    /**
     *
     * @return name of exhibit
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return theme of the exhibit
     */
    public String getTheme() {
        return theme;
    }

    /**
     *
     * @return the size of the exhibit
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * @return max visitors in the room alongside the exhibit
     */
    public int getVisitors() {
        return visitors;
    }

}
