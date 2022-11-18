package com.homeproject.todolist;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StartApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Platform.setImplicitExit(false);
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("mainPage.fxml"));
        Scene scene = new Scene((Parent) fxmlLoader.load(), 650, 600);
        stage.setTitle("Опять работать");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
}