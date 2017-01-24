/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author waterbucket
 */
class Receipt {

    ArrayList<Item> itemsToBuy;
    double changeTogive;
    User user;
    String string;
    DateFormat df;
    Double total;

//        this.string = strung;
    Receipt(ArrayList<Item> itemsToBuy, double total, double changeToGive, User user, int transactionNum) {
        this.total = total;
        this.itemsToBuy = itemsToBuy;
        this.changeTogive = changeToGive;
        this.user = user;
        this.df = new SimpleDateFormat("dd/MM/yyyy");
        AIMS.instance.purchaseLog.makeTransaction(new Transaction(itemsToBuy, changeToGive, user, df.format(Calendar.getInstance().getTime())));
    }

    public void makeReceipt() {
        if (fileAndDirExists()) {
            writeTofile(generateString());
        } else {
            makeDirectoryAndFile();
            writeTofile(generateString());
        }
    }

    private String generateString() {
        StringBuilder sb = new StringBuilder();
        sb.append("#").append(AIMS.instance.purchaseLog.getTransactionNumber()).append("\n");
        sb.append("Receipt\n");
        sb.append(df.format(Calendar.getInstance().getTime())).append("\n");
        sb.append("-\n");
        itemsToBuy.forEach((item) -> {
            sb.append(item.getName()).append(" ").append(item.getPrice()).append("\n");
        });
        sb.append("Total: ").append(total).append("\n");
        sb.append("-\nOperator: ");
        sb.append(user.getName()).append("\n");
        sb.append("-\n");

        return sb.toString();
    }

    private boolean fileAndDirExists() {
        File checkFile = new File("Receipts");
        File checkReceiptLog = new File("Receipts/" + df.format(Calendar.getInstance().getTime()));
        return checkFile.isDirectory() && checkReceiptLog.isFile();
    }

    private void makeDirectoryAndFile() {
        try {
            new File("Receipts").mkdir();
            new File("Receipts/" + df.format(Calendar.getInstance().getTime())).getParentFile().createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Receipt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeTofile(String receiptString) {
        try {
            FileWriter fw = new FileWriter(new File("Receipts/" + df.format(Calendar.getInstance().getTime())).getAbsoluteFile(), true);
            fw.write(receiptString);
        } catch (IOException ex) {
            Logger.getLogger(Receipt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
