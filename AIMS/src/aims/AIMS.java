package aims;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author waterbucket
 */
public class AIMS implements Runnable {

    public static AIMS instance;

    public static void main(String[] args) throws Exception {
        instance = new AIMS();
        instance.run();
    }

    AIMSFrame frame;
    //views for the frame
    ItemSelector itemSelect;
    PurchaseScreen purchaseScreen;
    FunctionScreen functionScreen;
    LoginScreen loginScreen;
    PurchaseList purchaseList;
    PurchaseScreen ps;
    
    public AIMS() {
        this.frame = new AIMSFrame();
        //set up controller
        //set up screens
        this.ps = new PurchaseScreen();
        this.purchaseList = new PurchaseList();
        this.loginScreen = new LoginScreen();
        this.functionScreen = new FunctionScreen();
        this.purchaseScreen = new PurchaseScreen();
        try {
            this.itemSelect = new ItemSelector();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AIMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        frame.setTitle("AIMS");
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(frame.getExtendedState()|Frame.MAXIMIZED_BOTH);
        //frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
        frame.add(purchaseList, BorderLayout.WEST);
        JLabel label = new JLabel("hello");
        purchaseList.add(label);
        //switch to initial screen (like in future login screen?)
        //changes only the right hand screen/list is always there
        switchToScreen(itemSelect);
    }
    
    public void switchToScreen(JPanel screen) {
        frame.add(screen, BorderLayout.CENTER);
        frame.pack();
        //not permanent, change!!!!!
        frame.setSize(1600,900);
    }
}
