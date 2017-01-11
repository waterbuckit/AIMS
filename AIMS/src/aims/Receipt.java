/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import java.util.ArrayList;

/**
 *
 * @author waterbucket
 */
class Receipt {

    ArrayList<Item> itemsToBuy;
    double changeTogive;
    User user;

    Receipt(ArrayList<Item> itemsToBuy, double changeToGive, User user, String companyName) {
        this.itemsToBuy = itemsToBuy;
        this.changeTogive = changeToGive;
        this.user = user;
    }

    private void makeReceipt() {
        try {
            //check if file exists
            if(fileExists()){
                
            }
        } catch (Exception e) {
            
        }
    }

    private String generateString() {
        StringBuilder sb = new StringBuilder();
        sb.setLength(20);
        
        
        return sb.toString();
    }

    private boolean fileExists() {
        return true;
    }
}
