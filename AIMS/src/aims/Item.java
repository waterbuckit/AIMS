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
    
    class ParseException extends Exception{
        public ParseException(String str) {
            super(str);
        }
    }

    private String name;
    private int barcode;
    private double price;
    private String category;

    public Item(String line) throws ParseException {
        try {
            String[] memVars = line.split(":");
            this.name = memVars[0];
            this.barcode = Integer.parseInt(memVars[1]);
            this.price = Double.parseDouble(memVars[2]);
            this.category = formatCategory(memVars[3]);
//            this.category = memVars[3];
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
    public void setName(String name){
        this.name = name;
    }
    public void setBarcode(int num){
        this.barcode = num;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String toFormattedString(){
        return name + ":" + barcode + ":" + price + ":" + category + "\n";
    }
    //alt+insert -> select toString(), check all, let netbeans do the work for you
    @Override
    public String toString() {
        return "£" + String.format("%.2f", getPrice()) + " " + getName();
    }

    private String formatCategory(String memVar) {
        return memVar.replaceAll("\\r\\n|\\r|\\n", "");
    }
}
