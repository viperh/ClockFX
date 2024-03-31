package com.example.clockfx.Utils;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageBuilder{

    private final Group root;

    private final Scene scene;
    private Stage stage;

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

    public StageBuilder removeUpperBar(){
        this.stage.initStyle(StageStyle.UNDECORATED);
        return this;
    }

    public StageBuilder showStage(){
        this.stage.show();
        return this;
    }



}
