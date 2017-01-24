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

    String transDetails;
    
    Transaction(String transDetails) {
    }
}
