/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
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
    

    public ItemSelector() throws FileNotFoundException {
        //move things into here!
        this.objectCreate = new ProcessHandler.ObjectCreator();
        //Unlikely to stay as hard code
        this.rows = 7;
        this.cols = 7;
        this.categories = new HashSet();
        this.theLayout = new GridLayout(rows, cols, 1, 1);
        this.items = new ArrayList();
        //maybe changed because annoying compiler
        this.setUpItems();
        //get the purchaseList
    }

//    private Map<String, JButton> buttonMap;
    //          vvvvvvvvvvvv BAD. 
    public void setUpItems() throws FileNotFoundException {
        items = (ArrayList<Item>) objectCreate.getItems();
//        objectCreate.items.forEach(System.err::println);
        categories = objectCreate.items.stream().distinct().map(s -> s.getCategory()).collect(Collectors.toSet());
        this.setLayout(theLayout);
        categories.stream().forEach((category) -> {
            addCategoryButton(category);
        });
    }

    private void addCategoryButton(String cat) {
        JButton categoryButton = new JButton(cat);
//        System.err.println(cat);
//        buttonMap.put(cat, categoryButton);
        categoryButton.addActionListener((ae) -> {
            showItemGrid(cat);
        });
        this.add(categoryButton);
    }

    private void showItemGrid(String cat) {
        ArrayList<Item> itemsOfCategory = new ArrayList();
        HashMap<JButton, Item> buttonItemMap = new HashMap<>();
        //remove all components from the current JPanel
        this.removeAll();
        JPanel itemGrid = new JPanel();
        //iterate over items and add them to list of items of category
        items.stream().filter((item) -> (item.getCategory().equals(cat))).forEachOrdered((item) -> {
            itemsOfCategory.add(item);
        });
        //make a button for each item
        //map each button to its relevant object
        itemsOfCategory.forEach(System.out::println);
        //traditional recursive
        itemsOfCategory.stream().map((item) -> {
            JButton itemButton = new JButton(item.getName());
            buttonItemMap.put(itemButton, item);
            return itemButton;
        }).map((itemButton) -> {
            itemButton.addActionListener((ae) -> {
            });
            return itemButton;
        }).forEachOrdered((itemButton) -> {
            itemGrid.add(itemButton);
        });
        this.add(itemGrid);
        this.repaint();
        this.revalidate();
    }
//    private void removeButton(String cat) {
//        JButton button = buttonMap.remove(cat);
//        this.remove(button);
//    }
}
