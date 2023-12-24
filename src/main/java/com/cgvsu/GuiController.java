package com.cgvsu;

import com.cgvsu.objreader.ObjReaderException;
import com.cgvsu.render_engine.RenderEngine;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.awt.*;
import java.awt.event.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import javax.swing.*;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;

import static com.cgvsu.ExceptionDialog.throwExceptionWindow;

public class GuiController {

    final private float TRANSLATION = 1F;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Model mesh = null;

    private Camera camera = new Camera(
            new Vector3f(0, 0, 10),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100
    );

    private float angle = 0;
    private float angleY = 0;

    private Vector2f last;

    private boolean leftFlag = false;

    private boolean rightFlag = false;

    private boolean middleFlag = false;

    private Timeline timeline;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }
        });


        timeline.getKeyFrames().add(frame);
        timeline.play();

        canvas.setOnScroll(scrollEvent -> {
            float deltaY = (float) scrollEvent.getDeltaY();
            handleMouseWheel(deltaY / 40 * TRANSLATION);
        });

        canvas.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                leftFlag = true;
            if (mouseEvent.getButton() == MouseButton.SECONDARY)
                rightFlag = true;
            if (mouseEvent.getButton() == MouseButton.MIDDLE)
                middleFlag = true;
            last = new Vector2f((float) mouseEvent.getX(), (float) mouseEvent.getY());
        });

        canvas.setOnMouseReleased(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                leftFlag = false;
            if (mouseEvent.getButton() == MouseButton.SECONDARY)
                rightFlag = false;
            if (mouseEvent.getButton() == MouseButton.MIDDLE)
                middleFlag = false;

            if (!leftFlag && !rightFlag && !middleFlag)
                last = null;
        });

        canvas.setOnMouseDragged(event -> {
            if (last == null) {
                last = new Vector2f((float) event.getX(), (float) event.getY());
            }
            float dx = (float) event.getX() - last.x;
            float dy = (float) event.getY() - last.y;
            if (event.getButton() == MouseButton.PRIMARY) {
                angle += dx / 100 * TRANSLATION;
                angleY = Math.min((float) Math.PI / 4, Math.max(-(float) Math.PI / 4, angleY + dy / 100));
                rotateXZ();
            }
            last = new Vector2f((float) event.getX(), (float) event.getY());

        });
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            if (mesh == null){
                throwExceptionWindow();

            }
        } catch (IOException | RuntimeException exception) {
            throwExceptionWindow();

        }
    }


    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        float dx = Math.signum(camera.getPosition().x) * (Math.abs(camera.getPosition().x) - TRANSLATION) - camera.getPosition().x;
        float dz = Math.signum(camera.getPosition().z) * (Math.abs(camera.getPosition().z) - TRANSLATION) - camera.getPosition().z;
        camera.movePosition(new Vector3f(dx, 0, dz));
        System.out.println(camera.getPosition());
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        float dx = Math.signum(camera.getPosition().x) * (Math.abs(camera.getPosition().x) + TRANSLATION) - camera.getPosition().x;
        float dz = Math.signum(camera.getPosition().z) * (Math.abs(camera.getPosition().z) + TRANSLATION) - camera.getPosition().z;
        camera.movePosition(new Vector3f(dx, 0, dz));
        System.out.println(camera.getPosition());
    }

    @FXML
    public void rotateXZ() {
        double radius = Math.sqrt(
                camera.getPosition().z * camera.getPosition().z + camera.getPosition().x * camera.getPosition().x
        );
        double dx = radius * Math.sin(angle) - camera.getPosition().x;
        double dy = radius * Math.sin(angleY) - camera.getPosition().y;
        double dz = radius * Math.cos(angle) - camera.getPosition().z;
        camera.movePosition(new Vector3f((float) dx, (float) dy, (float) dz));
        System.out.println(camera.getPosition());
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        angle -= TRANSLATION * 0.1f;
        rotateXZ();
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        angle += TRANSLATION * 0.1f;
        rotateXZ();
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
        System.out.println(52);
    }

    @FXML
    public void handleMouseWheel(float delta) {
        System.out.println(delta);
        double radius = Math.sqrt(
                camera.getPosition().z * camera.getPosition().z + camera.getPosition().x * camera.getPosition().x
        );
        if (radius > 2 * TRANSLATION && delta > 0 || delta < 0) {
            float dx = Math.signum(camera.getPosition().x) * (Math.abs(camera.getPosition().x) - delta) - camera.getPosition().x;
            float dz = Math.signum(camera.getPosition().z) * (Math.abs(camera.getPosition().z) - delta) - camera.getPosition().z;
            camera.movePosition(new Vector3f(dx, 0, dz));
        }
        System.out.println(camera.getPosition());
        System.out.println(radius);
    }
}