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
        //move things into here!
        this.objectCreate = new ProcessHandler.ItemObjectManipulator();
        //Unlikely to stay as hard code
        this.rows = 7;
        this.cols = 7;
        this.categories = new HashSet();
        this.theLayout = new GridLayout(rows, cols, 1, 1);
        this.itemsList = new ArrayList();
        //maybe changed because annoying compiler
        this.getItems();
        //get the purchaseList
    }

    public void reset() throws FileNotFoundException {
        this.removeAll();
        this.itemsList.clear();
        this.getItems();
        if(AIMS.instance.purchaseList.getTotal() > 0){
            AIMS.instance.purchaseList.jButton15.setEnabled(true);
        }
    }

    private void getItems() throws FileNotFoundException {
        itemsList = (ArrayList<Item>) objectCreate.getItemsAsList();
        setUpItems();
    }

    public void setUpItems() {
        categories = this.itemsList.stream().distinct().map(s -> s.getCategory()).collect(Collectors.toSet());
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
