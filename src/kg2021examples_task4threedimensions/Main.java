/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2021examples_task4threedimensions;

import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new DrawPanel());
        frame.setVisible(true);
    }
}
