package uk.ac.aber.cs21120.museum.solution;
import uk.ac.aber.cs21120.museum.interfaces.IMuseum;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// Makes rooms and the map for the museum along with allocation of the exhibits
public class Museum implements IMuseum {

    //list all exhibits loaded from csv file
    private ArrayList<Exhibit> exhibits;
    private HashMap<String,Room> rooms;//map of room names and objects() shows museum layout
    private HashMap<String, ArrayList<Exhibit>> exhibitsHashMap ;//tracks which exhibts are in which rooms

    /**
     * Constructor
     */
    public Museum() {
        this.rooms = new HashMap<>();
        this.exhibits = new ArrayList<>();
        this.exhibitsHashMap = new HashMap<>();
        String line;

        //loads the csv file
        try{
            BufferedReader reader = new BufferedReader(new FileReader("data/exhibits.csv"));

            //reads every line of csv file
            while((line = reader.readLine()) != null){
                String[] split = line.split(",");//splits up the info from csv file to name,theme,size,visitors
                String name = split[0];
                String theme = split[1];
                int size = Integer.parseInt(split[2]);
                int visitors = Integer.parseInt(split[3]);

                //creates new exhibit object and adds to list
                exhibits.add(new Exhibit(name, theme, size, visitors));
            }
        } catch (IOException e) {
            System.out.println("Error reading file - data/exhibits.csv" + e.getMessage());
        }

        //adds main hall
        Room mainHall = new Room ("Main Hall",20,10);
        mainHall.addConnection("North Room", 3,"North");
        mainHall.addConnection("Second Hall", 4,"West");
        mainHall.addConnection("Atrium", 4,"East");
        this.rooms.put("Main Hall",mainHall);

        //adds secondHall
        Room secondHall = new Room ("Second Hall",20,10);
        secondHall.addConnection("Main Hall", 4,"East");
        secondHall.addConnection("South Room", 4,"South");
        this.rooms.put("Second Hall",secondHall);

        //adds southRoom
        Room southRoom = new Room ("South Room",15,15);
        southRoom.addConnection("Second Hall", 4,"North");
        this.rooms.put("South Room",southRoom);

        //adds northRoom
        Room northRoom = new Room ("North Room",10,10);
        northRoom.addConnection("Main Hall", 3,"South");
        northRoom.addConnection("Exhibition Hall", 2,"East");
        this.rooms.put("North Room",northRoom);

        //adds atrium
        Room atrium= new Room ("Atrium",0,10);
        atrium.addConnection("Main Hall", 4,"West");
        atrium.addConnection("Long Room", 2,"East");
        atrium.addConnection("E1", 2,"South");
        this.rooms.put("Atrium",atrium);

        //adds longRoom
        Room longRoom = new Room ("Long Room",10,10);
        longRoom.addConnection("Exhibition Hall", 5,"North");
        longRoom.addConnection("E2", 2,"South");
        longRoom.addConnection("Atrium", 2,"West");
        this.rooms.put("Long Room",longRoom);

        //adds exhibitionHall
        Room exhibitionHall = new Room ("Exhibition Hall",20,20);
        exhibitionHall.addConnection("Long Room", 5,"South");
        exhibitionHall.addConnection("North Room", 2,"West");
        this.rooms.put("Exhibition Hall",exhibitionHall);

        //adds e1
        Room e1= new Room ("E1",5,5);
        e1.addConnection("Atrium", 2,"North");
        e1.addConnection("E2", 1,"East");
        this.rooms.put("E1",e1);

        //adds e2
        Room e2= new Room ("E2",4,4);
        e2.addConnection("Long Room", 2,"North");
        e2.addConnection("E1", 1,"West");
        e2.addConnection("E3", 1,"East");
        this.rooms.put("E2",e2);

        //adds e3
        Room e3= new Room ("E3",5,5);
        e3.addConnection("E2", 1,"West");
        this.rooms.put("E3",e3);

        for(Map.Entry<String,Room> entry : this.rooms.entrySet()){
            this.exhibitsHashMap.put(entry.getKey(),new ArrayList<>());// to make sure that the hashmap has something so it dsoent return a null when i search in allocation method
        }


    }

    /**
     *
     * @return all room names in the museum
     */
    @Override
    public Set<String> getRoomNames() {
        return rooms.keySet();
    }

    /**
     * maximum size that can fit in one room found
     * @param roomName the room to check the maxsize
     * @return max size
     */
    @Override
    public int getTotalMaxSize(String roomName) {
        if (rooms.get(roomName) == null){
            throw new IllegalArgumentException("Room does not exist");
        }else {
            return rooms.get(roomName).getMaxSize();
        }
    }

    /**
     * gets the max visitor cap for one room
     * @param roomName used to find max visitors of that room
     * @return max visitors
     */
    @Override
    public int getTotalMaxVisitors(String roomName) {
        if(rooms.get(roomName) == null){
            throw new IllegalArgumentException("Room does not exist");
        }else{
            return rooms.get(roomName).getMaxVisitors();
        }
    }

    /**
     *connection between 2 room swith the direction
     * @param room1 start
     * @param room2 destination
     * @param direction - e.g. "north", "south", "east", "west" but should also accept upper case e.g. "NORTH"
     * @return doorwidth if connection is true, if false 0
     */
    @Override
    public int getConnection(String room1, String room2, String direction) {
        String lowerDirection = direction.toLowerCase();
        if (lowerDirection.equals("north") || lowerDirection.equals("south") || lowerDirection.equals("east") || lowerDirection.equals("west")) {
            if (rooms.get(room1) == null || rooms.get(room2) == null) {
                throw new IllegalArgumentException("Room does not exist");
            } else {
                Room r1 = rooms.get(room1);
                HashMap<String, Connection> r1connection = r1.getConnection(); //gets the whole o hashmap
                Connection r1conn = r1connection.get(room2);//checks connection to second room
                if (r1conn == null) {
                    return 0;//no connection exists
                } else {
                    if (r1conn.getDirection().toLowerCase().equals(lowerDirection)) {
                        return r1conn.getRoomWidth();
                    } else {
                        return 0;
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Direction does not exist");
        }
    }

    /**
     * Allows User to walk through the museum
     */
    @Override
    public void walk() {
        Scanner scanner = new Scanner(System.in);
        String currentRoom = "Main Hall";
        while (true) {
            //dosplays curretn room and gets user input
            System.out.println("\nCurrent Room:  "+currentRoom);
            System.out.println("What direction do you want to walk? * North, East, South, West *  'Exit' to quit");
            String input = scanner.nextLine();
            String lowerDirection = input.toLowerCase();
            if (lowerDirection.equals("exit")) {
                System.out.println("End of program");
                break;
            }
            Room currentRoom1 = rooms.get(currentRoom);
            HashMap<String, Connection> currentRoom1connection = currentRoom1.getConnection();//gets connections of current room

            boolean moveOccurred = false; //to see if a move happened

            for (Map.Entry<String, Connection> entry : currentRoom1connection.entrySet()){
                Connection currentConnection = entry.getValue();
                String destination = entry.getKey();

                //if direction matches then move towards that direction
                if (currentConnection.getDirection().toLowerCase().equals(lowerDirection)) {
                    currentRoom = destination;
                    moveOccurred = true;
                    break;
                }
            }
            if (!moveOccurred) {
                System.out.println("Cant go this way");
            }
        }
    }

    /**
     * gmenerates mermaid layout
     * @return String builder with the mermaid diagram
     */
    @Override
    public String getMermaid() {
        StringBuilder mermaid = new StringBuilder("graph LR");
        mermaid.append("\n");

        // adds the nodes
        for (Map.Entry <String, Room > entry : rooms.entrySet()) {
            String displayName = entry.getKey();
            String identifier = displayName.replaceAll(" ", "");
            mermaid.append(identifier);
            mermaid.append("[");
            mermaid.append(displayName);
            mermaid.append("]");
            mermaid.append("\n");
        }

        Set<String> addedConnections = new HashSet<String>();

        //makes sure there are no duplicates between edges
        for (Map.Entry <String, Room > entry : rooms.entrySet()) {
            String r1name = entry.getKey();
            String r1identifier = r1name.replaceAll(" ", "");
            Room r1 = entry.getValue();

            HashMap<String, Connection> roomConnection = r1.getConnection();

            //adds edges
            for (Map.Entry <String,Connection> connectionEntry : roomConnection.entrySet()){
                String r2name = connectionEntry.getKey();
                Connection currentConnection = connectionEntry.getValue();
                String r2connectionIdentifier = r2name.replaceAll(" ", "");
                int connectionWidth = currentConnection.getRoomWidth();

                String forwardConnection = r1name + " " + r2name;
                String backwardConnection = r2name + " " + r1name;

                if(!addedConnections.contains(forwardConnection) && !addedConnections.contains(backwardConnection)){
                    mermaid.append(r1identifier);
                    mermaid.append(" <-- ");
                    mermaid.append(connectionWidth);
                    mermaid.append(" --> ");
                    mermaid.append(r2connectionIdentifier);
                    mermaid.append("\n");

                    addedConnections.add(forwardConnection);
                }
            }
        }
        return mermaid.toString();
    }


    /**
     *Use BFS to pathfind between 2 rooms in museum
     * @param room1 starting
     * @param room2 destination
     * @param maxSize doorwidth required
     * @return true if valid path exists and false if it dosent
     */
    @Override
    public boolean hasValidPath(String room1, String room2, int maxSize) {
        if (rooms.get(room1) == null || rooms.get(room2) == null) {
            throw new IllegalArgumentException("Room does not exist");
        }else if (room1.equals(room2)) {
            return true;
        }
        //bfs
        Queue <String> explore = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();

        explore.add(room1);
        visited.add(room1);
        while (!explore.isEmpty()) {
            String currentRoom1 = explore.remove();
            Room currentRoom = rooms.get(currentRoom1);

            HashMap<String, Connection> currentRoomconnection = currentRoom.getConnection();

            for (Map.Entry <String,Connection> entry : currentRoomconnection.entrySet()) {
                String neighbour = entry.getKey();
                Connection currentConnection = entry.getValue();

                if (currentConnection.getRoomWidth() >= maxSize && !visited.contains(neighbour)) {
                    if (neighbour.equals(room2)) {
                        return true;
                    }
                    explore.add(neighbour);//add to bfs queue
                    visited.add(neighbour);
                }
            }
        }
        return false;
    }

    /**
     * checks if an exhibiot can be placed in a room, looks at all the rules
     * @param exhibit echibit to check
     * @param roomName room that exhibit goes in
     * @return true if exhibit can be placed and false if it cant
     */
    public boolean exhibitCanFit(Exhibit exhibit,String roomName) {
        if (rooms.get(roomName) == null) {
            throw new IllegalArgumentException("Room does not exist");
        }
        Room currentRoom = rooms.get(roomName);

        ArrayList<Exhibit> exhibitsInRoom = exhibitsHashMap.get(roomName);

        boolean theme = false;
        boolean sizeVisitor = false;
        boolean validPath = false;

        //checks size and visitors current
        int usedSize = 0;
        int usedVisitors = 0;
        for (int i = 0; i < exhibitsInRoom.size(); i++) {
            Exhibit currentExhibit = exhibitsInRoom.get(i);
            usedSize += currentExhibit.getSize();
            usedVisitors += currentExhibit.getVisitors();
        }

        //checks if adding exhibit would add too much size or visitors
        if (usedSize + exhibit.getSize() <= currentRoom.getMaxSize() && usedVisitors + exhibit.getVisitors() <= currentRoom.getMaxVisitors()) {
            sizeVisitor = true;
        }else {
            sizeVisitor = false;
        }

        //makes sure there is theme consistancy
        if (exhibitsInRoom.size() > 0) {
            Exhibit firstExhibit = exhibitsInRoom.get(0);
            String exhibitTheme = firstExhibit.getTheme();
            String currentExhibitTheme = exhibit.getTheme();

            if (exhibitTheme.equals(currentExhibitTheme)) {
                theme = true;
            } else {
                theme = false;
            }
        }else{
            theme = true;
        }

        //checks validpath, theme,size, visitors is all valid
        if (hasValidPath("Main Hall",roomName, exhibit.getSize()) == true && theme == true && sizeVisitor == true) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * Helper method to find theme relation within rooms between exhibits
     * @param exhibitName name of exhibit
     * @return theme of exhibit, null if not found
     */
    public String getExhibitTheme(String exhibitName) {
        for (Exhibit exhibit : exhibits) {
            if (exhibit.getName().equals(exhibitName)) {
                return exhibit.getTheme();
            }
        }
        return null;
    }

    /**
     * use exhibit here to call size in test
     * @param exhibitName name of exhibit
     * @return Exhibit object, null if not found
     */
    public Exhibit getExhibit(String exhibitName) {
        for (Exhibit exhibit : exhibits) {
            if (exhibit.getName().equals(exhibitName)) {
                return exhibit;
            }
        }
        return null;
    }

    /**
     * backtracking to allocate exhibit to rooms
     * @param exhibitIndex curretn exhibit being alloacted
     * @return true if successful, false if not
     */
    public boolean recursiveAllocation(int exhibitIndex){

        //allocate exhibits correctly
        if (exhibitIndex == exhibits.size()) {
            return true;
        }

        Exhibit currentExhibit = exhibits.get(exhibitIndex);

        for(Map.Entry<String,Room> entry: rooms.entrySet()) {
            String currentRoom = entry.getKey();

            if(exhibitCanFit(currentExhibit,currentRoom)){

                ArrayList<Exhibit> list = exhibitsHashMap.get(currentRoom);
                list.add(currentExhibit);

                //recursive allocation
                if(recursiveAllocation(exhibitIndex+1)){
                    return true;
                }

                list.remove(currentExhibit);
            }
        }
        return false;
    }


    /**
     * Allocate all exhibits useing backtracking
     * @return true if all exhibits alloacted, false if not
     */
    @Override
    public boolean allocateExhibits() {
        //clear previous allocations
        for (Map.Entry <String, ArrayList<Exhibit> > entry : exhibitsHashMap.entrySet()) {
            ArrayList<Exhibit> clear = entry.getValue();
            clear.clear();
        }

        // start recursive alloacation from first exhibit
        boolean recursionSuccessful = recursiveAllocation(0);

        return recursionSuccessful;
    }

    /**
     * get comma seperated list of exhibits within rooms
     * @param roomName room to check
     * @return commas seperated room names or empty if it has no exhibits
     */
    @Override
    public String getAllocation(String roomName) {
        //checks room exists
        if (rooms.get(roomName) == null) {
            throw new IllegalArgumentException("Room does not exist");
        }

        ArrayList<Exhibit> exhibitedRooms = exhibitsHashMap.get(roomName);
        //if there is nothing inside return ""
        if (exhibitedRooms == null || exhibitedRooms.size() == 0) {
            return "";
        }

        //build comma seperted list of exhibits
        StringBuilder allocation = new StringBuilder();

        for (int i = 0; i < exhibitedRooms.size(); i++) {
            Exhibit roomExhibit = exhibitedRooms.get(i);
            allocation.append(roomExhibit.getName());

            if (i < exhibitedRooms.size() - 1) {
                allocation.append(",");
            }
        }
        return allocation.toString();
    }
}


