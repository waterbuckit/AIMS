package aims;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        // abstract method that runs once an instance of AIMS has been made
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
    Transactions purchaseLog;
    StatusBar status;
    Boolean loggedIn;

    public AIMS() {
        this.frame = new AIMSFrame();
        //set up screens
        this.purchaseLog = new Transactions();
        this.purchaseList = new PurchaseList(itemSelect);
        this.loginScreen = new LoginScreen();
        this.functionScreen = new FunctionScreen();
        this.status = new StatusBar();
        try {
            this.itemSelect = new ItemSelector();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AIMS.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.loggedIn = false;
    }

    @Override
    public void run() {
        frame.setTitle("AIMS");
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(purchaseList, BorderLayout.WEST);
        frame.add(status, BorderLayout.SOUTH);
        //switch to initial screen
        //changes only the right hand screen/list is always there
        frame.setSize(1600, 900);
        status.setTransactionNumber(purchaseLog.getTransactionNumber(new SimpleDateFormat("dd-MM-yyyy").format(
                Calendar.getInstance().getTime())));
        //check if there are any users, if there is, switch to the login screen
        try {
            boolean anyUsers = checkForUsers();
            if (anyUsers) {
                switchToScreen(loginScreen);
            } else {
                // add new user if there are no known users
                loginScreen.checkIfUsers();
                switchToScreen(new UserAddScreen());
            }
        // if there is no file for users, add a new user.
        } catch (FileNotFoundException ex) {
            loginScreen.checkIfUsers();
            switchToScreen(new UserAddScreen());
        }
    }
    // method that allows for switching between jpanels
    public void switchToScreen(JPanel screen) {
        frame.add(screen, BorderLayout.CENTER);
        frame.repaint();
        frame.revalidate();
    }

    private boolean checkForUsers() throws FileNotFoundException {
        return new File("Users/userList").exists() && new File("Users/userList").length() != 0;
    }
}
