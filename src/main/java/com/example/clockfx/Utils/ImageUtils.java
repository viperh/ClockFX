package com.example.clockfx.Utils;

import com.example.clockfx.MainApplication;
import javafx.scene.image.Image;

public abstract class ImageUtils {


    public static Image getImage(String name){

        return new Image(MainApplication.class.getResourceAsStream("/" + name));

    }

}
