/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author waterbucket
 */
public class ItemSelector extends JPanel {

    ProcessHandler.ObjectCreator objectCreate;
    ArrayList<Item> items;
    final int rows;
    final int cols;
    GridLayout theLayout;
    Set<String> categories;
    
    public ItemSelector() {
        //move things into here!
        this.objectCreate = new ProcessHandler.ObjectCreator();
        //Unlikely to stay as hard code
        this.rows = 7;
        this.cols = 7;
        this.categories = new HashSet();
        this.theLayout = new GridLayout(rows, cols, -1, -1);
        this.items = new ArrayList();
    }

//    private Map<String, JButton> buttonMap;

    //          vvvvvvvvvvvv BAD. 
    public void setUpItems() throws FileNotFoundException {
        items = (ArrayList<Item>) objectCreate.getItems();
        objectCreate.items.forEach(System.err::println);
        categories = objectCreate.items.stream().distinct().map(s -> s.getCategory()).collect(Collectors.toSet());
        this.setLayout(theLayout);
        categories.stream().forEach((category) -> {
            System.err.println(category);
            addCategoryButton(category);
        });
    }

    private void addCategoryButton(String cat) {
        JButton categoryButton = new JButton(cat);
        System.err.println(cat);
//        buttonMap.put(cat, categoryButton);
        categoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
//                updateGrid(cat);
            }

//            private void updateGrid(String cat) {
//                buttonMap.keySet().stream().forEach((_item) -> {
//                    removeButton(cat);
//                });
//            }
        });
        this.add(categoryButton);
    }

//    private void removeButton(String cat) {
//        JButton button = buttonMap.remove(cat);
//        this.remove(button);
//    }
}
