/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import java.awt.Dimension;
import java.awt.GridLayout;
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

    public ItemSelector(AIMS instance) throws FileNotFoundException {
        //move things into here!
        this.objectCreate = new ProcessHandler.ObjectCreator();
        //Unlikely to stay as hard code
        this.rows = 7;
        this.cols = 7;
        this.categories = new HashSet();
        this.theLayout = new GridLayout(rows, cols, 1, 1);
        this.items = new ArrayList();
        //maybe changed because annoying compiler
        this.getItems();
        //get the purchaseList
    }

    private void getItems() throws FileNotFoundException {
        items = (ArrayList<Item>) objectCreate.getItems();
        setUpItems();
    }
//    private Map<String, JButton> buttonMap;
    //          vvvvvvvvvvvv BAD. 

    public void setUpItems() {
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
        //remove all components from the current JPanel
        this.removeAll();
        JPanel itemGrid = new JPanel();
        itemGrid.setLayout(new GridLayout(2,2));
        //iterate over items and add them to list of items of category
        items.stream().filter((item) -> (item.getCategory().equals(cat))).forEachOrdered((item) -> {
            itemsOfCategory.add(item);
        });
        //make a button for each item
        //map each button to its relevant object
        //traditional recursive
        itemsOfCategory.stream().map((item) -> {
            JButton itemButton = new JButton(item.getName());
            itemButton.setPreferredSize(new Dimension(0, 400));
            itemButton.addActionListener((ae) -> {
                AIMS.instance.purchaseList.addItemToList(item);
            });
            return itemButton;
        }).forEachOrdered((itemButton) -> {
            itemGrid.add(itemButton);
        });
        //backButton
        JButton backButton = new JButton("Return");
        backButton.addActionListener((ae) -> {
            this.removeAll();
            setUpItems();
            this.repaint();
            this.revalidate();
        });
        itemGrid.add(backButton);
        this.add(itemGrid);
        this.repaint();
        this.revalidate();
    }
}
