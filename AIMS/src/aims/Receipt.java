/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import java.io.BufferedWriter;
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

    Receipt(ArrayList<Item> itemsToBuy, double total, double changeToGive, User user, int transactionNum) {
        this.total = total;
        this.itemsToBuy = itemsToBuy;
        this.changeTogive = changeToGive;
        this.user = user;
        this.df = new SimpleDateFormat("dd-MM-yyyy");
    }

    public void makeReceipt() {
        if (!dirExists()) {
            makeDir();
            writeToFile(generateString());
        } else {
            writeToFile(generateString());
        }
    }

    private String generateString() {
        StringBuilder sb = new StringBuilder();
        sb.append(AIMS.instance.status.getTransactionNum()).append(";");
        sb.append("Receipt;");
        sb.append(df.format(Calendar.getInstance().getTime())).append(";");
        itemsToBuy.forEach((item) -> {
            sb.append(item.getName()).append(" ").append(item.getPrice()).append(";");
        });
        sb.append("Total: ").append(String.format("%.2f", total)).append(";");
        sb.append("Operator: ");
        sb.append(user.getName()).append(";");
        sb.append("\n");
        return sb.toString();
    }

    private void makeDir() {
        new File("Receipts").mkdir();
    }

    private void writeToFile(String receiptString) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("Receipts/" + df.format(Calendar.getInstance().getTime())).getAbsoluteFile(), true))) {
            bw.write(receiptString);
            bw.flush();
        } catch (IOException ex) {
            Logger.getLogger(Receipt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean dirExists() {
        File checkDir = new File("Receipts");
        return checkDir.isDirectory();
    }
}
