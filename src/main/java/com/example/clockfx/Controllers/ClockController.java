package com.example.clockfx.Controllers;

import com.example.clockfx.Components.TBackground;
import com.example.clockfx.Utils.CFXMLLoader;
import com.example.clockfx.Utils.ConfigUtils;
import com.example.clockfx.Utils.Settings;
import com.example.clockfx.Utils.StageBuilder;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("all")
public class ClockController {

    @FXML private BorderPane mainPane;

    @FXML private Canvas xCanvas;

    @FXML private Label clockLabel;

    @FXML private Canvas settingsCanvas;


    private double settingsAngle = 0;
    private Timeline timeline;
    private boolean autoRun = false;

    StageBuilder stageBuilder;

    @FXML
    public void initialize() throws IOException {
        ConfigUtils.loadConfig();
        setDesign();
        drawX();
        drawSettingsIcon();
        setMainPaneDesign();
        setLayoutAndLogic();
        Platform.runLater(this::setComponentsSize);
        createSubScene();
    }

    private void createSubScene() throws IOException {
        FXMLLoader loader = CFXMLLoader.getFXMLLoader("Settings");
        Parent parent = loader.load();
        Rectangle2D screenSize = Screen.getPrimary().getBounds();

        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        double settingsWidth = width * 0.8;
        double settingsHeight = height * 0.8;

        double settingsX = (width - settingsWidth) / 2;
        double settingsY = (height - settingsHeight) / 2;



        stageBuilder = StageBuilder.newBuilder()
                .withScene(new Scene(parent,settingsWidth, settingsHeight))
                .setMaxWidthAndHeight(settingsWidth, settingsHeight)
                .removeUpperBar()
                .setIcon("icons/taskIcon.png")
                .setPosition(settingsX, settingsY);
    }

    public void setComponentsSize(){
        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        int width =  (int) screenSize.getWidth();
        int maxLabelWidth = width * 8 / 10;
        Text clockText = new Text("00:00:00");
        clockText.setFont(new Font("Arial",50));
        while(clockText.getLayoutBounds().getWidth() < maxLabelWidth){
            clockText.setFont(new Font(clockText.getFont().getSize() + 1));
        }
        clockLabel.setFont(clockText.getFont());

    }

    public void updateComponents(){
        Platform.runLater(() -> {
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            clockLabel.setText(time);
        });
    }

    public void setMainPaneDesign(){
        mainPane.setBackground(TBackground.getBg(Settings.getBgColor()));
    }

    public void setLayoutAndLogic(){

        // xCanvas
        AnchorPane.setTopAnchor(xCanvas, 5.0);
        AnchorPane.setRightAnchor(xCanvas, 5.0);
        // clockLabel
        AnchorPane.setTopAnchor(clockLabel, 0.0);
        AnchorPane.setBottomAnchor(clockLabel, 0.0);
        AnchorPane.setLeftAnchor(clockLabel, 0.0);
        AnchorPane.setRightAnchor(clockLabel, 0.0);

        AnchorPane.setBottomAnchor(settingsCanvas, 5.0);
        AnchorPane.setRightAnchor(settingsCanvas, 5.0);


        xCanvas.setOnMouseClicked(this::handleXEvent);
        settingsCanvas.setOnMouseEntered(event -> startRotation());
        settingsCanvas.setOnMouseExited(event -> stopRotation());
        settingsCanvas.setOnMouseClicked(event -> {
           if (event.getButton() == MouseButton.SECONDARY){
               autoRun = !autoRun;
           }
           if (event.getButton() == MouseButton.PRIMARY){
               try {
                   handleSettingsClicked();
               }catch (IOException e){
                   e.printStackTrace();
                   System.exit(0);
               }
           }
        });
    }


    private void startRotation(){
        if (timeline == null){
            timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
                settingsAngle += 3;
                drawSettingsIcon();
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }

    private void stopRotation(){
        if (timeline != null && !autoRun){
            timeline.stop();
            timeline = null;
            settingsAngle = 0;
        }
    }

    public void setDesign() {
        setClockColor(Settings.getClockColor());
        clockLabel.setFont(new Font("Arial", 50));

        clockLabel.setBackground(TBackground.getBg(Color.TRANSPARENT));

        double elementsWidth = 35;
        double elementsHeight = 35;
        xCanvas.setWidth(elementsWidth - 10);
        xCanvas.setHeight(elementsHeight - 10);

        settingsCanvas.setWidth(elementsWidth);
        settingsCanvas.setHeight(elementsHeight);

    }

    public void setClockColor(Color color){
        clockLabel.setTextFill(color);
    }

    private void drawX(){

        if(!Settings.areControlsHidden()){
            GraphicsContext gc = xCanvas.getGraphicsContext2D();
            gc.setStroke(Settings.getControlsColor());
            gc.setLineWidth(4);
            double width = gc.getCanvas().getWidth();
            double height = gc.getCanvas().getHeight();

            gc.strokeLine(0, 0, width, height);
            gc.strokeLine(0, width, height, 0);
        }


    }




    private void drawSettingsIcon(){
        if (!Settings.areControlsHidden()){

            GraphicsContext gc = settingsCanvas.getGraphicsContext2D();


            double width = gc.getCanvas().getWidth();
            double height = gc.getCanvas().getHeight();


            double centerX = width / 2;
            double centerY = height / 2;
            double radius = width / 4;

            int teethCount = 8;
            double toothWidth = Math.PI * 2 / teethCount * 0.4;
            double toothHeight = (radius / 3) + 4;


            gc.clearRect(0,0, width, height);
            gc.save();





            gc.setFill(Settings.getControlsColor());
            gc.setStroke(Settings.getControlsColor());
            gc.setLineWidth(4);

            gc.beginPath();
            for (int i = 0; i < teethCount; i++) {
                double angle = i * 2 * Math.PI / teethCount + settingsAngle;
                double x1 = centerX + Math.cos(angle) * radius;
                double y1 = centerY + Math.sin(angle) * radius;
                double x2 = centerX + Math.cos(angle + toothWidth) * radius;
                double y2 = centerY + Math.sin(angle + toothWidth) * radius;
                double x3 = centerX + Math.cos(angle + toothWidth) * (radius + toothHeight);
                double y3 = centerY + Math.sin(angle + toothWidth) * (radius + toothHeight);
                double x4 = centerX + Math.cos(angle) * (radius + toothHeight);
                double y4 = centerY + Math.sin(angle) * (radius + toothHeight);
                gc.moveTo(x1, y1);
                gc.lineTo(x2, y2);
                gc.lineTo(x3, y3);
                gc.lineTo(x4, y4);
                gc.closePath();
                gc.stroke();
            }

            // Draw the circle at the center

            double overlappingRadius = radius + gc.getLineWidth();
            gc.setFill(Settings.getBgColor());
            gc.fillOval(centerX - overlappingRadius, centerY - overlappingRadius, overlappingRadius * 2, overlappingRadius * 2);


            double middleCircleRadius = overlappingRadius * 0.6;
            double smallCircleRadius = overlappingRadius * 0.4;
            gc.setFill(Settings.getControlsColor());
            gc.fillOval(centerX - middleCircleRadius, centerY - middleCircleRadius, middleCircleRadius * 2, middleCircleRadius * 2);
            gc.setFill(Settings.getControlsColor());
            gc.fillOval(centerX - smallCircleRadius, centerY - smallCircleRadius, smallCircleRadius * 2, smallCircleRadius * 2);



            gc.restore();
        }

    }

    private void handleXEvent(MouseEvent event){
        System.exit(0);
    }


    private void handleSettingsClicked() throws IOException {


        Stage mainStage = (Stage) mainPane.getScene().getWindow();
        if (mainStage.isFullScreen()){
            mainStage.setFullScreen(false);
        }
        mainStage.close();
        stageBuilder.showStage();



    }


}
