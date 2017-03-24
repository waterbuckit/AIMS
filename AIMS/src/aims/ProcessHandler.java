/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author waterbucket
 */
public class ProcessHandler {

    static int getTransactionNumber() {
        int transactionNumber = 0;

        return transactionNumber;
    }

    static class UserData {
        
        // returns a string array of the details regarding a user
        public String[] makeUserObject(String password) throws FileNotFoundException, NoSuchAlgorithmException {
            File file = new File("Users/userList");
            Scanner lines = new Scanner(file).useDelimiter("\n");
            while (lines.hasNext()) {
                String wordsOfLine[] = lines.next().split(":");
                // the stored hash must equal the digest of the hashed inputted password
                if (wordsOfLine[1].equals(hashPass(password))) {
                    return wordsOfLine;
                }
            }
            return null;
        }

        public void logOut() {
            LoginScreen.user = null;
            AIMS.instance.status.setUserLabel(null);
            AIMS.instance.loggedIn = false;
            AIMS.instance.switchToScreen(AIMS.instance.loginScreen);
        }
        
        // Java implementation of the MD5 hashing algorithm.
        private String hashPass(String s) throws NoSuchAlgorithmException {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes());
            byte[] bytesOfPass = m.digest();
            BigInteger bigInt = new BigInteger(1, bytesOfPass);
            String encodedPassword = bigInt.toString(16);
            while (encodedPassword.length() < 32) {
                encodedPassword = "0" + encodedPassword;
            }
            return encodedPassword;
        }
    }

    static class ItemObjectManipulator {

        List<Item> itemsList = new ArrayList<>();
        HashMap<Item, Integer> items = new HashMap<>();

        public HashMap<Item, Integer> getItemsAsMap() throws FileNotFoundException {
            File file = new File("Items/itemList");
            Scanner lines = new Scanner(file) //Wrap a scanner around the file
                    .useDelimiter("\n"); //And make it's .next() method return 1 line

            Integer lineNum = 0;
            while (lines.hasNext()) {
                String line = lines.next();
                //check it in the file.
                //would be better if it was global and configurable
                try {
                    Item item = Item.fromString(line);
                    this.items.put(item, lineNum);
                    lineNum++;
                } catch (Item.ParseException e) {
                    //inform the user there was a problem in the file!
                    System.err.println(e.getMessage());
                    //Because we already created an informative message in the Item
                    //constructor
                    //We still want to add any other valid items
                }
            }
            return items;
        }

        public void addItem(String itemData) throws Item.ParseException {
            itemsList.add(Item.fromString(itemData));
        }
        
        // same method as above, but returns a list rather than a map.
        public List<Item> getItemsAsList() throws FileNotFoundException {
            File file = new File("Items/itemList");
            Scanner lines = new Scanner(file) 
                    .useDelimiter("\n"); 
            while (lines.hasNext()) {
                String line = lines.next();
                try {
                    Item item = Item.fromString(line);
                    this.itemsList.add(item);
                } catch (Item.ParseException e) {
                    System.err.println(e.getMessage());
                }
            }
            
            return itemsList;
        }
        // rewrites the list to the items file
        void updateItems(ArrayList<Item> listOfItems) throws IOException {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("Items/itemList"), false));
            listOfItems.forEach((item) -> {
                try {
                    bw.write(item.toFormattedString());
                    bw.flush();
                } catch (IOException ex) {
                    Logger.getLogger(ProcessHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }
}
