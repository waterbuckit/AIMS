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
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author waterbucket
 */
public class ItemSelector extends JPanel {

    final int rows = 7;
    final int cols = 7;
    GridLayout theLayout = new GridLayout(rows, cols, -1, -1);
    ArrayList<String> categoryList = new ArrayList();
    ProcessHandler.ObjectCreator objectCreate = new ProcessHandler.ObjectCreator();
    private Map<String, JButton> buttonMap;

    public void setUpItems() throws FileNotFoundException {
        System.out.println("I am being called!");
        objectCreate.loadItems();
        setUpCategories();
        this.setLayout(theLayout);
        categoryList.stream().forEach((category) -> {
            addCategoryButton(category);
        });
    }

    private void setUpCategories() {
        objectCreate.items.stream().filter((item) -> (!categoryList.contains(item.getCategory()))).forEach((item) -> {
            categoryList.add(item.getCategory());
        });
    }

    private void addCategoryButton(String cat) {
        JButton categoryButton = new JButton(cat);
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
