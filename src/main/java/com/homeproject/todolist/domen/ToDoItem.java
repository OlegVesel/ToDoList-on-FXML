package com.homeproject.todolist.domen;

import java.time.LocalDate;
import java.util.Objects;

public class ToDoItem {
    private final int id;
    private String name;
    private String description;
    private boolean isExecute;
    private final LocalDate dateCreate;
    private LocalDate dateExecute;

    @Override
    public int hashCode() {
        return Math.abs(Objects.hash(name, description, dateCreate, System.nanoTime()));
    }

    public ToDoItem(int id, String name, String description, boolean isExecute, LocalDate dateCreate, LocalDate dateExecute) {
        this.name = name;
        this.description = description;
        this.isExecute = isExecute;
        this.dateCreate = dateCreate;
        this.dateExecute = dateExecute;
        if (id == 0)
            this.id = hashCode();
        else
            this.id = id;
    }

    @Override
    public String toString() {
        return  id +"|"+ name +  "|" + description + "|" + isExecute + "|" + dateCreate + "|" + dateExecute;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExecute() {
        return isExecute;
    }

    public void setExecute(boolean execute) {
        isExecute = execute;
    }

    public LocalDate getDateExecute() {
        return dateExecute;
    }

    public void setDateExecute(LocalDate dateExecute) {
        this.dateExecute = dateExecute;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }
}
