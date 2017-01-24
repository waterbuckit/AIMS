/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author waterbucket
 */
class Transactions {

    List<Transaction> transactions;

    public Transactions() {
        transactions = new ArrayList<>();
    }

    void makeTransaction(Transaction trans) {
        transactions.add(trans);
    }

    public void addPreviousTransactions(String time) {
        
    }

    int getTransactionNumber() {
        return transactions.size();
    }

}

class Transaction {

    int transactionNum;
    ArrayList<Item> itemsBought;
    double change;
    User user;
    String time;
    
    Transaction(ArrayList<Item> itemsBought, double changeToGive, User user, String time) {
        this.itemsBought = itemsBought;
        this.change = changeToGive;
        this.user = user;
        this.time = time;
    }
}
