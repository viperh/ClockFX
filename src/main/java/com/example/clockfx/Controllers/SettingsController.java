package com.example.clockfx.Controllers;

import com.example.clockfx.Components.TBackground;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class SettingsController {


    @FXML private GridPane grid;
    private Color bgColor = Color.BLACK;
    private Color controlsColor = Color.RED;

    @FXML
    public void initialize(){
        setMainPaneDesign();

    }

    public void setMainPaneDesign(){
        this.grid.setBackground(TBackground.getBg(bgColor));
    }

}
