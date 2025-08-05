package com.b2la.hnb.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ChartData {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty value;
    private final SimpleStringProperty date;

    public ChartData(String id, String name, int value, String date) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.value = new SimpleIntegerProperty(value);
        this.date = new SimpleStringProperty(date);
    }

    // Getters et setters
    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public int getValue() { return value.get(); }
    public String getDate() { return date.get(); }

    // Property getters
    public SimpleStringProperty idProperty() { return id; }
    public SimpleStringProperty nameProperty() { return name; }
    public SimpleIntegerProperty valueProperty() { return value; }
    public SimpleStringProperty dateProperty() { return date; }
}
