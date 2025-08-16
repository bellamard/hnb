package com.b2la.hnb.controllers;


import com.b2la.hnb.models.Produit;
import com.b2la.hnb.services.produitService;
import com.b2la.hnb.util.Stockage;
import com.b2la.hnb.util.categoryType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static javafx.application.Platform.runLater;

public class DashboardController {
    @FXML
    private Label username, fonction, dateHeure, PanneauDashboardProduit, PanneauDashboardFacture;
    @FXML
    private Button home,facturation, produit, cloture, depense, utilisateur, btnProduitMod, btnProduitAdd;
    @FXML
    private VBox homeLayout,facturationLayout, produitLayout, depenseLayout, clotureLayout, parametreLayout, loadingLayout;

    @FXML
    TextField research, articleField, prixField;
    @FXML
    TextArea description;
    @FXML
    ComboBox<Integer> nbreArticle;
    @FXML
    ComboBox<categoryType> category;
    @FXML
    TableView<Produit> tableProduit;
    @FXML
    TableColumn<Produit, String>Tarticle, Tdescription, Taction;
    @FXML
    TableColumn<Produit, Integer>Tquantite;
    @FXML
    TableColumn<Produit, Double>Tprix;
    @FXML
    TableColumn<Produit, categoryType>Ttype;

    @FXML
    Button btnAdd, btnAllDelete, btnValider;
    @FXML
    Label msgAlert;

    produitService ps;
    Long idProduit;

    FacturationController fc;

    public void initialize() {
        recoveryUsername();
        viewDateTime();
        cardLayout("");
        for (int i = 0; i < 1000; i++) {
            nbreArticle.getItems().add(i);
        }
        category.getItems().addAll(categoryType.values());
    }

    public void recoveryUsername() {
        Stockage stock = new Stockage();
        username.setText(stock.getUsername());
        fonction.setText(stock.getUsername());
    }

    public void viewDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    LocalDateTime now = LocalDateTime.now();
                    dateHeure.setText(now.format(formatter));
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }


    private void cardLayout(String layout){
        switch (layout){
            case "home":
                homeLayout.setVisible(true);
                produitLayout.setVisible(false);
                depenseLayout.setVisible(false);
                clotureLayout.setVisible(false);
                facturationLayout.setVisible(false);
                parametreLayout.setVisible(false);
                break;
            case "produit":
                homeLayout.setVisible(false);
                produitLayout.setVisible(true);
                depenseLayout.setVisible(false);
                clotureLayout.setVisible(false);
                facturationLayout.setVisible(false);
                parametreLayout.setVisible(false);
                break;
            case "depense":
                homeLayout.setVisible(false);
                produitLayout.setVisible(false);
                depenseLayout.setVisible(true);
                clotureLayout.setVisible(false);
                facturationLayout.setVisible(false);
                parametreLayout.setVisible(false);
                break;
            case "cloture":
                homeLayout.setVisible(false);
                produitLayout.setVisible(false);
                depenseLayout.setVisible(false);
                clotureLayout.setVisible(true);
                facturationLayout.setVisible(false);
                parametreLayout.setVisible(false);
                break;
            case "facture":
                homeLayout.setVisible(false);
                produitLayout.setVisible(false);
                depenseLayout.setVisible(false);
                clotureLayout.setVisible(false);
                facturationLayout.setVisible(true);
                parametreLayout.setVisible(false);
                break;
            case "parametre":
                homeLayout.setVisible(false);
                produitLayout.setVisible(false);
                depenseLayout.setVisible(false);
                clotureLayout.setVisible(false);
                facturationLayout.setVisible(false);
                parametreLayout.setVisible(true);
                break;
            default:
                loadingLayout.setVisible(true);
                homeLayout.setVisible(false);
                produitLayout.setVisible(false);
                depenseLayout.setVisible(false);
                clotureLayout.setVisible(false);
                facturationLayout.setVisible(false);
                parametreLayout.setVisible(false);
                break;

        }
    }
    @FXML
    private void homeView(){
        String layout="home";
        cardLayout(layout);
    }

    @FXML
    private void factureView(){
        String layout="facture";
        cardLayout(layout);
    }

    @FXML
    private void produitView(){
        String layout="produit";
        getProduitAll();
        cardLayout(layout);
    }

    @FXML
    private void depenseView(){
        String layout="depense";
        cardLayout(layout);
    }

    @FXML
    private void clotureView(){
        String layout="cloture";
        cardLayout(layout);
    }

    @FXML
    private void parametreView(){
        String layout="parametre";
        cardLayout(layout);
    }
    @FXML
    private void annulerFacture(){

    }
    @FXML
    private void genererFacture(){

    }
    @FXML
    private void ajouterArticle(){

    }

    private void getAllProduit(){

    }
    @FXML
    public void getProduitAll(){
        ps=new produitService();
        List<Produit> produitList= ps.findAll();
        Tarticle.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Tdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        Tprix.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        Tquantite.setCellValueFactory(new PropertyValueFactory<>("quantiteStock"));
        Ttype.setCellValueFactory(new PropertyValueFactory<>("type"));
        Taction.setCellFactory(col->new TableCell<>(){
            Button btnMod= new Button("Mod");
            Button btnSup= new Button("Sup");
            {

                btnMod.setOnAction(e->{
                    Produit produitItem= getTableView().getItems().get(getIndex());
                    System.out.println(produitItem.getNom());
                    getProduit(produitItem);
                    btnProduitMod.setVisible(true);
                    btnProduitAdd.setVisible(false);
                });

                btnSup.setOnAction(e->{
                    Produit produitItem= getTableView().getItems().get(getIndex());
                    System.out.println(produitItem.getNom());
                    askSupression(produitItem.getId());
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(empty)setGraphic(null);
                else{
                    HBox boxBtn=new HBox(2);
                    boxBtn.getChildren().addAll(btnSup,btnMod);
                    setGraphic(boxBtn);

                }
            }
        });

        Task<ObservableList<Produit>> task= new Task<>(){

            @Override
            protected ObservableList<Produit> call() throws Exception {
                return FXCollections.observableArrayList(produitList);
            }
        };
        task.setOnSucceeded(e->tableProduit.setItems(task.getValue()));
        new Thread(task).start();
        resetProduit();
    }
    private void getProduit(Produit produit){
        idProduit=produit.getId();
        articleField.setText(produit.getNom());
        description.setText(produit.getDescription());
        prixField.setText(String.valueOf(produit.getPrixUnitaire()));
        nbreArticle.setValue(produit.getQuantiteStock());
        category.setValue(produit.getType());
    }

    private void resetProduit(){
        articleField.setText("");
        prixField.setText("");
        description.setText("");
        nbreArticle.setValue(0);
        category.setValue(categoryType.plat);
        btnProduitAdd.setVisible(true);
        btnProduitMod.setVisible(false);
    }

    @FXML
    public void getProduitSearch(){
        String search=research.getText();
        List<Produit> produitList=ps.findAll().stream().filter(produit ->
                produit.getNom().toLowerCase().contains(search.toLowerCase()) ||
                        produit.getDescription().toLowerCase().contains(search.toLowerCase())||
                        String.valueOf(produit.getPrixUnitaire()).toLowerCase().contains(search.toLowerCase())||
                        String.valueOf(produit.getQuantiteStock()).toLowerCase().contains(search.toLowerCase())
        ).collect(Collectors.toList());

        Task<ObservableList<Produit>> task= new Task<ObservableList<Produit>>() {
            @Override
            protected ObservableList<Produit> call() throws Exception {
                return FXCollections.observableArrayList(produitList);
            }
        };
        task.setOnSucceeded(e->tableProduit.setItems(task.getValue()));
        new Thread(task).start();
        resetProduit();
    }
    @FXML
    public void deleteAllProduit(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous Supprimer tout les produits?");
        alert.setContentText("Cliquez sur Ok pour confirmer la suppression.");
        Optional<ButtonType> result= alert.showAndWait();
        if(result.isPresent()&& result.get()== ButtonType.OK){
            List<Produit> produitList=ps.findAll();
            for (Produit produit : produitList) {
                ps.delete(produit.getId());
            }
        }
        getProduitAll();

    }
    @FXML
    private void updateProduit(){
        if(articleField.getText().isEmpty())messageErreur("Votre champs nom est vide!!!");
//        if(description.getText().isEmpty())messageErreur("Votre champs description est vide!!!");
        if(prixField.getText().isEmpty())messageErreur("votre champs prix est vide!!!");
        if(category.getValue().toString().isEmpty())messageErreur("Votre categorie est vide!!!");
        if(nbreArticle.getValue().toString().isEmpty())messageErreur("Votre nombre d'article est vide!!!");
        Produit produit= new Produit();
        produit.setId(idProduit);
        produit.setNom(articleField.getText());
        produit.setDescription(description.getText());
        produit.setType(category.getValue());
        produit.setQuantiteStock(nbreArticle.getValue());
        produit.setPrixUnitaire(Double.parseDouble(prixField.getText()));
        ps= new produitService();
        ps.update(produit);
        getProduitAll();

    }
    @FXML
    private void addProduit(){
        if(articleField.getText().isEmpty())messageErreur("Votre champs nom est vide!!!");
//        if(description.getText().isEmpty())messageErreur("Votre champs description est vide!!!");
        if(prixField.getText().isEmpty())messageErreur("votre champs prix est vide!!!");
        if(category.getValue().toString().isEmpty())messageErreur("Votre categorie est vide!!!");
        if(nbreArticle.getValue().toString().isEmpty())messageErreur("Votre nombre d'article est vide!!!");
        Produit produit= new Produit();
        produit.setNom(articleField.getText());
        produit.setDescription(description.getText());
        produit.setType(category.getValue());
        produit.setQuantiteStock(nbreArticle.getValue());
        produit.setPrixUnitaire(Double.parseDouble(prixField.getText()));
        ps= new produitService();
        ps.save(produit);
        boiteAlert("felicitation vous avez enregistre le produit "+produit.getNom());
        getProduitAll();

    }
    private void boiteAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(message.toUpperCase().substring(0,10));
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void messageErreur(String message){

        runLater(() -> {
            msgAlert.setText(message);
        } );
        throw new RuntimeException(message);
    }


    private void askSupression(Long produitId){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous Supprimer ?");
        alert.setContentText("Cliquez sur Ok pour confirmer la suppression.");
        Optional<ButtonType> result= alert.showAndWait();
        if(result.isPresent()&& result.get()== ButtonType.OK){
            ps.delete(produitId);
            System.out.println("suppression valider");
            getProduitAll();
        }
    }



}

