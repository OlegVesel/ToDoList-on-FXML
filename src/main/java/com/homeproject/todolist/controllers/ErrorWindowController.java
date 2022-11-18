package com.homeproject.todolist.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.nio.file.Paths;

public class ErrorWindowController {
    @FXML
    private ImageView closeImage;
    @FXML
    private ImageView errorImage;
    @FXML
    private Label errorTextPlace;
    @FXML
    private AnchorPane top;
    @FXML
    public void closeWindow(){
        stage.close();
    };

    private static Stage stage;
    private static double xOffset;
    private static double yOffset;
    private static String errorText;
    public ErrorWindowController(){
    };
    public static void showMessage(String error){
        errorText = error;
        stage = new Stage();
        FXMLLoader loader = null;
        Scene scene = null;
        try {
            loader = new FXMLLoader(Paths.get("src/main/resources/com/homeproject/todolist/errorWindow.fxml").toUri().toURL());
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMinHeight(200);
        stage.setMinWidth(400);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void initialize(){
        errorTextPlace.setText(errorText);
    }

    @FXML
    public void onMousePressed(MouseEvent event){
        if (event.isPrimaryButtonDown()){
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        }
    }
    @FXML
    public void onMouseDragged(MouseEvent event){
        if (event.isPrimaryButtonDown()){
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        }
    }
}
