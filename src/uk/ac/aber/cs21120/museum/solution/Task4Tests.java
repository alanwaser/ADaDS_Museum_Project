package uk.ac.aber.cs21120.museum.solution;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;




public class Task4Tests {

    //test to see if the alloaction happens

    @Test
    public void testallocateExhibits() {
        Museum museum = new Museum();// New museum made
        boolean allocated = museum.allocateExhibits(); // attempt to allocate every exhibit to the rooms - returns true
        Assertions.assertTrue(allocated, "Allocation should succeed");

        //loop to check allocations of exhibits
        for (String roomName: museum.getRoomNames()) {
            String allocation = museum.getAllocation(roomName);

            //makes sure allocation occurs in rooms
            Assertions.assertNotNull(allocation, "Allocation should not be null");
        }

    }

    @Test

    //check to see if allocations are not empty and properly formatted
    public void testMethodName(){
        Museum museum = new Museum();
        museum.allocateExhibits();

        String allocation = museum.getAllocation("Main Hall");

        //verifys Main hall has exhibits
        Assertions.assertFalse(allocation.isEmpty(), "Should contain exhibits");
        //verifys allocation is not null
        Assertions.assertNotNull(allocation, "Allocation shouldnt be null");

    }


    @Test

    //test to make sure comma seperated values and trimming occured properly in all exhibits
    public void testAllocationCommas(){
        Museum museum = new Museum();
        museum.allocateExhibits();

        String allocation = museum.getAllocation("Main Hall");

        //verify main hall has exhibits
        Assertions.assertFalse(allocation.isEmpty(), "Should contain exhibits");

        if (!allocation.isEmpty()){
            String[] exhibits = allocation.split(",");
            for (String exhibit : exhibits){
                String exhibitTrim = exhibit.trim();

                //verifys that after trimming, the exhibits have proper comma seperation
                Assertions.assertFalse(exhibitTrim.isEmpty(), "Should contain ',' ");
            }
        }
    }

    @Test
    //Tests to see if rooms have same themes
    public void testExhibitTheme(){
        Museum museum = new Museum();
        museum.allocateExhibits();

        //loop to check allocations of exhibits
        for(String roomName: museum.getRoomNames()){
            String allocation = museum.getAllocation(roomName);

            //makes sure rooms are not empty and there are exhibits in them
            if (!allocation.isEmpty()){
                String[] exhibits = allocation.split(",");

                //checks there is more than 1 exhibit so as haveing a single exhibit wouldnt require a check
                if (exhibits.length >= 1){
                    String firstTheme = null;

                    //checks each erxhibit in 1 room
                    for (String exhibitName : exhibits){
                        String currentTheme = museum.getExhibitTheme(exhibitName.trim());

                        //if there is an exhibit then change the theme to the first theme - which should be the only theme in the room
                        if (firstTheme == null){
                            firstTheme = currentTheme;
                        }else {
                            //for all other echibits in the same room checks to see if the themes are the same
                            Assertions.assertEquals(firstTheme, currentTheme, "First theme should match");
                        }
                    }
                }
            }
        }
    }

    @Test
    //test to see if the rooms with exhibits have a valid path back to the main hall
    public void testValidPath(){
        Museum museum = new Museum();
        museum.allocateExhibits();//allocate exhibits to the rooms

        //loop to check allocations of exhibits
        for (String roomName: museum.getRoomNames()){
            String allocation = museum.getAllocation(roomName);
            if (!allocation.isEmpty()){//only looks at rooms with exhibits
                String[] exhibits = allocation.split(",");//makes sure the exhibits are seperates useing ","

                //loops through each exhibit in one room
                for (String exhibitName : exhibits){
                    Exhibit exhibit = museum.getExhibit(exhibitName.trim());// gets the exhibit objet
                    //Checks a valid path to the main hall is existant and valid
                    Assertions.assertTrue(museum.hasValidPath(roomName,"Main Hall",exhibit.getSize()), "Exhibit has valid path to Main Hall");
                }
            }
        }
    }
}
