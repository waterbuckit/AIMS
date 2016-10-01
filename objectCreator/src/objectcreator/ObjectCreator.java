/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectcreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

    public void loadItems() throws IOException {
        File file = new File("objectList.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        // The string (line from .txt file) that will be asigned to said variable.
        String line;
        ArrayList<Item> items = new ArrayList<>();
        try {
            int numOfMemVars = 3;
            Object[] memVars = new String[numOfMemVars];
            int lineCounter = 0;
            while((line = br.readLine()) != null){
                memVars[lineCounter] = line;
                lineCounter++;
                if((lineCounter) % numOfMemVars == 0){
                    items.add(new Item(memVars));
                }
            }
            printList(items);
        } catch (IOException e) {
            System.out.println("File does not exist.");
        }
    }

    private void printList(ArrayList<Item> items) {
        items.stream().forEach((item) -> {
            System.out.println(item.getName() + " " + item.getBarcode() + " " + item.getPrice());
        });
    }

    class Item {
        private final String name;
        private final int barcode;
        private final double price;
        
        private Item(Object[] memVars) {
            this.name = (String) memVars[0];
            this.barcode = Integer.parseInt((String) memVars[0]);
            this.price = (double) memVars[0];
        }
        //behaviours
        public String getName(){
            return name;
        }
        public int getBarcode(){
            return barcode;
        }
        public double getPrice(){
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
