package com.example.clockfx;


import com.example.clockfx.Controllers.ClockController;
import com.example.clockfx.Utils.CFXMLLoader;
import com.example.clockfx.Utils.StageBuilder;
import com.example.clockfx.Utils.UpdateComponents;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;

@SuppressWarnings("unused")
public class MainApplication extends Application {
    @Override
    public void start(Stage mainStage) throws IOException {

        FXMLLoader fxmlLoader = CFXMLLoader.getFXMLLoader("Clock");

        Parent parent = fxmlLoader.load();
        ClockController clockController = fxmlLoader.getController();

        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        StageBuilder mainBuilder = StageBuilder.newBuilder()
                .withScene(new Scene(parent,width, height, Color.BLACK))
                .setMaxWidthAndHeight(width, height)
                .setFullscreen(true)
                .setFullScreenHint("Press f to change fullscreen state!")
                .seExitFullScreenKey("f")
                .removeUpperBar()
                .setIcon("icons/taskIcon.png");





        Timer timer = new Timer();
        timer.schedule(new UpdateComponents(clockController), 0, 10);

        mainBuilder.showStage();

    }

    public static void main(String[] args) {
        launch(args);
    }
}