/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author waterbucket
 */
public class ReportPieChart extends JPanel {

    // final as values do not change
    private final HashMap<String, Integer> values;
//    private HashMap<HashMap.Entry<String, Integer>, Double> valuesWithAngleSize;
    private final ArrayList<Segment> segments;
    private final ArrayList<Double> segmentExtents;

    public ReportPieChart(HashMap<String, Integer> values) {
        segments = new ArrayList<>();
        segmentExtents = new ArrayList<>();
        this.values = values;
        calculateForEach();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        segments.forEach((segment) -> {
            // gets us a random colour 
            // draw the current segment being iterated over
            segment.draw(g2d);
            System.out.println("I am called!");
        });
    }

    private void calculateForEach() {
        // work out the total 
        double sumOfBeforeValues = 0;
        int total = calcTotalOfMapValues();
        for (HashMap.Entry<String, Integer> entry : values.entrySet()) {
            double extent = (float) entry.getValue() / total * 360;
            segmentExtents.add(extent);
        }
        for (int i = 0; i < segmentExtents.size(); i++) {
            if (i == 0) {
                // KILL ME 
                segments.add(new Segment(0.0, segmentExtents.get(i), this.getSize()));
            } else {
                segments.add(new Segment(sumOfBeforeValues, segmentExtents.get(i), this.getSize()));
            }
            sumOfBeforeValues += segmentExtents.get(i);
        }
    }

    private int calcTotalOfMapValues() {
        int total = 0;
        for (HashMap.Entry<String, Integer> entry : values.entrySet()) {
            total = total + entry.getValue();
        }
        return total;
    }

    private Color getColour() {
        Random random = new Random();
        final float hue = random.nextFloat();
        final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
        final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
        return Color.getHSBColor(hue, saturation, luminance);
    }

    class Segment {

        private final double previousExtent;
        private final double angle;
        int panelX;
        int panelY;
        Color colour;
        public Segment(Double previousExtent, Double i, Dimension panelSize) {
            this.previousExtent = previousExtent;
            System.out.println("PREVIOUS EXTENT" + this.previousExtent);
            this.angle = i + this.previousExtent;
            System.out.println("new ANGLE " + this.angle);
            this.panelX = (int) (panelSize.getWidth() / 2);
            System.out.println("X " + panelX);
            this.panelY = (int) (panelSize.getHeight() / 2);
            System.out.println("Y " + panelY);
            this.colour = getColour();
        }
        private void draw(Graphics2D g2d) {
            g2d.setColor(colour);
            g2d.fillArc(panelX, panelY, 100, 100, (int) previousExtent, (int) angle);
        }
    }
}
