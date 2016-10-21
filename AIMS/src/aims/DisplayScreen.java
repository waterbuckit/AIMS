/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import javax.swing.JPanel;


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
        JPanel currentJPanel = new JPanel(); 
        frame.setVisible(true);
        setCurrentRightJPanel(loginScreen, currentJPanel);
        setCurrentLeftJPanel(purchaseList, currentJPanel);
    }
    private void setCurrentRightJPanel(JPanel newCurrent, JPanel currentJPanel){
        if(currentJPanel == null){
            currentJPanel = newCurrent;
            frame.add(currentJPanel);
        }else{
            frame.remove(currentJPanel);
            frame.add(newCurrent);
        }
    }

    private void setCurrentLeftJPanel(JPanel currentLeft, JPanel currentJPanel) {
        
    }
    
}
