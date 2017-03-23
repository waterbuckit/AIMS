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
import java.util.Collections;
import java.util.HashMap;
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

    private final int XAxisFinishXValue = 450;
    private final int XAxisFinishYValue = 450;
    private final int XAxisStartXValue = 30;
    private final int XAxisStartYValue = 450;
    private final int YAxisFinishXValue = 30;
    private final int YAxisFinishYValue = 30;
    private final int YAxisStartXValue = 30;
    private final int YAxisStartYValue = 450;

    private final HashMap<String, Integer> hashMapOfValues;
    private Random random = new Random(5);
    private final AffineTransform affineTransform;
    private final Font font;

    public ReportBarChart(HashMap<String, Integer> values) {
        this.hashMapOfValues = values;
        affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(270),0,0);
        font = new Font(null, Font.PLAIN, 12);
        this.setSize(500, 500);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawAxis(g2d);
        drawBars(hashMapOfValues, g2d);
    }

    private void drawBars(HashMap<String, Integer> catMap, Graphics2D g2d) {
        // find the biggest value
        int highestValue = Collections.max(catMap.values());
        //work out width per bar
        int width = calculateLengthOfXAxis() / catMap.size();
        //initialise as 30 for offset
        int sumOfPreviousWidths = 31;
        for (Map.Entry<String, Integer> entry : catMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            //normalise and scale 
            double height = ((double)value / (double) highestValue) * calculateLengthOfYAxis();
            g2d.setColor(generateColour());
            g2d.fillRect(sumOfPreviousWidths, 450-(int)height, width, (int)height);
            g2d.setColor(Color.BLACK);
            Font rotatedFont = font.deriveFont(affineTransform);
            g2d.setFont(rotatedFont);
            g2d.drawString(key + " - " + value, sumOfPreviousWidths+width/2, 450-(int)height/2);
            sumOfPreviousWidths = sumOfPreviousWidths + width;
        }
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
        Font rotatedFont = font.deriveFont(affineTransform);
        g2d.setFont(rotatedFont);
        g2d.drawString("Quantity", 18, YAxisStartYValue / 2);
    }
    /*
    Two methods for working out the distance between each point. 
    */
    private int calculateLengthOfYAxis() {
        return YAxisStartYValue - YAxisFinishYValue;
    }

    private int calculateLengthOfXAxis() {
        return XAxisFinishXValue - XAxisStartXValue;
    }
}
