/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectcreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author aroyal1
 */
public class ObjectCreator implements Runnable {

    static ObjectCreator instance;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        instance = new ObjectCreator();
        //instance.run();
        try {
            //Testing loadItems()
            instance.loadItems();
        } catch (IOException ex) {
            System.err.println("Error occured in loadItems: " + ex.getMessage());
        }
    }

    ArrayList<Item> items = new ArrayList<>();

    public void loadItems() throws FileNotFoundException {
        File file = new File("objectList.txt");
        Scanner lines = new Scanner(file) //Wrap a scanner around the file
            .useDelimiter("\n"); //And make it's .next() method return 1 line

        //classic method 
        while (lines.hasNext()) {
            String line = lines.next();
            String[] entries = line.split(":"); //use a delimeter for every line.
            //check it in the file.
            //would be better if it was global and configurable
            try {
                Item item = new Item(entries);
                this.items.add(item);
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
        this.printList();

    }

    private void printList() {
        this.items.stream().forEach(System.out::println);
    }

    class Item {

        private final String name;
        private final int barcode;
        private final double price;

        private Item(String[] memVars) throws Exception {
            try {
                this.name = memVars[0];
                this.barcode = Integer.parseInt(memVars[1]);
                this.price = Double.parseDouble(memVars[2]);
            } catch (Exception e) {
                throw new Exception("Could not convert " + Arrays.toString(memVars) + " to a valid object - " + e.getMessage());
            }

        }

        //getters
        public String getName() {
            return name;
        }

        public int getBarcode() {
            return barcode;
        }

        public double getPrice() {
            return price;
        }

        //alt+insert -> select toString(), check all, let netbeans do the work for you
        @Override
        public String toString() {
            return "Item{" + "name=" + name + ", barcode=" + barcode + ", price=" + price + '}';
        }

    }

    @Override
    public void run() {
        //dummy application logic
    }

}
