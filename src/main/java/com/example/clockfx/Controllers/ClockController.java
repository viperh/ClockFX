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

    @FXML private Label quoteLabel;

    @FXML private AnchorPane inner;



    @FXML
    public void initialize(){
        drawX();
        setMainPaneDesign();
        setLayoutAndLogic();
        setDesign();
    }

    public void setComponentsSize(String quote){
        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        int width =  (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        int maxLabelWidth = width * 8 / 10;
        int maxLabelHeight = height * 8 / 10;
        Text clockText = new Text("00:00:00");
        Text quoteText = new Text(quote);
        clockText.setFont(new Font("Arial",30));
        quoteText.setFont(new Font("Arial", 10));

        while((clockText.getLayoutBounds().getWidth() < maxLabelWidth) && (clockText.getLayoutBounds().getHeight() + quoteText.getLayoutBounds().getHeight() < maxLabelHeight)){
            clockText.setFont(new Font(clockText.getFont().getSize() + 1));
            quoteText.setFont(new Font(quoteText.getFont().getSize() + 1));
        }


    }

    public void updateComponents(String quote){
        Platform.runLater(() -> {
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            clockLabel.setText(time);
            if (clockLabel.getText().split(":")[2].equals("00") && quote != null){
                quoteLabel.setText(quote);
                setComponentsSize(quote);
            }
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
        AnchorPane.setTopAnchor(clockLabel, 10.0);
        AnchorPane.setBottomAnchor(clockLabel, 0.0);
        AnchorPane.setLeftAnchor(clockLabel, 0.0);
        AnchorPane.setRightAnchor(clockLabel, 0.0);
        // quoteLabel
        AnchorPane.setTopAnchor(quoteLabel, 0.0);
        AnchorPane.setBottomAnchor(quoteLabel, 10.0);
        AnchorPane.setLeftAnchor(quoteLabel, 0.0);
        AnchorPane.setRightAnchor(quoteLabel, 0.0);


        inner.setBackground(TBackground.getBg(Color.TRANSPARENT));
        xCanvas.setOnMouseClicked(this::handleXEvent);

    }

    public void setDesign() {
        clockLabel.setTextFill(Color.WHITE);
        clockLabel.setFont(new Font("Arial", 50));
        clockLabel.setBackground(TBackground.getBg(Color.TRANSPARENT));

        quoteLabel.setTextFill(Color.WHITE);
        quoteLabel.setFont(new Font("Arial", 50));
        quoteLabel.setBackground(TBackground.getBg(Color.TRANSPARENT));

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
