/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aims;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * @author waterbucket
 */
public class ReportPieChart extends JPanel {
    /**
     * ArrayList to store segments of the piechart
     */
    private final ArrayList<Segment> segments;
    private Random random = new Random(5);

    /**
     * Construct pie chart with pairings of name -> relative value
     *
     * @param values map of items
     */
    ReportPieChart(HashMap<String, Integer> values) {
        segments = calculateForEach(values);
    }

    /**
     * Test case: One frame filled with the piechart
     * SHOULD APPEAR AS: 1half 1quarter and 1quarter randomly coloured
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        ReportPieChart panel = new ReportPieChart(new HashMap<String, Integer>() {{
            put("Half", 20);
            put("Oranges", 5);
            put("Bananas", 40);
            put("Quarter", 10);
        }});
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        segments.forEach((segment) -> {
            // draw the current segment being iterated over
            segment.draw(g2d, new Rectangle(300,300));
        });
    }

    /**
     * Converts the map of title->value to segments
     *
     * @param values hashmap
     * @return segments
     */
    private ArrayList<Segment> calculateForEach(HashMap<String, Integer> values) {
        double angleCovered = 0;
        int total = values.values().stream().mapToInt(s -> s).sum();
        ArrayList<Segment> ret = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : values.entrySet()) {
            float angle = 360 / ((float) total / entry.getValue());
            Segment seg = new Segment(angleCovered, angle, entry.getKey());
            angleCovered += angle; //increase covered angle, after creating segment.
            ret.add(seg); //add to return collection
        }
//        System.out.println(ret);
        return ret;
    }

    /**
     * Generate random colour
     * Random is initialised with 1 as a seed, so that on every run the colours are the same for the same elements.
     *
     * @return a random colour
     */
    private Color generateColour() {
        final float hue = random.nextFloat();
        final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
        final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
        return Color.getHSBColor(hue, saturation, luminance);
    }

    class Segment {

        final String text;

        private final double start;
        private final double angle;
        private final double mid;
        Color colour;

        Segment(double start, double angle, String text) {
            colour = generateColour();
            this.start = start;
            this.angle = angle;
            this.text = text;
            this.mid = start + (angle / 2);
//            System.out.println(mid);
        }

        private void draw(Graphics2D g2d, Rectangle size) {
            g2d.setColor(this.getColour());
            g2d.fillArc(size.x, size.y, size.width, size.height, (int) start, (int) angle);
            int x = size.width - size.x;
            int y = size.height - size.y;
            int centrex = x / 2;
            int centrey = y / 2;
            int textx = (int) ((x / 4) * sin(Math.toRadians(mid + 90)) + centrex);
            int texty = (int) ((y / 4) * cos(Math.toRadians(mid + 90)) + centrey);
            g2d.setColor(Color.BLACK);
            g2d.drawString(text, textx, texty);
        }

        @Override
        public String toString() {
            return "Segment{" +
                    "text='" + text + '\'' +
                    ", start=" + start +
                    ", angle=" + angle +
                    ", mid=" + mid +
                    ", colour=" + colour +
                    '}';
        }

        Color getColour() {
            return colour;
        }
    }
}
