package aims;

import java.awt.Frame;
import javax.swing.JFrame;
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

        //set up screens
        this.ps = new PurchaseScreen();
        this.purchaseList = new PurchaseList();
        this.loginScreen = new LoginScreen();
        this.functionScreen = new FunctionScreen();
        this.purchaseScreen = new PurchaseScreen();
        this.itemSelect = new ItemSelector();

        //switch to initial screen (like in future login screen?)
        switchToScreen(itemSelect);
        
    }

    @Override
    public void run() {
        frame.setExtendedState(frame.getExtendedState()|Frame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
    }

    public void switchToScreen(JPanel screen) {
        frame.setContentPane(screen);
        frame.pack();
    }
}
