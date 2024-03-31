package com.example.clockfx.Controllers;

import com.example.clockfx.Components.TBackground;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockController {

    @FXML private BorderPane mainPane;

    @FXML private Canvas xCanvas;

    @FXML private Label clockLabel;







    @FXML
    public void initialize(){
        drawX();
        setMainPaneDesign();
        setLayoutAndLogic();
        setDesign();
        Platform.runLater(this::setComponentsSize);
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
        mainPane.setBackground(TBackground.getBg(Color.BLACK));
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

        xCanvas.setOnMouseClicked(this::handleXEvent);

    }

    public void setDesign() {
        clockLabel.setTextFill(Color.WHITE);
        clockLabel.setFont(new Font("Arial", 50));
        clockLabel.setBackground(TBackground.getBg(Color.TRANSPARENT));

    }




    private void drawX(){
        GraphicsContext gc = xCanvas.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeLine(0,0, gc.getCanvas().getWidth() - 5, gc.getCanvas().getHeight() - 5);
        gc.strokeLine(0,gc.getCanvas().getWidth() - 5,gc.getCanvas().getHeight() - 5,0);
    }

    private void handleXEvent(MouseEvent event){
        System.exit(0);
    }


}
