/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author waterbucket
 */
class Transactions {

    public String findPreviousTransactions(String time, int transactionNumberNeeded) {
        File requiredDayFile = new File("Receipts/" + time);
        try {
            // get all the lines of the file to a list ;
            List<String> lines = Files.lines(requiredDayFile.toPath()).collect(Collectors.toList());
            // pass the list to the binarysearch method and assign the previous transaction string to the return
            String previousTransaction = binarySearch(lines, transactionNumberNeeded);
            return previousTransaction;
        } catch (IOException ex) {
            Logger.getLogger(Transactions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "There was no such transaction!";
    }
    
    public HashMap<String, Integer> getCategoryAmounts(String timeStart, String timeFinish){
        HashMap<String, Integer> categoryQuantities = new HashMap<>();
        // get all of our files
        File[] files = new File("Receipts").listFiles();
        // sort our list of files in date order
        Arrays.sort(files, (a, b) -> Long.compare(a.lastModified(), b.lastModified()));
        // get the range of files we need, so that will be all files between time start finish
        for(int i = 0; i < files.length; i++){
            if(files[i].getName().equals(timeStart)){
                for(int j = i; j < files.length; j++){
                    if(!files[j].getName().equals(timeFinish)){
                        categoryQuantities = calculateTotalPerCategory(files[j], categoryQuantities);
                    }else{
                        categoryQuantities = calculateTotalPerCategory(files[j], categoryQuantities);
                        break;
                    }
                }
                break;
            }
        }
        return categoryQuantities;
    }
    
    public int getTransactionNumber(String time) {
        try {
            File requiredDayFile = new File("Receipts/" + time);
            int transactioNumber = (int) Files.lines(requiredDayFile.toPath()).count();
            return transactioNumber;
        } catch (IOException ex) {
        }
        return 1;
    }
    
    private String binarySearch(List<String> lines, int transactionNumberNeeded) {
        int low = 0;
        int high = lines.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            String[] wordsOfLine = lines.get(mid).split(";");
            if (Integer.parseInt(wordsOfLine[0]) == transactionNumberNeeded) {
                return formatString(wordsOfLine);
            } else if (Integer.parseInt(wordsOfLine[0]) < transactionNumberNeeded) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return "No such transaction";
    }

    private String formatString(String[] wordsOfLine) {
        StringBuilder formattedString = new StringBuilder();
        for(String wordOfLine : wordsOfLine){
            formattedString.append(wordOfLine.replace(";", "\n"));
        }
        return formattedString.toString();
    }

    private HashMap<String, Integer> calculateTotalPerCategory(File file, HashMap<String, Integer> categoryQuantities) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}