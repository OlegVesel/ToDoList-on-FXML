package com.homeproject.todolist.controllers;

import com.homeproject.todolist.domen.ToDoItem;
import com.homeproject.todolist.service.ToDoItemInCSV;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;

import java.time.LocalDate;

public class ShortItemController {
    @FXML
    private Label dateCreate;
    @FXML
    private Label nameToDo;
    @FXML
    private AnchorPane shortItem;
    private ToDoItem toDoItem;

    public void setData(ToDoItem toDoItem){
        this.toDoItem = toDoItem;
        dateCreate.setText(toDoItem.getDateCreate().toString());
        nameToDo.setText(toDoItem.getName());
        Tooltip tooltip = new Tooltip(toDoItem.getName());
        tooltip.setShowDelay(Duration.seconds(1.0));
        nameToDo.setTooltip(tooltip);
    }

    public void showMore() {
        Parent root = shortItem.getScene().getRoot();
        root.setEffect(new BoxBlur(5d, 5d, 1));
        ChangeToDoItemController changeWindow = new ChangeToDoItemController(this.toDoItem, true);
        root.setEffect(new BoxBlur(0, 0, 0));
    }

    public void executeTask(){
        this.toDoItem.setExecute(true);
        this.toDoItem.setDateExecute(LocalDate.now());
        if (ToDoItemInCSV.saveInArchive(this.toDoItem)){
            ErrorWindowController.showMessage("Задача успешно выполнена");
            TilePane parent = (TilePane) shortItem.getParent();
            parent.getChildren().remove(this.shortItem);
        } else
            ErrorWindowController.showMessage("При изменении что то пошло не так");
    }
}
