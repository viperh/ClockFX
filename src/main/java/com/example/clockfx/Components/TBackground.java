package com.example.clockfx.Components;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import static javafx.scene.layout.Background.*;

public class TBackground {


    public static Background getBg(Color color){
        return new Background(new BackgroundFill(color, null, null));
    }

}
