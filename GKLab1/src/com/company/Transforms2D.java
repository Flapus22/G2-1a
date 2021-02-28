package com.company;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Transforms2D extends JPanel {

    private class Display extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.translate(300, 300);  // Moves (0,0) to the center of the display.
            int whichTransform = transformSelect.getSelectedIndex();

            // TODO Apply transforms here, depending on the value of whichTransform!
            // ad2
            RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            RenderingHints hints = rh;
            g2.setRenderingHints(hints);
            // ad3
            Stroke stroke = new BasicStroke(2);
            g2.setStroke(stroke);
            // ad4

            int numberOfPoints = 18; // mój wielokąt foremny ma mieć 18 wierzchołków
            int Radius = 150;
            var Xcoordinates = new int[18];
            var Ycoordinates = new int[18];
            for (int i = 0; i < numberOfPoints; i++) {
                Xcoordinates[i] = (int) (Radius * Math.cos((Math.PI / 2 + 2 * Math.PI * i) / numberOfPoints));
                Ycoordinates[i] = (int) (Radius * Math.sin((Math.PI / 2 + 2 * Math.PI * i) / numberOfPoints));
            }
            // nowy gradient
            g2.setPaint(new GradientPaint(new Point(Xcoordinates[0], Ycoordinates[0]), Color.YELLOW,
                    new Point(Xcoordinates[numberOfPoints / 2], Ycoordinates[numberOfPoints / 2]), Color.RED));


            switch (whichTransform) {
                case 1:
                    g2.scale(0.5, 0.5);
                    System.out.println("jeden");
                    break;
                case 2:
                    g2.rotate(Math.toRadians(45));
                    System.out.println("dwa");
                    break;
                case 3:
                    g2.scale(0.5, -1);
                    System.out.println("trzy");
                    break;
                case 4:
//                    for (int i = 0; i < numberOfPoints; i++) {
//                        Xcoordinates[i] += (Ycoordinates[i] / Math.PI);
//                        Ycoordinates[i] -= (Ycoordinates[i] / Math.PI);
//                    }
                    g2.shear(0.5,0);
                    System.out.println("cztery");
                    break;
                case 5:
                    g2.translate(0, -1.5 * Radius);
                    g2.scale(1, 0.5);
                    System.out.println("pięć");
                    break;
                case 6:
                    g2.rotate(Math.toRadians(90));
                    g2.shear(0.5,0);
                    System.out.println("sześć");
                    break;
                case 7:
                    g2.scale(-0.5, -1);
                    System.out.println("siedem");
                    break;
                case 8:
                    g2.translate(-25, 100);
                    g2.rotate(Math.toRadians(30));
                    g2.scale(1, 0.5);
                    System.out.println("osiem");
                    break;
                case 9:
                    g2.translate(100, 0);
                    g2.shear(0,0.5);
                    g2.rotate(Math.toRadians(180));
                    System.out.println("dziewięć");
                    break;
                default:

                    System.out.println("No valid transform selected");
                    break;
            }
            g2.fillPolygon(Xcoordinates, Ycoordinates, numberOfPoints);
            //g2.drawImage(pic, -200, -150, null); // Draw image with center at (0,0).
        }
    }

    private Display display;
    private BufferedImage pic;
    private JComboBox<String> transformSelect;

    public Transforms2D() throws IOException {
        pic = ImageIO.read(new FileInputStream("shuttle.jpg"));
        display = new Display();
        display.setBackground(Color.GREEN);
        display.setPreferredSize(new Dimension(600, 600));
        transformSelect = new JComboBox<String>();
        transformSelect.addItem("None");
        for (int i = 1; i < 10; i++) {
            transformSelect.addItem("No. " + i);
        }
        transformSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                display.repaint();
            }
        });
        setLayout(new BorderLayout(3, 3));
        setBackground(Color.GRAY);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 10));
        JPanel top = new JPanel();
        top.setLayout(new FlowLayout(FlowLayout.CENTER));
        top.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        top.add(new JLabel("Transform: "));
        top.add(transformSelect);
        add(display, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);
    }


    public static void main(String[] args) throws IOException {
        JFrame window = new JFrame("2D Transforms");
        window.setContentPane(new Transforms2D());
        window.pack();
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation((screen.width - window.getWidth()) / 2, (screen.height - window.getHeight()) / 2);
        window.setVisible(true);
    }

}