/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screenshotter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author waterbucket
 */
public class ScreenShotter {

    JFrame screen;
    PanelDrag drawOn;

    public ScreenShotter() {
        screen = new JFrame();
        drawOn = new PanelDrag();
        screen.setTitle("screenShotter");
        screen.setAlwaysOnTop(true);
        screen.setContentPane(drawOn);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        screen.setUndecorated(true);
        screen.setBackground(new Color(0, 0, 0, 50));
        drawOn.setOpaque(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScreenShotter screenShotter = new ScreenShotter();
            screenShotter.screen.setVisible(true);
        });
    }

    class PanelDrag extends JPanel {
        
        private Shape s;

        public PanelDrag() {
            MouseControl madapt = new MouseControl();
            addMouseMotionListener(madapt);
            addMouseListener(madapt);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.clearRect(0, 0, getWidth(), getHeight());
            g2d.setColor(Color.cyan);
            if(s == null){
                return;
            }
            s.draw(g2d);
        }

        private class MouseControl extends MouseAdapter {

            @Override
            public void mousePressed(MouseEvent me) {
                s = new Shape(me.getPoint(),me.getPoint());
                System.out.println(s.rect.getX() + " " + s.rect.getY() + " " + s.getClickedPoint());
            }

            @Override
            public void mouseDragged(MouseEvent me) {
                s.rect.setSize(s.getWidth(me.getPoint()), s.getHeight(me.getPoint()));
                System.out.println(s.rect.getX() + " " + s.rect.getY() + " " + s.getClickedPoint());
                drawOn.repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent me) {
                System.out.println("Released");
                s = null;
            }
        }
    }
    class Shape{
        Point clickedPoint;
        Rectangle rect; 
        public Shape(Point p1, Point p2) {
            this.clickedPoint = p1;
            rect = new Rectangle(p1);
            rect.add(p2);
        }
        public Point getClickedPoint() {
            return clickedPoint;
        }
        public void draw(Graphics2D g){
            g.drawRect(rect.x, rect.y, rect.width, rect.height);
        }

        private int getHeight(Point point) {
            Point pointTo = new Point(getClickedPoint().x, point.y);
            int height = (int) Math.sqrt(Math.pow((pointTo.x - getClickedPoint().x), 2) + Math.pow((pointTo.y - getClickedPoint().y), 2));
            return height;
        }

        private int getWidth(Point point) {
            Point pointTo = new Point(point.x, getClickedPoint().y);
            int width = (int) Math.sqrt(Math.pow((pointTo.x - getClickedPoint().x), 2) + Math.pow((pointTo.y - getClickedPoint().y), 2));
            return width;
        }
    }
}
