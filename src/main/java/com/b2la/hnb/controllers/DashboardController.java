package com.b2la.hnb.controllers;


import com.b2la.hnb.models.Bilan;
import com.b2la.hnb.models.Commande;
import com.b2la.hnb.models.Produit;
import com.b2la.hnb.services.bilanService;
import com.b2la.hnb.services.commandeService;
import com.b2la.hnb.services.facturationService;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static javafx.application.Platform.runLater;

public class DashboardController {
    @FXML
    private Label username, fonction, dateHeure, PanneauDashboardProduit, PanneauDashboardFacture, labelDescription;
    @FXML
    private Button home, facturation, produit, cloture, depense, utilisateur, btnProduitMod, btnProduitAdd;
    @FXML
    private VBox homeLayout, facturationLayout, produitLayout, depenseLayout, clotureLayout, parametreLayout, loadingLayout;

    @FXML
    TextField research, articleField, prixField, researchFacture;
    @FXML
    TextArea description;
    @FXML
    ComboBox<Integer> nbreArticle;

    @FXML
    ComboBox<Integer> produitComboBox;
    @FXML
    ComboBox<categoryType> category;
    @FXML
    TableView<Produit> tableProduit, articlesTable;

    @FXML
    TableColumn<Produit, String> Tarticle, Tdescription, Taction, produitArticle;
    @FXML
    TableColumn<Produit, Integer> Tquantite, quantiteArticle;
    @FXML
    TableColumn<Produit, Double> Tprix, prixUnitaireArticle;
    @FXML
    TableColumn<Produit, categoryType> Ttype;

    @FXML
    Button btnAdd, btnAllDelete, btnValider;
    @FXML
    Label msgAlert;

    produitService ps;
    facturationService fs;
    commandeService cs;
    bilanService bs;
    Long idProduit;
    Double dashBoardFacture = 0.0, dashBoardProduit = 0.0;
    Bilan bilanHebdo;

    String commande;
    Double prixUnitcomm;
    int quantiteComm;
    Long idComm;

    public void initialize() {
        recoveryUsername();
        viewDateTime();
        cardLayout("");
        dashBoardNumber();
        for (int i = 0; i < 1000; i++) {
            nbreArticle.getItems().add(i);
        }
        category.getItems().addAll(categoryType.values());
        ps = new produitService();
        fs = new facturationService();
        dashBoardProduit = (double) ps.numberProduit();
        dashBoardFacture = fs.sommeFacture();
        dashBoardNumber();
        genererBilan();

    }

    private void dashBoardNumber() {
        runLater(() -> {
            PanneauDashboardProduit.setText(String.valueOf(dashBoardProduit));
            PanneauDashboardFacture.setText(String.valueOf(dashBoardFacture));
        });
    }

    public void recoveryUsername() {
        Stockage stock = new Stockage();
        username.setText(stock.getUsername());
        fonction.setText(stock.getFonction());
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


    private void cardLayout(String layout) {
        switch (layout) {
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
    private void homeView() {
        String layout = "home";
        cardLayout(layout);
    }

    @FXML
    private void factureView() {
        String layout = "facture";
        cardLayout(layout);
        getAllProduit();
    }

    @FXML
    private void produitView() {
        String layout = "produit";
        getProduitAll();
        cardLayout(layout);
    }

    @FXML
    private void depenseView() {
        String layout = "depense";
        cardLayout(layout);
    }

    @FXML
    private void clotureView() {
        String layout = "cloture";
        cardLayout(layout);
    }

    @FXML
    private void parametreView() {
        String layout = "parametre";
        cardLayout(layout);
    }

    @FXML
    private void annulerFacture() {

    }

    @FXML
    private void genererFacture() {
        fs = new facturationService();

    }

    @FXML
    private void genererBilan() {
        try {
            bs = new bilanService();
            List<Bilan> bilanList = bs.findAll();
            if(!bilanList.isEmpty()){
                Bilan lastBilan=bs.lastBilan();
                System.out.println(LocalDate.now());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(!LocalDate.now().toString().equals(sdf.format(lastBilan.getDebutBilan()).toString())){
                    ps= new produitService();
                    Bilan bil= new Bilan();
                    bil.setProduits(ps.findAll());
                    bs.save(bil);
                    bil=bs.lastBilan();
                    bilanHebdo=bil;
                }else{
                    bilanHebdo=lastBilan;
                }
            }
            else{
                ps= new produitService();
                Bilan bil= new Bilan();
                bil.setProduits(ps.findAll());
                bs.save(bil);
                bil=bs.lastBilan();
                bilanHebdo=bil;
            }


        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void ajouterArticle() {
        cs = new commandeService();
        Commande comm = new Commande();
        ps = new produitService();
        Produit pro = ps.findById(idComm);
        comm.setProduit(pro);
        comm.setNombre(quantiteComm);
        comm.calculerPrixTotal();
//        comm.setFacturation();

    }

    private void getAllProduit() {
        ps = new produitService();
        List<Produit> prodList = ps.findAll();
        produitArticle.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prixUnitaireArticle.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        quantiteArticle.setCellValueFactory(new PropertyValueFactory<>("quantiteStock"));

        articlesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("Ligne sélectionnée : " + newSelection.getNom() + ", Prix : " + newSelection.getPrixUnitaire());
                idComm = newSelection.getId();
                commande = newSelection.getNom();
                prixUnitcomm = newSelection.getPrixUnitaire();
                researchFacture.setText(newSelection.getNom());
                descriptionFacturation(commande, prixUnitcomm, 0);
                produitComboBox.getItems().removeAll();
                for (int i = 0; i <= newSelection.getQuantiteStock(); i++) {
                    produitComboBox.getItems().add(i);
                }
                produitComboBox.setOnAction(
                        actionEvent -> {
                            quantiteComm = produitComboBox.getValue();
                            descriptionFacturation(commande, prixUnitcomm, quantiteComm);
                        }
                );

            }
        });
        Task<ObservableList<Produit>> task = new Task<>() {

            @Override
            protected ObservableList<Produit> call() throws Exception {
                return FXCollections.observableArrayList(prodList);
            }
        };
        task.setOnSucceeded(e -> articlesTable.setItems(task.getValue()));
        new Thread(task).start();
    }

    @FXML
    public void getProduitAll() {
        ps = new produitService();
        List<Produit> produitList = ps.findAll();
        dashBoardProduit = (double) ps.numberProduit();
        dashBoardNumber();
        Tarticle.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Tdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        Tprix.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        Tquantite.setCellValueFactory(new PropertyValueFactory<>("quantiteStock"));
        Ttype.setCellValueFactory(new PropertyValueFactory<>("type"));
        Taction.setCellFactory(col -> new TableCell<>() {
            Button btnMod = new Button("Mod");
            Button btnSup = new Button("Sup");

            {

                btnMod.setOnAction(e -> {
                    Produit produitItem = getTableView().getItems().get(getIndex());
                    System.out.println(produitItem.getNom());
                    getProduit(produitItem);
                    btnProduitMod.setVisible(true);
                    btnProduitAdd.setVisible(false);
                });

                btnSup.setOnAction(e -> {
                    Produit produitItem = getTableView().getItems().get(getIndex());
                    System.out.println(produitItem.getNom());
                    askSupression(produitItem.getId());
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else {
                    HBox boxBtn = new HBox(2);
                    boxBtn.getChildren().addAll(btnSup, btnMod);
                    setGraphic(boxBtn);

                }
            }
        });

        Task<ObservableList<Produit>> task = new Task<>() {

            @Override
            protected ObservableList<Produit> call() throws Exception {
                return FXCollections.observableArrayList(produitList);
            }
        };
        task.setOnSucceeded(e -> tableProduit.setItems(task.getValue()));
        new Thread(task).start();
        resetProduit();
    }

    private void getProduit(Produit produit) {
        idProduit = produit.getId();
        articleField.setText(produit.getNom());
        description.setText(produit.getDescription());
        prixField.setText(String.valueOf(produit.getPrixUnitaire()));
        nbreArticle.setValue(produit.getQuantiteStock());
        category.setValue(produit.getType());
    }

    private void resetProduit() {
        articleField.setText("");
        prixField.setText("");
        description.setText("");
        nbreArticle.setValue(0);
        category.setValue(categoryType.plat);
        btnProduitAdd.setVisible(true);
        btnProduitMod.setVisible(false);
    }

    @FXML
    public void getProduitSearch() {
        String search = research.getText();
        List<Produit> produitList = ps.findAll().stream().filter(produit ->
                produit.getNom().toLowerCase().contains(search.toLowerCase()) ||
                        produit.getDescription().toLowerCase().contains(search.toLowerCase()) ||
                        String.valueOf(produit.getPrixUnitaire()).toLowerCase().contains(search.toLowerCase()) ||
                        String.valueOf(produit.getQuantiteStock()).toLowerCase().contains(search.toLowerCase())
        ).collect(Collectors.toList());

        Task<ObservableList<Produit>> task = new Task<ObservableList<Produit>>() {
            @Override
            protected ObservableList<Produit> call() throws Exception {
                return FXCollections.observableArrayList(produitList);
            }
        };
        task.setOnSucceeded(e -> tableProduit.setItems(task.getValue()));
        new Thread(task).start();
        resetProduit();
    }

    @FXML
    public void deleteAllProduit() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous Supprimer tout les produits?");
        alert.setContentText("Cliquez sur Ok pour confirmer la suppression.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            List<Produit> produitList = ps.findAll();
            for (Produit produit : produitList) {
                ps.delete(produit.getId());
            }
        }
        getProduitAll();

    }

    @FXML
    private void updateProduit() {
        if (articleField.getText().isEmpty()) messageErreur("Votre champs nom est vide!!!");
//        if(description.getText().isEmpty())messageErreur("Votre champs description est vide!!!");
        if (prixField.getText().isEmpty()) messageErreur("votre champs prix est vide!!!");
        if (category.getValue().toString().isEmpty()) messageErreur("Votre categorie est vide!!!");
        if (nbreArticle.getValue().toString().isEmpty()) messageErreur("Votre nombre d'article est vide!!!");
        Produit produit = new Produit();
        produit.setId(idProduit);
        produit.setNom(articleField.getText());
        produit.setDescription(description.getText());
        produit.setType(category.getValue());
        produit.setQuantiteStock(nbreArticle.getValue());
        produit.setPrixUnitaire(Double.parseDouble(prixField.getText()));
        ps = new produitService();
        ps.update(produit);
        getProduitAll();

    }

    @FXML
    private void addProduit() {
        if (articleField.getText().isEmpty()) messageErreur("Votre champs nom est vide!!!");
        if (prixField.getText().isEmpty()) messageErreur("votre champs prix est vide!!!");
        if (category.getValue().toString().isEmpty()) messageErreur("Votre categorie est vide!!!");
        if (nbreArticle.getValue().toString().isEmpty()) messageErreur("Votre nombre d'article est vide!!!");
        Produit produit = new Produit();
        produit.setNom(articleField.getText());
        produit.setDescription(description.getText());
        produit.setType(category.getValue());
        produit.setQuantiteStock(nbreArticle.getValue());
        produit.setPrixUnitaire(Double.parseDouble(prixField.getText()));
        ps = new produitService();
        ps.save(produit);
        boiteAlert("felicitation vous avez enregistre le produit " + produit.getNom());
        getProduitAll();

    }

    private void boiteAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(message.toUpperCase().substring(0, 10));
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void messageErreur(String message) {

        runLater(() -> {
            msgAlert.setText(message);
        });
        throw new RuntimeException(message);
    }

    private void askSupression(Long produitId) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous Supprimer ?");
        alert.setContentText("Cliquez sur Ok pour confirmer la suppression.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ps.delete(produitId);
            System.out.println("suppression valider");
            getProduitAll();
        }
    }

    private void descriptionFacturation(String nomProduit, double prixUnitaire, int quantite) {
        double total = prixUnitaire * quantite;
        labelDescription.setText(String.format(
                        "Produit : %s\nPrix    : %.2f €\nQuantité: %d pièces\nTotal   : %.2f €",
                        nomProduit, prixUnitaire, quantite, total
                )
        );
    }


}

