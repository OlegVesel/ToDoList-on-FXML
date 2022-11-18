package com.homeproject.todolist.controllers;

import com.homeproject.todolist.domen.ToDoItem;
import com.homeproject.todolist.service.ToDoItemInCSV;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;

public class ChangeToDoItemController {
    private static Stage stage;
    private static ToDoItem currentToDoItem;
    private static double xOffset;
    private static double yOffset;
    //режим работы true - изменяем задачи, false - смотрим задачи из архива
    private static boolean typeWork;

    @FXML
    private Button cancelChangeTaskButton;

    @FXML
    private DatePicker createDate;

    @FXML
    private Button changeTaskButton;

    @FXML
    private TextArea descriptionTask;

    @FXML
    private AnchorPane editMainPanel;

    @FXML
    private DatePicker executeDate;

    @FXML
    private TextField nameTask;

    @FXML
    private CheckBox statusCheck;

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

    @FXML
    public void closeWindow(){
        stage.close();
    }

    @FXML
    public void changeToDoItem(){
        if (nameTask.getText().isEmpty() || descriptionTask.getText().isEmpty()){
            ErrorWindowController.showMessage("Оставлять пустыми название или описание нельзя!");
            return;
        }

        currentToDoItem.setName(nameTask.getText());
        currentToDoItem.setDescription(descriptionTask.getText());
        if (statusCheck.isSelected()){
            currentToDoItem.setExecute(statusCheck.isSelected());
            currentToDoItem.setDateExecute(LocalDate.now());
            if (ToDoItemInCSV.saveInArchive(currentToDoItem))
                stage.close();
        } else if (ToDoItemInCSV.editInCSV(currentToDoItem))
            stage.close();
    }

    public ChangeToDoItemController(){
    }
    public ChangeToDoItemController(ToDoItem toDoItem, boolean type){
        typeWork = type;
        currentToDoItem = toDoItem;
        stage = new Stage();
        FXMLLoader loader = null;
        Scene scene = null;
        try {
            loader = new FXMLLoader(Paths.get("src/main/resources/com/homeproject/todolist/changeToDoItem.fxml").toUri().toURL());
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMinHeight(330);
        stage.setMinWidth(330);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }
    @FXML
    public void initialize(){
        if (currentToDoItem.isExecute()){
            statusCheck.setSelected(true);
            statusCheck.setText("Задача уже выполнена, иди отдохни");
            statusCheck.setTextFill(Paint.valueOf("#498C89"));
        } else {
            statusCheck.setSelected(false);
            statusCheck.setText("Еще не все!");
            statusCheck.setTextFill(Paint.valueOf("red"));
        }

        nameTask.setText(currentToDoItem.getName());
        descriptionTask.setText(currentToDoItem.getDescription());
        createDate.setValue(currentToDoItem.getDateCreate());
        executeDate.setValue(currentToDoItem.getDateExecute());
        createDate.setEditable(false);
        executeDate.setEditable(false);
        if (typeWork){
            nameTask.setEditable(true);
            descriptionTask.setEditable(true);
            changeTaskButton.setDisable(false);
        } else{
            nameTask.setEditable(false);
            descriptionTask.setEditable(false);
            changeTaskButton.setDisable(true);
        }
    }

}
