module com.homeproject.todolist {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    exports com.homeproject.todolist;
    exports com.homeproject.todolist.domen;
    exports com.homeproject.todolist.service;
    exports com.homeproject.todolist.controllers;
    opens com.homeproject.todolist to javafx.fxml;
    opens com.homeproject.todolist.domen to javafx.fxml;
    opens com.homeproject.todolist.controllers to javafx.fxml;
}
