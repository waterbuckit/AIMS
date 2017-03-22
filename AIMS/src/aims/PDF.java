/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author kine
 */
final public class PDF {

    private  PDF() {
    }
    

    private static class PDFWriterImpl implements Writer {

        private PDFWriterImpl() {
        }
        

        @Override
        public File write(Receipt receipt, File folder) throws IOException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    public static class Receipt {

        private final String shop;
        private final String date;
        private final String name;
        private final Optional<Long> code;
        private final List<Item> item;

        public Receipt(String shopName, String dateTime, String cashier, Optional<Long> barcode, List<Item> items) {
            this.shop = shopName;
            this.date = dateTime;
            this.name = cashier;
            this.code = barcode;
            this.item = items;
        }
    }

    public static interface Writer {

        public File write(Receipt receipt, File folder) throws IOException;

    }

    public final static Writer WRITER_INSTANCE = new PDFWriterImpl();

}
