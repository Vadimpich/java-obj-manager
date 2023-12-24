/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2021examples_task4threedimensions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import kg2021examples_task4threedimensions.draw.IDrawer;
import kg2021examples_task4threedimensions.draw.SimpleEdgeDrawer;
import kg2021examples_task4threedimensions.math.Vector3;
import kg2021examples_task4threedimensions.screen.ScreenConverter;
import kg2021examples_task4threedimensions.third.Camera;
import kg2021examples_task4threedimensions.third.IModel;
import kg2021examples_task4threedimensions.third.Scene;
import models.Icosahedron;
import models.Parallelepiped;
import models.Triangle;


public class DrawPanel extends JPanel
        implements CameraController.RepaintListener, KeyListener {
    private Scene scene;
    private ScreenConverter sc;
    private Camera cam;
    private CameraController camController;
    
    public DrawPanel() {
        super();
        sc = new ScreenConverter(-1, 1, 2, 2, 1, 1);
        cam = new Camera();
        camController = new CameraController(cam, sc);
        scene = new Scene(Color.WHITE.getRGB());
        scene.showAxes();

        IModel model = new Triangle(new Vector3(0, 0, 0), new Vector3(0.1f, 0.1f, 0.1f), new Vector3(-0.2f, -0.2f, 0.2f));
        //new Icosahedron(new Vector3(0, 0, 0), 1, Color.black);
        scene.getModelsList().add(model);
        
        camController.addRepaintListener(this);
        addMouseListener(camController);
        addMouseMotionListener(camController);
        addMouseWheelListener(camController);
    }
    
    @Override
    public void paint(Graphics g) {
        sc.setScreenSize(getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D)bi.getGraphics();
        IDrawer dr = new SimpleEdgeDrawer(sc, graphics);
        scene.drawScene(dr, cam);
        //scene.getModelsList().get(0).rotate(0.01f);
        g.drawImage(bi, 0, 0, null);
        graphics.dispose();
    }

    private void rotateFigure(Parallelepiped p, float angle){
        p.rotate(angle);
    }

    @Override
    public void shouldRepaint() {
        repaint();
    }

    private boolean toRotate = false;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_E){
            toRotate = !toRotate;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
