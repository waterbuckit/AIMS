/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author waterbucket
 */
public class ReportBarChart extends JPanel {

    private final ArrayList<Bar> bars;
    private final int MAXHEIGHT = 400;
    private final int MAXWIDTH = 400;

    public ReportBarChart(HashMap<String, Integer> values) {
        bars = makeBars(values);
        this.setSize(500, 500);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawAxis(g2d, new Rectangle(this.getX(), this.getY()));
    }

    private ArrayList<Bar> makeBars(HashMap<String, Integer> values) {
        // find the tallest bar
        ArrayList<Bar> ret = new ArrayList<>();
        values.entrySet().forEach((entry) -> {
            ret.add(new Bar(entry.getKey(), entry.getValue()));
        });
        ret.sort((Bar a, Bar b) -> b.getValue() - a.getValue());
        //normalise and scale
        int highestValue = ret.get(0).getValue();
        ret.forEach((b) -> {
            b.setValue((b.getValue() / highestValue) * MAXHEIGHT);
        });
        return ret;
    }

    private void drawAxis(Graphics2D g2d, Rectangle rectangle) {
        // x axis

        g2d.drawLine(MAXWIDTH - 380, 380, MAXWIDTH, 380);
        g2d.drawString("Categories", MAXWIDTH / 2, 400);
        // y axis
        g2d.drawLine(MAXWIDTH - 380, 380, 20, 20);
        Font font = new Font(null, Font.PLAIN, 12);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(270), 0, 0);
        Font rotatedFont = font.deriveFont(affineTransform);
        g2d.setFont(rotatedFont);
        g2d.drawString("Quantity", 13, 190);
        g2d.dispose();
    }

    class Bar {

        private String category;
        private Integer value;

        public Bar(String category, Integer value) {

        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        HashMap<String, Integer> myMap = new HashMap<>();
        myMap.put("Oranges", 20);
        myMap.put("Apples", 30);
        myMap.put("DeliciousFruits", 10);
        myMap.put("Tastiness", 50);
        ReportBarChart panel = new ReportBarChart(myMap);
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
