package com.example.clockfx.Utils;

import com.example.clockfx.MainApplication;
import javafx.fxml.FXMLLoader;

import java.net.URL;

public class CFXMLLoader {


    public static FXMLLoader getFXMLLoader(String fxmlPath){
        return new FXMLLoader(getFXMLResource(fxmlPath));
    }

    public static URL getFXMLResource(String string){
        string = "/" + string;
        return MainApplication.class.getResource(string.concat(".fxml"));
    }


}
