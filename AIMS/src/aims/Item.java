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

    static class ParseException extends Exception {

        public ParseException(String str) {
            super(str);
        }
    }

    private String name;
    private int barcode;
    private double price;
    private String category;

    public Item(String name, int barcode, double price, String category) {
        this.name = name;
        this.barcode = barcode;
        this.price = price;
        this.category = category;
    }

    public static Item fromString(String line) throws ParseException {
        try {
            String[] memVars = line.split(":");
            // Make a new item based off array of member variables passed to it.
            return new Item(memVars[0], Integer.parseInt(memVars[1]), Double.parseDouble(memVars[2]), formatCategory(memVars[3]));
        } catch (NumberFormatException e) {
            throw new ParseException("Could not convert <" + line + "> to a valid object - " + e.getMessage());
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

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setBarcode(int num) {
        this.barcode = num;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    //For adding new items to file.
    public String toFormattedString() {
        return name + ":" + barcode + ":" + price + ":" + category + "\n";
    }

    //Overriden the toString method to allow easy access to fields of the item class
    @Override
    public String toString() {
        return "£" + String.format("%.2f", getPrice()) + " " + getName();
    }
    //Ensures there are no newline characters for Windows or Linux systems.
    private static String formatCategory(String memVar) {
        return memVar.replaceAll("\\r\\n|\\r|\\n", "");
    }
}
