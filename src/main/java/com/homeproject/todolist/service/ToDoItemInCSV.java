package com.homeproject.todolist.service;

import com.homeproject.todolist.domen.ToDoItem;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ToDoItemInCSV {
    public static boolean saveInCSV(ToDoItem newItem) {
        Path path = Paths.get("src/main/resources/com/homeproject/csvStorage/activeTasks.csv");
        try (FileWriter fw = new FileWriter(path.toFile(), true)) {
            fw.write(newItem.toString() + "\n");
            fw.flush();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean saveInCSV(List<ToDoItem> toDoItemList) {
        Path path = Paths.get("src/main/resources/com/homeproject/csvStorage/activeTasks.csv");
        try (FileWriter fw = new FileWriter(path.toFile(), true)) {
            for (ToDoItem item : toDoItemList) {
                fw.write(item.toString() + "\n");
            }
            fw.flush();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean editInCSV(ToDoItem editToDoItem) {
        Path path = Paths.get("src/main/resources/com/homeproject/csvStorage/activeTasks.csv");
        ToDoItemCSVParser toDoItemCSVParser = new ToDoItemCSVParser();
        List<ToDoItem> toDoItemList;
        try {
            toDoItemList = toDoItemCSVParser.getCurrentTaskFromCSV(Files.readAllLines(path));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (deleteItemFromCSV(toDoItemList
                .stream()
                .filter(item -> item.getId() == editToDoItem.getId())
                .findFirst()
                .get()))
            return saveInCSV(editToDoItem);
        return false;
    }

    public static boolean deleteItemFromCSV(ToDoItem toDoItem) {
        Path path = Paths.get("src/main/resources/com/homeproject/csvStorage/activeTasks.csv");
        ToDoItemCSVParser toDoItemCSVParser = new ToDoItemCSVParser();
        List<ToDoItem> toDoItemList;
        try {
            toDoItemList = toDoItemCSVParser.getCurrentTaskFromCSV(Files.readAllLines(path));
            FileWriter fw = new FileWriter(path.toFile());
            fw.write("");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        toDoItemList.removeIf(item -> item.getId() == toDoItem.getId());
        if (saveInCSV(toDoItemList))
            return true;
        return false;
    }

    /**
     * метод записывает таску в CSV файл с выполненными задачами (или Архив), перед
     * этим удаляет ее из CSV файла с текущими задачами
     * @param toDoItem - задача, которую нужно сохранить в архив
     * @return true/false в зависимости от успешности сохранения
     */
    public static boolean saveInArchive(ToDoItem toDoItem) {
        //перед тем как сохранять задачу в архив нужно удалить ее из текущих
        Path pathToActive = Paths.get("src/main/resources/com/homeproject/csvStorage/activeTasks.csv");
        ToDoItemCSVParser toDoItemCSVParser = new ToDoItemCSVParser();
        List<ToDoItem> toDoItemActive = null;
        try {
            toDoItemActive = toDoItemCSVParser.getCurrentTaskFromCSV(Files.readAllLines(pathToActive));

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        //если удаление прошло успешно, то сохраняем задачу в Архив
        if (deleteItemFromCSV(toDoItemActive
                .stream()
                .filter(item -> item.getId() == toDoItem.getId())
                .findFirst()
                .get())) {
            Path pathToExecuted = Paths.get("src/main/resources/com/homeproject/csvStorage/executedTasks.csv");
            try (FileWriter fw = new FileWriter(pathToExecuted.toFile(), true)) {
                fw.write(toDoItem.toString() + "\n");
                fw.flush();
            } catch (Exception exception) {
                exception.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }
}
