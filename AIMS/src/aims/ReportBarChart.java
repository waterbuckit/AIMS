/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author waterbucket
 */
public class ReportBarChart extends JPanel {
    /**
     * Test case
     * @param args 
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Map<String,Integer> values = new HashMap();
        values.put("Potatoes", 120);
        values.put("Ranch sauce", 100);
        values.put("Avocadoes", 50);
        values.put("PB&j",140);
        ReportBarChart chart = new ReportBarChart(values);
        frame.setContentPane(chart);
        frame.setDefaultCloseOperation(2);
        frame.pack();
        frame.setVisible(true);
    }

    private final int XAxisFinishXValue = 450;
    private final int XAxisFinishYValue = 450;
    private final int XAxisStartXValue = 30;
    private final int XAxisStartYValue = 450;
    private final int YAxisFinishXValue = 30;
    private final int YAxisFinishYValue = 30;
    private final int YAxisStartXValue = 30;
    private final int YAxisStartYValue = 450;

    private List<Bar> bars;

    /**
     * Set the value of bars
     *
     * @param categories new value of bars
     */
    public final void setBars(Map<String, Integer> categories) {
        this.bars = makeBars(categories);
    }

    private final Random random = new Random(5);
    private final Font rotatedFont;

    public ReportBarChart(Map<String, Integer> values) {
        //TODO: handle cases when there are no values!
        this.setBars(values);
        this.setSize(500, 500);
        AffineTransform rotation = new AffineTransform();
        rotation.rotate(Math.toRadians(270), 0, 0);
        this.rotatedFont = new Font(null, Font.PLAIN, 12).deriveFont(rotation);
    }

    private List<Bar> makeBars(Map<String, Integer> catMap) {
        //allocate space for return collection
        List<Bar> retList = new ArrayList<>(catMap.size());
        // find the biggest value
        int highestValue = Collections.max(catMap.values());
        //work out width per bar
        int width = calculateLengthOfXAxis() / catMap.size();
        //initialise as 30 for offset
        int sumOfPreviousWidths = 31;
        for (Map.Entry<String, Integer> entry : catMap.entrySet()) {
            Integer value = entry.getValue();
            //normalise and scale 
            double height = ((double) value / (double) highestValue) * calculateLengthOfYAxis();
            //create new data container
            Bar bar = new Bar(
                    generateColour(),
                    new Rectangle2D.Float(sumOfPreviousWidths, 450 - (int) height, width, (int) height),
                    entry.getKey() + " - " + entry.getValue());
            retList.add(bar);
            sumOfPreviousWidths = sumOfPreviousWidths + width;
        }
        return retList;
    }

    class Bar {

        private final Rectangle2D.Float shape;

        /**
         * Get the value of shape
         *
         * @return the value of shape
         */
        public Rectangle2D.Float getShape() {
            return shape;
        }

        private final Color colour;

        /**
         * Get the value of colour
         *
         * @return the value of colour
         */
        public Color getColour() {
            return colour;
        }

        private final String label;

        /**
         * Get the value of label
         *
         * @return the value of label
         */
        public String getLabel() {
            return label;
        }

        public Bar(Color colour, Rectangle2D.Float shape, String label) {
            this.shape = shape;
            this.label = label;
            this.colour = colour;
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawAxis(g2d);
        drawBars(g2d);
    }

    private void drawBars(Graphics2D g2d) {
        bars.forEach((bar) -> {
            g2d.setColor(bar.getColour());
            g2d.fill(bar.getShape());
        });
        g2d.setFont(rotatedFont);
        g2d.setColor(Color.BLACK);
        bars.forEach((t) -> {
            final int x = (int) (t.shape.x+t.shape.width/2);
            final int y = (int) (450 - t.shape.height/2);
            g2d.drawString(t.getLabel(), x, y);
        });
    }

    private Color generateColour() {
        final float hue = random.nextFloat();
        final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
        final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
        return Color.getHSBColor(hue, saturation, luminance);
    }

    private void drawAxis(Graphics2D g2d) {
        // x axis
        g2d.drawLine(XAxisStartXValue, XAxisStartYValue, XAxisFinishXValue, XAxisFinishYValue);
        g2d.drawString("Categories", XAxisFinishXValue / 2, XAxisFinishYValue + 20);
        // y axis
        g2d.drawLine(YAxisStartXValue, YAxisStartYValue, YAxisFinishXValue, YAxisFinishYValue);
        Font font = new Font(null, Font.PLAIN, 12);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(270), 0, 0);
        g2d.setFont(rotatedFont);
        g2d.drawString("Quantity", 18, YAxisStartYValue / 2);
    }

    private int calculateLengthOfYAxis() {
        return YAxisStartYValue - YAxisFinishYValue;
    }

    private int calculateLengthOfXAxis() {
        return XAxisFinishXValue - XAxisStartXValue;
    }
}
