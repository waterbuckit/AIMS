/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

/**
 *
 * @author zugbug
 */
public class Item {

    private final String name;
    private final int barcode;
    private final double price;
    private final String category;

    public Item(String line) throws Exception {
        try {
            String[] memVars = line.split(":");
            this.name = memVars[0];
            this.barcode = Integer.parseInt(memVars[1]);
            this.price = Double.parseDouble(memVars[2]);
            this.category = memVars[3];
        } catch (Exception e) {
            throw new Exception("Could not convert <" + line + "> to a valid object - " + e.getMessage());
        }

    }


    //getters
    public String getName() {
        return name;
    }

    public int getBarcode() {
        return barcode;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    //alt+insert -> select toString(), check all, let netbeans do the work for you
    @Override
    public String toString() {
        return getName() + " of " + getCategory();
    }
}
