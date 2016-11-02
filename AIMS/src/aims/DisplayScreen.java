/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;


/**
 *
 * @author waterbucket
 */
public class DisplayScreen{
    AIMSFrame frame = new AIMSFrame();
    //create objects of each JPanel form
    ItemSelector itemSelect = new ItemSelector();
    PurchaseScreen purchaseScreen = new PurchaseScreen();
    FunctionScreen functionScreen = new FunctionScreen();
    LoginScreen loginScreen = new LoginScreen();
    PurchaseList purchaseList = new PurchaseList();
    PurchaseScreen ps = new PurchaseScreen();
    //setup process for the GUI
    void guiSetUp() {
//        JPanel currentJPanel = new JPanel(); 
//        setCurrentRightJPanel(itemSelect, currentJPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(itemSelect);
        try {
            itemSelect.setUpItems();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DisplayScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame.pack();
        frame.setVisible(true);
    }
//    private void setCurrentRightJPanel(JPanel newCurrent, JPanel currentJPanel){
//        if(currentJPanel == null){
//            currentJPanel = newCurrent;
//            frame.add(currentJPanel);
//        }else{
//            frame.remove(currentJPanel);
//            frame.add(newCurrent);
//        }
//    }
}
