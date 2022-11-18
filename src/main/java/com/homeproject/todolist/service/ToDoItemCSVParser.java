package com.homeproject.todolist.service;

import com.homeproject.todolist.domen.ToDoItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ToDoItemCSVParser {
    public List<ToDoItem> getCurrentTaskFromCSV(List<String> lines) {
        List<ToDoItem> listToDoItems = new ArrayList<>();
        for (String line : lines) {
                listToDoItems.add(parseLine(line));
        }
        return listToDoItems;
    }

    public ToDoItem parseLine(String line) {
        String[] columns = line.split("\\|");
        return new ToDoItem(
                Integer.parseInt(columns[0]),
                columns[1], columns[2],
                Boolean.parseBoolean(columns[3]),
                LocalDate.parse(columns[4]),
                columns[5].equals("null") ? null : LocalDate.parse(columns[5])
        );
    }
}
