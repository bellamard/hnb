package com.b2la.hnb.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class DashboardController {
    @FXML
    private LineChart<String, Number> salesChart;
    @FXML
    private PieChart clientsChart;
    @FXML
    private BarChart<String, Number> conversionChart;
    @FXML
    private TableView<ChartData> dataTable;

    @FXML
    public void initialize() {
        setupSalesChart();
        setupClientsChart();
        setupConversionChart();
        setupDataTable();
    }

    private void setupSalesChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Jan", 2000));
        series.getData().add(new XYChart.Data<>("Fév", 3000));
        series.getData().add(new XYChart.Data<>("Mar", 4500));
        series.getData().add(new XYChart.Data<>("Avr", 2500));
        series.getData().add(new XYChart.Data<>("Mai", 6000));
        series.getData().add(new XYChart.Data<>("Jun", 7000));

        salesChart.getData().add(series);
        salesChart.setTitle("");
    }

    private void setupClientsChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Nouveaux", 35),
                new PieChart.Data("Réguliers", 45),
                new PieChart.Data("Inactifs", 20));

        clientsChart.setData(pieChartData);
    }

    private void setupConversionChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Site web", 18));
        series.getData().add(new XYChart.Data<>("Mobile", 24));
        series.getData().add(new XYChart.Data<>("Boutique", 32));
        series.getData().add(new XYChart.Data<>("Réseaux", 12));

        conversionChart.getData().add(series);
    }

    private void setupDataTable() {
        ObservableList<ChartData> data = FXCollections.observableArrayList(
                new ChartData("1", "Produit A", 1250, "2023-01-15"),
                new ChartData("2", "Produit B", 980, "2023-01-16"),
                new ChartData("3", "Produit C", 1560, "2023-01-17"),
                new ChartData("4", "Produit D", 2100, "2023-01-18"),
                new ChartData("5", "Produit E", 750, "2023-01-19"));

        dataTable.setItems(data);
    }
}

