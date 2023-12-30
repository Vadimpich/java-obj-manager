package com.cgvsu;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objreader.ObjWriter;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.RenderEngine;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.vecmath.Point2f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.cgvsu.ExceptionDialog.throwExceptionWindow;

public class GuiController {

    final private float TRANSLATION = 2F;
    private float x = 0;

    @FXML
    AnchorPane anchorPane;


    @FXML
    private VBox modelsMenuBox;


    @FXML
    private VBox camerasMenuBox;

    @FXML
    private VBox settingsMenuBox;

    @FXML
    private Spinner fpsSpinner;

    @FXML
    private Button deleteCameraButton;

    @FXML
    private Button applyFpsButton;

    @FXML
    private Label fpsLabel;

    @FXML
    private Canvas canvas;

    @FXML
    private TabPane settingsTab;

    @FXML
    private Button showSettingsButton;

    @FXML
    private ToggleGroup camerasGroup = new ToggleGroup();

    @FXML
    private ToggleGroup camerasPinGroup = new ToggleGroup();

    private List<Model> models = new ArrayList<>();

    private Model selectedModel;

    private List<Float> modelCenters = new ArrayList<>();

    private Camera camera;

    private List<Camera> cameras = new ArrayList<>();

    private int currentCameraNum = 1;

    private float angle = 0;

    private float angleY = 0;

    private Vector2f last;

    private boolean leftFlag = false;

    private boolean rightFlag = false;

    private boolean middleFlag = false;

    private Timeline timeline;

    private double maxFPS = 60;

    private KeyFrame frame = new KeyFrame(Duration.millis(1000 / maxFPS), event -> frameEvent());

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        addCamera();

        startRender();

        fpsSpinner.getEditor().setText("60");
        fpsSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            int fps = 60;
            try {
                fps = Integer.parseInt(newValue);
            } catch (NumberFormatException e) {
                if (!Objects.equals(newValue, "")) {
                    fpsSpinner.getEditor().setText(oldValue);
                } else {
                    applyFpsButton.setDisable(true);
                }
                return;
            }

            if (fps < 1) {
                fpsSpinner.getEditor().setText("1");
                fps = 1;
            } else if (fps > 120) {
                fpsSpinner.getEditor().setText("120");
                fps = 120;
            }

            applyFpsButton.setDisable(fps == maxFPS);
        });

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
                RenderEngine.deleteVertex(new Point2f((float) mouseEvent.getX(), (float) mouseEvent.getY()));
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
            } else if (event.getButton() == MouseButton.SECONDARY) {
                translateModel(selectedModel, -dx / 25, -dy / 20, 0);
            }
            last = new Vector2f((float) event.getX(), (float) event.getY());
        });
    }

    void frameEvent() {
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
        camera.setAspectRatio((float) (width / height));

        renderModels(width, height);
    }

    void startRender() {
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        frame = new KeyFrame(Duration.millis(1000 / maxFPS), event -> frameEvent());
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    void applyFPS() {
        timeline.stop();
        maxFPS = Integer.parseInt(fpsSpinner.getEditor().getText());
        fpsLabel.setText(String.format("FPS: %d", (int)maxFPS));
        startRender();
    }

    private void renderModels(double width, double height) {
        RenderEngine.render(canvas.getGraphicsContext2D(), camera, models, (int) width, (int) height);
    }

    @FXML
    private void toggleSettings() {
        settingsTab.setVisible(!settingsTab.isVisible());
        String arrow = (settingsTab.isVisible()) ? ">" : "<";
        showSettingsButton.setText(arrow);
        showSettingsButton.setTranslateX(240-showSettingsButton.getTranslateX());
    }

    @FXML
    private void onOpenModelMenuItemClick() throws IOException {
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
            addToModelsList(fileName);
            Model newModel = ObjReader.read(fileContent);
            if (newModel == null) {
                throwExceptionWindow();
            }
            translateModel(newModel, x, 0, 0);
            if (models.isEmpty()) {
                newModel.selected = true;
                selectedModel = newModel;
            }
            models.add(newModel);
            modelCenters.add(x);
            x += newModel.xSize;
        } catch (Exception exception) {
            throwExceptionWindow();
            throw exception;
        }
    }

    @FXML
    private void addToModelsList(Path fileName) {
        String name = fileName.toString();
        MenuButton modelButton = new MenuButton(name.split("\\\\")[name.split("\\\\").length - 1]);
        modelButton.setMinWidth(240);
        modelButton.setMaxWidth(240);
        CheckMenuItem polygonMeshShow = new CheckMenuItem("Полигональная сетка");
        polygonMeshShow.setSelected(true);
        CheckMenuItem textureShow = new CheckMenuItem("Текстура");
        textureShow.setSelected(false);
        CheckMenuItem lightingShow = new CheckMenuItem("Освещение");
        lightingShow.setSelected(false);
        RadioMenuItem pinCamera = new RadioMenuItem("Центрировать камеру");
        pinCamera.setSelected(models.isEmpty());
        int modelIndex = models.size();

        pinCamera.setOnAction(actionEvent -> setCameraTarget(modelIndex));

        camerasPinGroup.getToggles().add(camerasPinGroup.getToggles().size(), pinCamera);
        modelButton.getItems().add(pinCamera);

        modelButton.getItems().add(polygonMeshShow);
        modelButton.getItems().add(textureShow);
        modelButton.getItems().add(lightingShow);

        modelsMenuBox.getChildren().add(modelButton);
    }

    @FXML
    private void onSaveModelMenuItemClick() throws IOException {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj");
        fileChooser.setTitle("Save Model");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());

        if (file != null) {
            ObjWriter.write(selectedModel, file.getAbsolutePath());
        }
    }

    @FXML
    private void setCameraTarget(int ind) {
        selectedModel = models.get(ind);
        for (Model model : models) {
            model.selected = (model == selectedModel);
        }
        camera.setCenteredModel(ind);
        camera.setTarget(new Vector3f(modelCenters.get(ind), 0, 0));
    }

    @FXML
    private void clearModelsList() {
        modelsMenuBox.getChildren().clear();
        camerasPinGroup.getToggles().clear();
    }

    @FXML
    private void clearAllModels() {
        models.clear();
        x = 0;
        clearModelsList();
    }


    private void translateModel(Model model, float x, float y, float z) {
        for (com.cgvsu.math.Vector3f vertex : model.getVertices()) {
            vertex.x += x;
            vertex.y += y;
            vertex.z += z;
        }
    }

    @FXML
    private void addCamera() {
        cameras.add(new Camera(
                new Vector3f(0, 0, 10),
                new Vector3f(0, 0, 0),
                1.0F, 1, 0.01F, 100
        ));

        deleteCameraButton.setDisable(cameras.size() <= 1);
        RadioButton newCameraButton = new RadioButton(String.format("Camera %d", currentCameraNum++));
        newCameraButton.setMinHeight(25);
        newCameraButton.setSelected(true);
        final int cameraIndex = cameras.size() - 1;
        newCameraButton.setOnAction(event -> chooseCamera(cameraIndex));
        camerasGroup.getToggles().add(cameraIndex, newCameraButton);
        camerasMenuBox.getChildren().add(newCameraButton);
        chooseCamera(cameras.size() - 1);
    }

    @FXML
    private void deleteCamera() {
        int cameraIndex = cameras.indexOf(camera);
        camerasMenuBox.getChildren().remove(cameraIndex + 3);
        cameras.remove(cameraIndex);
        camerasGroup.getToggles().remove(cameraIndex);
        chooseCamera(0);
        if (cameras.size() == 1) {
            deleteCameraButton.setDisable(true);
        }
    }

    @FXML
    private void chooseCamera(int ind) {
        camera = cameras.get(ind);
        if (!camerasGroup.getToggles().isEmpty()) {
            camerasGroup.selectToggle(camerasGroup.getToggles().get(ind));
        }
        if (!camerasPinGroup.getToggles().isEmpty()) {
            camerasPinGroup.selectToggle(camerasPinGroup.getToggles().get(camera.getCenteredModel()));
            System.out.println(camera.getCenteredModel());
        }
    }


    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        float dx = Math.signum(camera.getPosition().x) * (Math.abs(camera.getPosition().x) - TRANSLATION) - camera.getPosition().x;
        float dz = Math.signum(camera.getPosition().z) * (Math.abs(camera.getPosition().z) - TRANSLATION) - camera.getPosition().z;
        camera.movePosition(new Vector3f(dx, 0, dz));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        float dx = Math.signum(camera.getPosition().x) * (Math.abs(camera.getPosition().x) + TRANSLATION) - camera.getPosition().x;
        float dz = Math.signum(camera.getPosition().z) * (Math.abs(camera.getPosition().z) + TRANSLATION) - camera.getPosition().z;
        camera.movePosition(new Vector3f(dx, 0, dz));
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
    }

    @FXML
    public void handleMouseWheel(float delta) {
        double radius = Math.sqrt(
                camera.getPosition().z * camera.getPosition().z + camera.getPosition().x * camera.getPosition().x
        );
        if (radius > 2 * TRANSLATION && delta > 0 || delta < 0) {
            float dx = Math.signum(camera.getPosition().x) * (Math.abs(camera.getPosition().x) - delta) - camera.getPosition().x;
            float dz = Math.signum(camera.getPosition().z) * (Math.abs(camera.getPosition().z) - delta) - camera.getPosition().z;
            camera.movePosition(new Vector3f(dx, 0, dz));
        }
    }
}