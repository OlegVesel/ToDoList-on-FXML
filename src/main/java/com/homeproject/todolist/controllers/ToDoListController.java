package com.homeproject.todolist.controllers;

import com.homeproject.todolist.domen.ToDoItem;
import com.homeproject.todolist.service.ToDoItemCSVParser;
import com.homeproject.todolist.service.ToDoItemInCSV;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class ToDoListController {
    private static ContextMenu menu;
    @FXML
    private ToggleGroup categoryGroup;
    @FXML
    private RadioButton inWorkRB;
    @FXML
    private RadioButton isExecuteRB;
    @FXML
    private Button newTaskBtn;
    @FXML
    private TableView<ToDoItem> tableToDoItems;
    @FXML
    protected void initialize() {
        updateToDoList();
    }
    @FXML
    public void choiseRow(MouseEvent event) {
        if (menu != null)
            menu.hide();
        TableView.TableViewSelectionModel<ToDoItem> selectionModel = tableToDoItems.getSelectionModel();
        ToDoItem selectedItem = selectionModel.getSelectedItem();
        if (selectedItem == null)
            return;
        if (event.getClickCount() == 2 && event.getButton().compareTo(MouseButton.PRIMARY) == 0) {
            Parent root = tableToDoItems.getScene().getRoot();
            root.setEffect(new BoxBlur(5d, 5d, 1));
            ChangeToDoItemController changeWindow = new ChangeToDoItemController(selectedItem, inWorkRB.isSelected());
            root.setEffect(new BoxBlur(0, 0, 0));
            updateToDoList();
        }
        if (event.getButton().compareTo(MouseButton.SECONDARY) == 0) {
            ContextMenu contextMenu = prepareContextMenu(selectedItem);
            tableToDoItems.setOnContextMenuRequested(event1 ->
                    contextMenu.show(tableToDoItems, event1.getScreenX(), event1.getScreenY()));
        }
    }

    public void updateToDoList() {
        ToDoItemCSVParser toDoItemCSVParser = new ToDoItemCSVParser();
        Path path = null;
        if (inWorkRB.isSelected())
            path = Paths.get("src/main/resources/com/homeproject/csvStorage/activeTasks.csv");
        if (isExecuteRB.isSelected())
            path = Paths.get("src/main/resources/com/homeproject/csvStorage/executedTasks.csv");
        List<ToDoItem> toDoItems = null;
        try {
            toDoItems = toDoItemCSVParser.getCurrentTaskFromCSV(Files.readAllLines(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tableToDoItems.getColumns().isEmpty()) {
            TableColumn<ToDoItem, String> nameColumn = new TableColumn<>("????????????");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<ToDoItem, String> descriptionColumn = new TableColumn<>("??????????????????");
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            tableToDoItems.getColumns().addAll(nameColumn, descriptionColumn);
        }
        ObservableList<ToDoItem> list = FXCollections.observableArrayList(toDoItems);
        if (toDoItems != null)
            tableToDoItems.setItems(list);
    }

    public ContextMenu prepareContextMenu(ToDoItem selectedItem) {
        menu = new ContextMenu();
        MenuItem itemExecute = new MenuItem("????????????????, ?????? ??????????????????????");
        itemExecute.getStyleClass().add("menuItem");
        itemExecute.setOnAction(actionEvent -> {
            selectedItem.setExecute(true);
            selectedItem.setDateExecute(LocalDate.now());
            if (ToDoItemInCSV.saveInArchive(selectedItem))
                ErrorWindowController.showMessage("???????????? ?????????????? ??????????????????");
            else
                ErrorWindowController.showMessage("?????? ?????????????????? ?????? ???? ?????????? ???? ??????");
            updateToDoList();
        });
        MenuItem itemDelete = new MenuItem("?????????????? ????????????");
        itemDelete.getStyleClass().add("menuItem");
        itemDelete.setOnAction(actionEvent -> {
            if (ToDoItemInCSV.deleteItemFromCSV(selectedItem))
                ErrorWindowController.showMessage("???????????? ?????????????? ??????????????");
            else
                ErrorWindowController.showMessage("?????? ???????????????? ?????? ???? ?????????? ???? ??????");
            updateToDoList();
        });
        menu.getItems().addAll(itemDelete, itemExecute);
        return menu;
    }

    /**
     * ?????????? ?????????????????? ???????????????????? ???????? ?????? ???????????????? ?????????? ??????????
     */
    @FXML
    protected void onCreateNewTask() {
        Parent root = tableToDoItems.getScene().getRoot();
        root.setEffect(new BoxBlur(5d, 5d, 1));
        NewToDoItemController.openWindowForNewTask();
        root.setEffect(new BoxBlur(0, 0, 0));
        updateToDoList();
    }

    @FXML
    public void showTaskInWork() {
        updateToDoList();
    }

    @FXML
    public void showExecutedTask() {
        updateToDoList();
    }

}
