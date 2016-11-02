/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author waterbucket
 */
public class ProcessHandler {

    static class ObjectCreator {
        

        List<Item> items = new ArrayList<>();

        public void loadItems() throws FileNotFoundException {
            File file = new File("objectList.txt");
            Scanner lines = new Scanner(file) //Wrap a scanner around the file
                    .useDelimiter("\n"); //And make it's .next() method return 1 line

            //classic method 
            while (lines.hasNext()) {
                String line = lines.next();
                //check it in the file.
                //would be better if it was global and configurable
                try {
                    Item item = new Item(line);
                    this.items.add(item);
                    System.err.println(this.items);
                } catch (Exception e) {
                    //oh no, a malformed item! What will I do?
                    //I will inform the user there was a problem in the file!
                    System.err.println(e.getMessage());
                    //Because we already created an informative message in the Item
                    //constructor
                    //We still want to add any other valid items, so we are not gonna quit
                }
            }
            //Done! Now, remove the comments and see how simple this is.

            //lambda method, completely identical to the part above
            //---------UNCOMMENT
//        lines.forEachRemaining((String t) -> { //for every string
//            try { //try to add a new item, made up by splitting the line on a ':'
//                ObjectCreator.this.items.add(new Item(t.split(":")));
//            } catch (Exception ex) {
//                //if error occurs, say why.
//                System.err.println(ex.getMessage());
//            }
//        });
            //---------UNCOMMENT
            //Done!
            //And finally show our sweet results.
//        this.printList();
        }

        private void printList() {
            this.items.stream().forEach(System.out::println);
        }

       
    }
}