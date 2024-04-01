package com.example.clockfx.Utils;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageBuilder{

    private final Group root;

    private final Stage stage;
    private Scene scene;



    private StageBuilder(){
        this.root = new Group();
        this.scene = new Scene(root);
        this.stage = new Stage();

    }
    public static StageBuilder newBuilder(){
        return new StageBuilder();
    }

    public StageBuilder setIcon(String path){
        this.stage.getIcons().add(ImageUtils.getImage(path));
        return this;
    }



    public StageBuilder withScene(Scene scene){
        this.scene = scene;
        this.stage.setScene(scene);
        return this;
    }



    public StageBuilder setMaxWidthAndHeight(double width, double height){
        this.stage.setMaxWidth(width);
        this.stage.setMaxHeight(height);
        return this;
    }

    public StageBuilder setFullscreen(boolean isFullscreen){
        this.stage.setFullScreen(isFullscreen);
        return this;
    }
    public StageBuilder setFullScreenHint(String hint){
        this.stage.setFullScreenExitHint(hint);
        return this;
    }
    public StageBuilder setExitFullScreenKey(String combination){


        this.scene.setOnKeyPressed(event -> {
            if(KeyCode.valueOf(event.getCode().toString().toUpperCase()) == KeyCode.valueOf(combination.toUpperCase())){
                this.stage.setFullScreen(!this.stage.isFullScreen());
            }
        });
        return this;
    }

    public StageBuilder removeUpperBar(){
        this.stage.initStyle(StageStyle.UNDECORATED);
        return this;
    }

    public StageBuilder setPosition(double x, double y){
        this.stage.setX(x);
        this.stage.setY(y);
        return this;
    }

    public void showStage(){
        this.stage.show();
        scene.getRoot().requestFocus();
    }


    public void closeStage() {
        this.stage.close();
    }
}
