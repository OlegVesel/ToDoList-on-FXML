package com.homeproject.todolist.controllers;

import com.homeproject.todolist.domen.ToDoItem;
import com.homeproject.todolist.service.ToDoItemInCSV;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;

public class NewToDoItemController {
    private static double xOffset = 0;
    private static double yOffset = 0;
    private static Stage stage;
    @FXML
    private AnchorPane newTaskMainPanel;
    @FXML
    private TextField newNameTask;
    @FXML
    private TextArea newDescriptionTask;
    @FXML
    public void closeWindowCreateTask(){
        stage.close();
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
    /**
     * метод собирает данные из полей ввода и создает новую задачу
     */
    @FXML
    public void onCreateNewTask() {
        if (newNameTask.getText().isEmpty() || newDescriptionTask.getText().isEmpty()){
            ErrorWindowController.showMessage("Название новой задачи и ее описание должны быть заполнены!");
            return;
        }
        if (newNameTask.getText().contains("|") || newDescriptionTask.getText().contains("|")) {
            ErrorWindowController.showMessage("В названии и описании задачи не должно быть символа \"|\"");
            return;
        }

        ToDoItem newToDoItem = new ToDoItem(
                0,
                newNameTask.getText(),
                newDescriptionTask.getText(),
                false,
                LocalDate.now(),
                null);
        /**
         * задача сохраняется в CSV файл с помощью сервиса ToDoItemInCSV
         */
        boolean result = ToDoItemInCSV.saveInCSV(newToDoItem);
        if (result)
            stage.close();
        else
            ErrorWindowController.showMessage("Даже сам Один не знает, что произошло при сохранении :(");
    }

    /**
     * метод подготавливает и отображает окно для добавления новой таски
     */
    public static void openWindowForNewTask(){
        stage = new Stage();
        FXMLLoader loader = null;
        Scene scene = null;
        try {
            loader = new FXMLLoader(Paths.get("src/main/resources/com/homeproject/todolist/newToDoItem.fxml").toUri().toURL());
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Создадим новую задачу");
        stage.setMinHeight(320);
        stage.setMinWidth(330);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
