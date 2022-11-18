package com.homeproject.todolist.controllers;

import com.homeproject.todolist.domen.ToDoItem;
import com.homeproject.todolist.service.ToDoItemCSVParser;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShortPageController implements Initializable {
    private static double xOffset = 0;
    private static double yOffset = 0;
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private static List<ToDoItem> toDoItemList;
    @FXML
    private TilePane tilePane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private  AnchorPane topPane;
    @FXML
    private AnchorPane shortPage;

    public static void getDataFromCSV(){
        ToDoItemCSVParser toDoItemCSVParser = new ToDoItemCSVParser();
        try {
            Path path = Paths.get("src/main/resources/com/homeproject/csvStorage/activeTasks.csv");
            toDoItemList = toDoItemCSVParser.getCurrentTaskFromCSV(Files.readAllLines(path));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onMousePressed(MouseEvent event) {
        if (event.isPrimaryButtonDown()) {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        }
    }

    @FXML
    public void onMouseDragged(MouseEvent event) {
        if (event.isPrimaryButtonDown()) {
            shortPage.getScene().getWindow().setX(event.getScreenX() - xOffset);
            shortPage.getScene().getWindow().setY(event.getScreenY() - yOffset);
        }
    }

    public void draw(){
        getDataFromCSV();
        for (AnchorPane item : getListItems()){
            tilePane.getChildren().add(item);
        }
    }

    public List<AnchorPane> getListItems(){
        List<AnchorPane> listItems = new ArrayList<>();
        try {
            for (ToDoItem toDoItem : toDoItemList) {
                FXMLLoader loader = new FXMLLoader(ShortPageController.class.getResource("/com/homeproject/todolist/shortItem.fxml"));
                AnchorPane item = loader.load();
                listItems.add(item);
                ShortItemController controller = loader.getController();
                controller.setData(toDoItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null; // TODO: 24.05.2022 здесь нужно выбросить окно с ошибкой
        }
        return FXCollections.observableArrayList(listItems);
    }

    public void switchView(MouseEvent event) {
        if (event.getClickCount() > 1 && event.getButton().equals(MouseButton.PRIMARY)) {
            try {
                parent = FXMLLoader.load(ShortPageController.class.getResource("/com/homeproject/todolist/mainPage.fxml"));
                stage = (Stage) shortPage.getScene().getWindow();
                scene = new Scene(parent);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        draw();
    }
}
