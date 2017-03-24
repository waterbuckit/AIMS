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
import java.util.Arrays;
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

    ProcessHandler.ItemObjectManipulator objectCreate;
    private ArrayList<Item> itemsList;
    private final int rows;
    private final int cols;
    GridLayout theLayout;
    private Set<String> categories;

    public ItemSelector() throws FileNotFoundException {
        this.objectCreate = new ProcessHandler.ItemObjectManipulator();
        // define the number of rows that can be used by the gridlayout
        this.rows = 7;
        this.cols = 7;
        this.categories = new HashSet();
        this.theLayout = new GridLayout(rows, cols, 1, 1);
        this.itemsList = new ArrayList();
        this.getItems();
        //get the purchaseList
    }
    
    // allows for a reset of the current instance when new items are added
    public void reset() throws FileNotFoundException {
        this.removeAll();
        this.categories.clear();
        this.itemsList.clear();
        this.getItems();
        if(AIMS.instance.purchaseList.getTotal() > 0){
            AIMS.instance.purchaseList.jButton15.setEnabled(true);
        }else{
            AIMS.instance.purchaseList.jButton15.setEnabled(false);
        }
    }

    private void getItems() throws FileNotFoundException {
        itemsList = (ArrayList<Item>) objectCreate.getItemsAsList();
        setUpItems();
    }
    // get a list of categories as a distinct set (there will never be the same twice)
    public void setUpItems() {
        categories = this.itemsList.stream().distinct().map(s -> s.getCategory()).collect(Collectors.toSet());
        this.setLayout(theLayout);
        // make a buttonf or each category
        categories.stream().forEach((category) -> {
            addCategoryButton(category);
        });
    }

    private void addCategoryButton(String cat) {
        JButton categoryButton = new JButton(cat);
        // map each button to an action listener with its necessary category
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
        itemGrid.setLayout(new GridLayout(2, 2));
        //iterate over items and add them to list of items of category
        itemsList.stream().filter((item) -> (item.getCategory().equals(cat))).forEachOrdered((item) -> {
            System.out.println(item);
            itemsOfCategory.add(item);
        });
        //make a button for each item
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
