/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * Namespace class PDF
 *
 * Should contain any constants used to configure the PDF submodule. Also
 * contains Interfaces and Implementations of a Writer and Receipt.
 *
 * @author kine
 */
final public class PDF {

    /**
     * Max length for the Name column. Truncates and replaces with `...` if too
     * long.
     */
    private static final int NAME_COLUMN_LENGTH = 30;

    /**
     * Test procedure. Showcases creating receipt from items, and rendering it.
     *
     * @param args
     */
    public static void main(String[] args) {
        System.err.println("WARNING: RUNNING PDF TEST!");
        Writer writer = PDF.WRITER_INSTANCE;

        Map basket = new HashMap<>();
        basket.put(new Item("Toilet Paper", 0, 9.99, "Consumables"), 2);
        basket.put(new Item("Thor's Hammer", 1, 13.37, "Weapon"), 1);
        basket.put(new Item("Sabertooth Armour", 3, 3.50, "Wearables"), 5);

        Receipt r = new Receipt("Wallmart", "22/03/2017", "Kirill N.", Optional.of(203212l), basket);

        try {
            File f = writer.write(r);
            System.out.println(f.getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Disallows creation of instances of the namespace class.
     */
    private PDF() {
    }

    /**
     * Writer implementation. Should not be used directly. Please use the static
     * instance {@link PDF#WRITER_INSTANCE}
     */
    private static class WriterImpl implements Writer {

        /**
         * Disallows creation of instances of writer class.
         */
        private WriterImpl() {
        }

        /**
         * Blackbox implementation of Writer interface.
         *
         * @param receipt datastructure containing data to write to PDF
         * @param file specific file to which PDF is written to
         * @throws IOException if there were issues writing the PDF
         */
        @Override
        public void write(Receipt receipt, File file) throws IOException {
            PDPage page = new PDPage();
            try (PDDocument doc = new PDDocument();) {
                PDPageContentStream content = new PDPageContentStream(doc, page);
                /**
                 * **Content Creation Start***
                 */
                content.beginText();
                content.setFont(PDType1Font.COURIER, 20);
                content.setLeading(15);
                content.newLineAtOffset(100, 700);
                content.showText(receipt.shop);
                content.setFont(PDType1Font.COURIER, 12);
                if (receipt.code.isPresent()) {
                    content.newLine();
                    content.showText("Receipt ID " + receipt.code.get());
                }
                content.newLine();
                content.showText(receipt.date);
                content.newLine();
                content.showText("----------");
                content.newLine();
                content.showText(String.format("%1$-" + NAME_COLUMN_LENGTH + "s|%2$3s|%3$1s", "Item", "Amt", "£"));
                try {
                    receipt.item.entrySet().forEach((e) -> {
                        Item t = e.getKey();
                        int num = e.getValue();
                        try {
                            content.newLine();
                            String t_name = t.getName();
                            if (t_name.length() > NAME_COLUMN_LENGTH) {
                                t_name = t_name.substring(0, NAME_COLUMN_LENGTH - 3) + "...";
                            }
                            String name = String.format("%1$-" + NAME_COLUMN_LENGTH + "s", t_name);
                            String amt = String.format("%1$03d", num);
                            String cash = String.format("£%1$.2f", num * t.getPrice());
                            content.showText(name + "|" + amt + "|" + cash);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                } catch (RuntimeException e) {
                    throw (IOException) e.getCause();
                }
                content.newLine();
                content.showText("----------");
                content.newLine();
                content.showText("Total:");
                content.newLine();
                content.showText(String.format("£%1$-15s", receipt.item.entrySet().stream().mapToDouble((t) -> {
                    return t.getKey().getPrice() * t.getValue();
                }).sum()));
                content.newLine();
                content.newLine();
                content.showText("You have been served today by " + receipt.name);
                content.newLine();
                content.showText("Have a good day!");
                content.endText();
                content.close();
                /**
                 * **Content Creation Finish**
                 */
                doc.addPage(page);
                doc.save(file);
            }
        }

        /**
         * Uses the blackbox implementation of PDF writer. Chooses own location
         * for the PDF, and returns it to the caller.
         *
         * @param receipt datastructure containing data to write to PDF
         * @return location of the written PDF
         * @throws IOException if there were issues writing the PDF
         */
        @Override
        public File write(Receipt r) throws IOException {
            File f = File.createTempFile("receipt-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()), ".pdf");
            write(r, f);
            return f;
        }

    }

    /**
     * Standard that the Writer accepts as a receipt. Please create new
     * instances of this class to pass to the writer with relevant information.
     */
    public static class Receipt {

        /**
         * Shop name.
         */
        private final String shop;
        /**
         * Date as a string. Any format
         */
        private final String date;
        /**
         * Cashier's name.
         */
        private final String name;
        /**
         * Optional code to print on receipt.
         */
        private final Optional<Long> code;
        /**
         * Map of items and their amounts that are purchased.
         */
        private final Map<Item, Integer> item;

        /**
         * Public constructor for acceptable receipt format.
         *
         * @param shopName {@link PDF.Receipt#shop}
         * @param dateTime {@link PDF.Receipt#date}
         * @param cashier {@link PDF.Receipt#name}
         * @param barcode {@link PDF.Receipt#code}
         * @param items {@link PDF.Receipt#item}
         */
        public Receipt(String shopName, String dateTime, String cashier, Optional<Long> barcode, Map<Item, Integer> items) {
            this.shop = shopName;
            this.date = dateTime;
            this.name = cashier;
            this.code = barcode;
            this.item = items;
        }
    }

    /**
     * API for writing PDF Receipts.
     */
    public static interface Writer {

        /**
         * For writing some receipt formatted data to a specific file.
         *
         * @param receipt the data
         * @param file the target file to write to
         * @throws IOException if there were issues writing the PDF
         */
        public void write(Receipt receipt, File file) throws IOException;

        /**
         * For writing some receipt formatted data. Target is chosen by
         * implementation and returned for further work.
         *
         * @param r the data
         * @return the target file that was written to
         * @throws IOException if there were issues writing the PDF
         */
        public File write(Receipt r) throws IOException;

    }
    /**
     * Public instance of the writer with an accepted implementation that should
     * be used whenever writing a PDF.
     */
    public final static Writer WRITER_INSTANCE = new WriterImpl();

}
