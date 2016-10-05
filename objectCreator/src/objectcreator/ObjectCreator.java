/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectcreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        instance.run();
    }
    
    ArrayList<Item> items = new ArrayList<>();

    public void loadItems() throws IOException {
        File file = new File("objectList.txt");
        // The string (line from .txt file) that will be asigned to said variable.
        String line;
        try {
            FileReader fileReader = new FileReader(file);
            try (BufferedReader br = new BufferedReader(fileReader)) {
                int numOfMemVars = 3;
                Object[] memVars = new Object[numOfMemVars];
                int lineCounter = 0;
                while ((line = br.readLine()) != null) {
                    memVars[lineCounter] = line;
                    System.out.println(line);
                    lineCounter++;
                    System.out.println(lineCounter);
                    if ((lineCounter) % numOfMemVars == 0) {
                        items.add(new Item(memVars));
                        System.out.println(Arrays.toString(memVars));
                        lineCounter = 0;
                    }
                }
                br.close();
                printList(items);
            }
        } catch (IOException e) {
            System.out.println("File does not exist.");
        }
    }

    private void printList(ArrayList<Item> items) {
        for (Item item : items) {
            System.out.println(item.getName() + " " + item.getBarcode() + " " + item.getPrice());
        }
    }

    class Item {

        private final String name;
        private final int barcode;
        private final double price;

        private Item(Object[] memVars) {
            this.name = (String) memVars[0];
            this.barcode = Integer.parseInt((String) memVars[1]);
            this.price = Double.parseDouble((String) memVars[2]);

        }

        //behaviours
        public String getName() {
            return name;
        }

        public int getBarcode() {
            return barcode;
        }

        public double getPrice() {
            return price;
        }

    }

    @Override
    public void run() {
        try {
            loadItems();
        } catch (IOException ex) {
            Logger.getLogger(ObjectCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
