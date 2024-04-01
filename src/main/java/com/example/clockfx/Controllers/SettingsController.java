package com.example.clockfx.Controllers;

import com.example.clockfx.Components.TBackground;
import com.example.clockfx.Utils.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;

@SuppressWarnings("all")
public class SettingsController {


    @FXML private BorderPane main;

    @FXML private GridPane grid;

    @FXML private Canvas xCanvas;
    @FXML private AnchorPane canvasPane;

    @FXML private Label generalColorLabel;
    @FXML private Label clockColorLabel;
    @FXML private Label controlsColorLabel;
    @FXML private Label bgColorLabel;
    @FXML private Label hideControlsLabel;

    @FXML private ColorPicker generalColor;
    @FXML private ColorPicker clockColor;
    @FXML private ColorPicker controlsColor;
    @FXML private ColorPicker bgColor;
    @FXML private ChoiceBox hideControls;

    StageBuilder stageBuilder;

    @FXML
    public void initialize(){
        loadInitialValues();
        setDesign();
        setGeneralColors();
        drawX();
        checkBoxLogic();

        xCanvas.setOnMouseClicked(this::handleXEvent);
    }

    private void loadInitialValues() {
        generalColor.setValue(Settings.getGeneralColor());
        clockColor.setValue(Settings.getClockColor());
        controlsColor.setValue(Settings.getControlsColor());
        bgColor.setValue(Settings.getBgColor());
        hideControls.setItems(FXCollections.observableArrayList("Yes", "No"));
        if (Settings.areControlsHidden()) {
            hideControls.setValue("Yes");
        } else {
            hideControls.setValue("No");
        }
    }

    public boolean parseBoxValue(){
        return hideControls.getValue().equals("Yes") ? true : false;
    }

    private void drawX(){
        GraphicsContext gc = xCanvas.getGraphicsContext2D();
        gc.setStroke(Settings.getControlsColor());
        gc.setLineWidth(4);
        double width = gc.getCanvas().getWidth();
        double height = gc.getCanvas().getHeight();
        gc.strokeLine(0, 0, width, height);
        gc.strokeLine(0, width, height, 0);
    }
    private void handleXEvent(MouseEvent mouseEvent) {


        try {

            Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
            double width = screenSize.getWidth();
            double height = screenSize.getHeight();
            String[] values = parseColorValues();

            ConfigUtils.createConfig(values[0], values[1], values[2], values[3], values[4]);
            ConfigUtils.loadConfig();
            FXMLLoader fxmlLoader = CFXMLLoader.getFXMLLoader("Clock");
            Parent parent = fxmlLoader.load();

            ClockController clockController = fxmlLoader.getController();

            stageBuilder = StageBuilder.newBuilder()
                    .withScene(new Scene(parent,width, height))
                    .setMaxWidthAndHeight(width, height)
                    .setFullscreen(true)
                    .setFullScreenHint("Press f to change fullscreen state!")
                    .setExitFullScreenKey("f")
                    .removeUpperBar()
                    .setIcon("icons/taskIcon.png");

            Timer timer = new Timer();
            timer.schedule(new UpdateComponents(clockController), 0, 10);

            Stage thisStage = (Stage) grid.getScene().getWindow();
            thisStage.close();

            stageBuilder.showStage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String[] parseColorValues(){

        String[] values = new String[5];

        Color general = generalColor.getValue();
        Color clock = clockColor.getValue();
        Color controls = controlsColor.getValue();
        Color bg = bgColor.getValue();
        boolean hide = parseBoxValue();

        values[0] = "#" + general.toString().substring(2, 8);
        values[1] = "#" + clock.toString().substring(2, 8);
        values[2] = "#" + bg.toString().substring(2, 8);
        values[3] = "#" + controls.toString().substring(2, 8);
        values[4] = String.valueOf(parseBoxValue());
        return values;
    }

    public void checkBoxLogic(){
        Settings.setHiddenControls(!Settings.areControlsHidden());
    }

    public void setDesign(){
        canvasPane.setBackground(TBackground.getBg(Color.TRANSPARENT));
        this.grid.setBackground(TBackground.getBg(Color.TRANSPARENT));
        this.main.setBackground(TBackground.getBg(bgColor.getValue()));
        AnchorPane.setTopAnchor(xCanvas, 5.0);
        AnchorPane.setRightAnchor(xCanvas, 5.0);
        xCanvas.setWidth(32);
        xCanvas.setHeight(32);
    }


    public void setGeneralColors(){
        Color newValue = Settings.getGeneralColor();
        generalColorLabel.setTextFill(newValue);
        clockColorLabel.setTextFill(newValue);
        controlsColorLabel.setTextFill(newValue);
        bgColorLabel.setTextFill(newValue);
        hideControlsLabel.setTextFill(newValue);

    }





}
