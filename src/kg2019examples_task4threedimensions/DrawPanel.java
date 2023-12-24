/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2019examples_task4threedimensions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import kg2019examples_task4threedimensions.draw.IDrawer;
import kg2019examples_task4threedimensions.draw.SimpleEdgeDrawer;
import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.screen.ScreenConverter;
import kg2019examples_task4threedimensions.third.Camera;
import kg2019examples_task4threedimensions.third.Scene;
import models.outlines.Cylinder;
import models.outlines.Sinus;
import models.paths.ILine;
import models.OpenCylinder;
import models.paths.ITorus;
import models.paths.ITrefoilKnot;
import models.paths.I_Line;

/**
 * @author Alexey
 */
public class DrawPanel extends JPanel
        implements CameraController.RepaintListener {
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

        ILine l = new ILine() {
            @Override
            public Vector3 getPoint(double t) {
                return new Vector3((float) t, (float) Math.sin(t * Math.PI * 2), 0.0f);
            }
        };
        Cylinder cylinder = new Cylinder(0.1f);
        Sinus sinus = new Sinus(0.1f, 0.1f);

        ITorus torus = new ITorus(0.9f, new Vector3(0.f, 0.f, 0.f));
        ITrefoilKnot trefoilKnot = new ITrefoilKnot(0.2f, new Vector3(0.f, 0.f, 0.f));
        I_Line line = new I_Line(new Vector3(0.2f, 0.2f, 0.2f), 0.5f);
        //scene.getModelsList().add(new OpenCylinder(trefoilKnot, cylinder, true));
        scene.getModelsList().add(new OpenCylinder(torus, cylinder, true));

        camController.addRepaintListener(this);
        addMouseListener(camController);
        addMouseMotionListener(camController);
        addMouseWheelListener(camController);
    }

    @Override
    public void paint(Graphics g) {
        sc.setScreenSize(getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) bi.getGraphics();
        IDrawer dr = new SimpleEdgeDrawer(sc, graphics);
        scene.drawScene(dr, cam);
        g.drawImage(bi, 0, 0, null);
        graphics.dispose();
    }

    @Override
    public void shouldRepaint() {
        repaint();
    }
}
