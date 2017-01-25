/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    public String findPreviousTransactions(String time, int transactionNumberNeeded) {
        File requiredDayFile = new File("Receipts/" + time);
        try {
            // get all the lines of the file to a list ;
            List<String> lines = Files.lines(requiredDayFile.toPath()).collect(Collectors.toList());
            String previousTransaction = binarySearch(lines, transactionNumberNeeded);
            return previousTransaction;
        } catch (IOException ex) {
            Logger.getLogger(Transactions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    int getTransactionNumber() {
        return transactions.size();
    }

    private String binarySearch(List<String> lines, int transactionNumberNeeded) {
        int low = 0;
        int high = lines.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            String[] wordsOfLine = lines.get(mid).split(";");
            if (Integer.parseInt(wordsOfLine[0]) == transactionNumberNeeded) {
                return Arrays.toString(wordsOfLine);
            } else if (Integer.parseInt(wordsOfLine[0]) < transactionNumberNeeded) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return "No such transaction";
    }

    class Transaction {
        String transDetails;
        Transaction(String transDetails) {
        }
    }
