package kg2020.task4;

import kg2020.task4.drawer.IDrawer;
import kg2020.task4.drawer.SimpleEdgeDrawer;
import kg2020.task4.screen.ScreenConverter;
import kg2020.task4.threeD.Camera;
import kg2020.task4.threeD.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel {
    private ScreenConverter screenConverter = new ScreenConverter(-1, -1, 2, 2, 800, 600);
    private Scene scene;
    private Camera camera;
    private CameraController cameraController;

    public DrawPanel() {
        screenConverter = new ScreenConverter(-1, 1, 2, 2, 1, 1);
        camera = new Camera();
        cameraController = new CameraController(camera, screenConverter);
        scene = new Scene(Color.WHITE.getRGB());
        scene.showAxes();
    }

    @Override
    public void paint(Graphics g) {
        screenConverter.setScreenSize(getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D bi_g = (Graphics2D)bi.getGraphics();
        IDrawer dr = new SimpleEdgeDrawer(screenConverter, bi_g);
        scene.drawScene(dr, camera);

        /**
         * set my code here
         */
        g.drawImage(bi, 0, 0, null);
        bi_g.dispose();
    }
}
