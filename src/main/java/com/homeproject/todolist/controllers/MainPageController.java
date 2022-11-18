package com.homeproject.todolist.controllers;

import com.homeproject.todolist.domen.ToDoItem;
import com.homeproject.todolist.service.ToDoItemCSVParser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class MainPageController {
    private static double xOffset = 0;
    private static double yOffset = 0;

    private Parent parent;
    private Scene scene;
    private Stage stage;

    @FXML
    private AnchorPane topPanel;
    @FXML
    private AnchorPane leftPanel;
    @FXML
    private BorderPane mainPanel;
    @FXML
    private ImageView imageClose;
    @FXML
    private AnchorPane centerPane;
    @FXML
    private javafx.scene.control.MenuBar menuBar;

    @FXML
    private void addAppToTray() {
        ToDoItemCSVParser toDoItemCSVParser = new ToDoItemCSVParser();
        try {
            List<ToDoItem> currentTaskFromCSV = toDoItemCSVParser.getCurrentTaskFromCSV(Files.readAllLines(Paths.get("src/main/resources/com/homeproject/csvStorage/activeTasks.csv")));
            int size = currentTaskFromCSV.size();
            String label = "Осталось задач: " + size;
            ((Stage) mainPanel.getScene().getWindow()).hide();
            Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
            if (!SystemTray.isSupported()) {
                System.out.println("No system tray support, application exiting.");
                Platform.exit();
            }

            // set up a system tray icon.
            SystemTray tray = SystemTray.getSystemTray();
            URL imageLoc =
                    Paths.get("src/main/resources/com/homeproject/img/trayIcon.png").toUri().toURL();
            Image image = ImageIO.read(imageLoc);
            TrayIcon trayIcon = new TrayIcon(image);
            trayIcon.setImageAutoSize(true);
            tray.add(trayIcon);

            trayIcon.addActionListener(event -> Platform.runLater(() -> {
                if (mainPanel.getScene().getWindow() != null) {
                    ((Stage) mainPanel.getScene().getWindow()).show();
                    ((Stage) mainPanel.getScene().getWindow()).toFront();
                    tray.remove(trayIcon);
                }
            }));

            java.awt.MenuItem openItem = new java.awt.MenuItem(label);
            openItem.addActionListener(event -> Platform.runLater(() -> {
                if (mainPanel.getScene().getWindow() != null) {
                    ((Stage) mainPanel.getScene().getWindow()).show();
                    ((Stage) mainPanel.getScene().getWindow()).toFront();
                    tray.remove(trayIcon);
                }
            }));

            Font defaultFont = Font.decode(null);
            Font boldFont = defaultFont.deriveFont(Font.BOLD);
            openItem.setFont(boldFont);

            java.awt.MenuItem exitItem = new java.awt.MenuItem("Выйти из программы");
            exitItem.addActionListener(event -> {
                Platform.exit();
                tray.remove(trayIcon);
            });

            final PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);

        } catch (AWTException | IOException e) {
            System.out.println("Unable to init system tray");
            e.printStackTrace();
        }
    }

    @FXML
    public void onMousePressed(MouseEvent event) {
        if (event.isPrimaryButtonDown()) {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        }
        new Button();
    }

    @FXML
    public void onMouseDragged(MouseEvent event) {
        if (event.isPrimaryButtonDown()) {
            mainPanel.getScene().getWindow().setX(event.getScreenX() - xOffset);
            mainPanel.getScene().getWindow().setY(event.getScreenY() - yOffset);
        }
    }

    @FXML
    protected void initialize() {
        showToDoList();
    }

    @FXML
    public void closeApplication() {
        Platform.exit();
    }

    @FXML
    public void showToDoList() {
        try {
            Parent todoList = FXMLLoader.load(MainPageController.class.getResource("/com/homeproject/todolist/toDoListView.fxml"));
            centerPane.getChildren().removeAll();
            centerPane.getChildren().setAll(todoList);
            AnchorPane.setRightAnchor(todoList,0d);
            AnchorPane.setLeftAnchor(todoList,0d);
            AnchorPane.setTopAnchor(todoList, 0d);
            AnchorPane.setBottomAnchor(todoList, 0d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showButtonPage() {
        try {
            Parent parent = FXMLLoader.load(MainPageController.class.getResource("/com/homeproject/todolist/button.fxml"));
            centerPane.getChildren().removeAll();
            centerPane.getChildren().setAll(parent);
            AnchorPane.setRightAnchor(parent,0d);
            AnchorPane.setLeftAnchor(parent,0d);
            AnchorPane.setTopAnchor(parent, 0d);
            AnchorPane.setBottomAnchor(parent, 0d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchView() {
        try {
            parent = FXMLLoader.load(MainPageController.class.getResource("/com/homeproject/todolist/shortPage.fxml"));
            stage = (Stage) mainPanel.getScene().getWindow();
            scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMenu(MouseEvent event) {
        /**
         * Метод для создания всплывающего меню по нажатию правой кнопки по верхней панели
         */
//        if (event.getButton().equals(MouseButton.SECONDARY)){
//            ContextMenu contextMenu = new ContextMenu();
//            javafx.scene.control.MenuItem item = new javafx.scene.control.MenuItem("Test 1");
//            javafx.scene.control.MenuItem item2 = new javafx.scene.control.MenuItem("Test 2");
//            item.setOnAction(event1 -> {
//                System.out.println("test 1");
//            });
//            item2.setOnAction(event2 -> {
//                System.out.println("test 2");
//            });
//            contextMenu.getItems().addAll(item, item2);
//            topPanel.setOnContextMenuRequested(mouseClickedEvent -> {
//                contextMenu.show(topPanel, mouseClickedEvent.getScreenX(), mouseClickedEvent.getScreenY());
//            });
//
//            System.out.println("true");
//        }
    }
}