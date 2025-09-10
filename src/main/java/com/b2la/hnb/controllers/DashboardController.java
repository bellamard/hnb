package com.b2la.hnb.controllers;


import com.b2la.hnb.HelloApplication;
import com.b2la.hnb.models.*;
import com.b2la.hnb.services.*;
import com.b2la.hnb.util.Etat;
import com.b2la.hnb.util.Stockage;
import com.b2la.hnb.util.categoryType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static javafx.application.Platform.runLater;

public class DashboardController {
    @FXML
    private Label username, fonction, dateHeure, PanneauDashboardProduit, PanneauDashboardFacture, labelDescription, factureRef, totalFactureLabel, countFacture, sumFacture, msgAlertSpent, apercusSpentText, bilanDate, bilanRef, bilanFactureElement, bilanDepenseElement, bilanSommeFacture, bilanSommeDepense, bilansTotal;
    @FXML
    private Button home, facturation, produit, cloture, depense, utilisateur, btnProduitMod, btnProduitAdd, btnAddCommande, btnValiderFacture, btnModifierSpent, btnCreateSpent;
    @FXML
    private VBox homeLayout, facturationLayout, produitLayout, depenseLayout, clotureLayout, parametreLayout, loadingLayout, boxBilanItem;

    @FXML
    TextField research, articleField, prixField, researchFacture, fieldVerifier, searchSpent, titleSpent, priceSpent, forSpent, researchBilan;
    @FXML
    TextArea description, motifSpent;
    @FXML
    ComboBox<Integer> nbreArticle;

    @FXML
    ComboBox<Integer> produitComboBox;
    @FXML
    ComboBox<categoryType> category;
    @FXML
    TableView<Produit> tableProduit, articlesTable;
    @FXML
    TableView<Depense> spentTable;
    @FXML
    TableColumn<Depense, Date> colSpentDate;
    @FXML
    TableColumn<Depense, Double> colSpentPrice;
    @FXML
    TableColumn<Depense, String> colSpentTitle, colSpentMotif, colSpentFor, colSpentAuthor, colSpentAction;

    @FXML
    TableView<Commande> articlesCommande;
    @FXML
    TableColumn<Commande, String> produitCol1, actionCol1;
    @FXML
    TableColumn<Commande, Double> prixUnitaireCol1, totalCol1;
    @FXML
    TableColumn<Commande, Integer> quantiteCol1;

    @FXML
    TableColumn<Produit, String> Tarticle, Tdescription, Taction, produitArticle;
    @FXML
    TableColumn<Produit, Integer> Tquantite, quantiteArticle;
    @FXML
    TableColumn<Produit, Double> Tprix, prixUnitaireArticle;
    @FXML
    TableColumn<Produit, categoryType> Ttype;

    @FXML
    TableView<Bilan> bilanTable;

    @FXML
    TableColumn<Bilan, Date> colBilanDate;
    @FXML
    TableColumn<Bilan, Long> colBilanRef;
    @FXML
    TableColumn<Bilan, String> colBilanFacture, colBilanDepense, colBilanTotal;
    @FXML
    TableColumn<Bilan, String> colBilanAction;


    @FXML
    Button btnAdd, btnAllDelete, btnValider;
    @FXML
    Label msgAlert;

    produitService ps;
    facturationService fs;
    commandeService cs;
    bilanService bs;
    utilisateurService us;
    Long idProduit;
    Double dashBoardFacture = 0.0, dashBoardProduit = 0.0;
    Bilan bilanHebdo;

    String commande;
    Double prixUnitcomm;
    int quantiteComm;
    Long idComm;
    Long idUser;
    Facturation facture;
    int nbreCommande = 0;
    double totalPrix = 0;
    double revenueToday;
    double factureTotal;
    double depenseTotal;
    double finalTotal, finalDepense, finalFacture;


    depenseService ds;


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
        ds = new depenseService();
        us = new utilisateurService();
        dashBoardProduit = (double) ps.numberProduit();
        dashBoardFacture = fs.sommeFacture();
        dashBoardNumber();
        genererBilan();
        priceSpent.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
        try {
            Thread thread = new Thread(this::homeView);
            thread.sleep(2000);
            thread.start();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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
        idUser = Long.parseLong(stock.getId());
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
        affichage();
    }

    @FXML
    private void factureView() {
        String layout = "facture";
        cardLayout(layout);
        getAllProduit();
        genererFacture();
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
        getAllspent();
        cardLayout(layout);
    }

    @FXML
    private void clotureView() {
        String layout = "cloture";
        getBilanHebdo();
        getAllCloture();
        cardLayout(layout);
    }

    @FXML
    private void parametreView() {
        String layout = "parametre";
        cardLayout(layout);
    }

    @FXML
    void onClose() {
        System.exit(0);
    }

    @FXML
    private void annulerFacture() {
        fs = new facturationService();
        fs.delete(facture.getId());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer la commande");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer la facture :" + facture.getCodeReference());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Logique de suppression
                genererFacture();
                resetFactureProduit();
                getFactureAllCommande();

            } catch (Exception e) {
                // Alerte d'erreur
                throw new RuntimeException(e);
            }
        }


    }

    @FXML
    private void validerFacture() {
        if (nbreCommande <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("vous n'avez pas fait des commande");
            alert.setHeaderText("probleme facturation");
            alert.showAndWait();
            throw new RuntimeException("vous n'avez pas fait des commande");
        }
        facture.setTtc(totalPrix);
        facture.setEtat(Etat.Payée);
        fs = new facturationService();
        fs.update(facture);
        facture = fs.findById(facture.getId());
        facture.getCommandes().forEach(commande -> {
            Produit produit = commande.getProduit();
            produit.setQuantiteStock(produit.getQuantiteStock() - commande.getNombre());
            ps = new produitService();
            ps.update(produit);
        });
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("impression-view.fxml"));
            Parent rootPrint = loader.load();
            ImpressionController ic = loader.getController();
            ic.imprimer(facture);
            ic.lancerImpression(rootPrint);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        getAllProduit();
        genererFacture();
        resetFactureProduit();
        getFactureAllCommande();
        initialize();

    }


    @FXML
    private void genererFacture() {
        nbreCommande = 0;
        totalPrix = 0.0;
        fs = new facturationService();
        us = new utilisateurService();
        facture = new Facturation();
        facture.setBilan(bilanHebdo);
        facture.setCodeReference(Long.parseLong(fs.generateNumericCode()));
        facture.setEtat(Etat.Non_payée);
        facture.setUtilisateur(us.findById(idUser));
        fs.save(facture);
        factureRef.setText(facture.getCodeReference().toString());

    }

    @FXML
    private void genererBilan() {
        try {
            bs = new bilanService();
            List<Bilan> bilanList = bs.findAll();
            if (!bilanList.isEmpty()) {
                Bilan lastBilan = bs.lastBilan();
                System.out.println(LocalDate.now());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (!LocalDate.now().toString().equals(sdf.format(lastBilan.getDebutBilan()).toString())) {
                    ps = new produitService();
                    Bilan bil = new Bilan();
                    bil.setProduits(ps.findAll());
                    bs.save(bil);
                    bil = bs.lastBilan();
                    bilanHebdo = bil;
                } else {
                    bilanHebdo = lastBilan;
                }
            } else {
                ps = new produitService();
                Bilan bil = new Bilan();
                bil.setProduits(ps.findAll());
                bs.save(bil);
                bil = bs.lastBilan();
                bilanHebdo = bil;
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
        if (quantiteComm <= 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("PROBLEME SUR LA QUANTITE");
            alert.setContentText("VOTRE NOMBRE ARTICLE N'EST PAS CORRECTE \nVEUILLEZ MODIFIER LA QUANTITE");
            alert.showAndWait();

        } else {

            Produit pro = ps.findById(idComm);
            comm.setProduit(pro);

            if (nbreCommande > 0) {
                fs = new facturationService();
                Facturation fac = fs.findById(facture.getId());
                Commande commVerif = fac.getCommandes().stream().filter(commande1 -> commande1.getProduit().getId() == pro.getId()).findFirst().orElse(null);

                if (commVerif != null) {
                    commVerif.setNombre(commVerif.getNombre() + quantiteComm);
                    commVerif.calculerPrixTotal();
                    cs.update(commVerif);
                    totalPrix += commVerif.getPrixTotal();
                } else {
                    comm.setNombre(quantiteComm);
                    comm.calculerPrixTotal();
                    comm.setFacturation(facture);
                    cs.save(comm);
                    totalPrix += comm.getPrixTotal();
                }

            } else {
                comm.setNombre(quantiteComm);
                comm.calculerPrixTotal();
                comm.setFacturation(facture);
                cs.save(comm);
                totalPrix += comm.getPrixTotal();
            }
            getFactureAllCommande();
        }
        nbreCommande++;
        resetFactureProduit();

    }

    @FXML
    private void getSearchFactureProduit() {
        ps = new produitService();
        List<Produit> prodList = ps.findAll().stream().filter(produit -> produit.getNom().toLowerCase().contains(researchFacture.getText().toLowerCase()) ||
                produit.getDescription().toLowerCase().contains(researchFacture.getText().toLowerCase()) ||
                String.valueOf(produit.getQuantiteStock()).contains(researchFacture.getText().toLowerCase()) ||
                String.valueOf(produit.getPrixUnitaire()).contains(researchFacture.getText().toLowerCase()) ||
                produit.getType().toString().contains(researchFacture.getText().toLowerCase())).toList();
        Task<ObservableList<Produit>> task = new Task<>() {

            @Override
            protected ObservableList<Produit> call() throws Exception {
                return FXCollections.observableArrayList(prodList);
            }
        };
        task.setOnSucceeded(e -> articlesTable.setItems(task.getValue()));
        new Thread(task).start();
    }

    void getAllProduit() {
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
                produitComboBox.getItems().clear();
                for (int i = 0; i <= newSelection.getQuantiteStock(); i++) {
                    produitComboBox.getItems().add(i);
                }
                produitComboBox.setOnAction(
                        actionEvent -> {
                            quantiteComm = produitComboBox.getValue();
                            descriptionFacturation(commande, prixUnitcomm, quantiteComm);
                            btnAddCommande.setDisable(false);
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

    void getFactureAllCommande() {
        fs = new facturationService();
        Facturation fac = fs.findById(facture.getId());

        produitCol1.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else {
                    Produit orderCommande = getTableView().getItems().get(getIndex()).getProduit();
                    Label nomProduit = new Label(orderCommande.getNom());
                    HBox boxText = new HBox(1);
                    boxText.getChildren().add(nomProduit);
                    setGraphic(boxText);
                }
            }
        });

        prixUnitaireCol1.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double aDouble, boolean empty) {
                super.updateItem(aDouble, empty);
                if (empty) setGraphic(null);
                else {
                    Produit orderCommande = getTableView().getItems().get(getIndex()).getProduit();
                    Label prixUnitaireProduit = new Label(String.valueOf(orderCommande.getPrixUnitaire()));
                    HBox boxText = new HBox(1);
                    boxText.getChildren().add(prixUnitaireProduit);
                    setGraphic(boxText);
                }
            }
        });
        quantiteCol1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        totalCol1.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));
        actionCol1.setCellFactory(col -> new TableCell<>() {
            Button btnS = new Button("Sup");

            {
                btnS.setOnAction(e -> {
                    Commande commS = getTableView().getItems().get(getIndex());
                    cs = new commandeService();
                    cs.delete(commS.getId());
                    totalPrix -= commS.getPrixTotal();
                    nbreCommande--;
                    getFactureAllCommande();
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else {
                    HBox boxBtn = new HBox(1);
                    boxBtn.getChildren().addAll(btnS);
                    setGraphic(boxBtn);
                }
            }
        });
        Task<ObservableList<Commande>> task = new Task<>() {
            @Override
            protected ObservableList<Commande> call() throws Exception {
                return FXCollections.observableArrayList(fac.getCommandes());
            }
        };
        task.setOnSucceeded(e -> articlesCommande.setItems(task.getValue()));
        new Thread(task).start();
        totalFactureLabel.setText(String.valueOf(totalPrix));
    }

    void resetFactureProduit() {
        researchFacture.setText("");
        btnAddCommande.setDisable(true);
        commande = "";
        prixUnitcomm = 0.0;
        quantiteComm = 0;
        produitComboBox.setValue(quantiteComm);
        descriptionFacturation("", 0.0, 0);
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

    void getProduit(Produit produit) {
        idProduit = produit.getId();
        articleField.setText(produit.getNom());
        description.setText(produit.getDescription());
        prixField.setText(String.valueOf(produit.getPrixUnitaire()));
        nbreArticle.setValue(produit.getQuantiteStock());
        category.setValue(produit.getType());
    }

    void resetProduit() {
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
    void updateProduit() {
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
    void addProduit() {
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

    void boiteAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(message.toUpperCase().substring(0, 10));
        alert.setContentText(message);
        alert.showAndWait();
    }

    void messageErreur(String message) {

        runLater(() -> {
            msgAlert.setText(message);
        });
        throw new RuntimeException(message);
    }

    void messageSpentErreur(String message) {

        runLater(() -> {
            msgAlertSpent.setText(message);
        });
        throw new RuntimeException(message);
    }

    void askSupression(Long produitId) {
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

    void descriptionFacturation(String nomProduit, double prixUnitaire, int quantite) {
        double total = prixUnitaire * quantite;
        labelDescription.setText(String.format(
                        "Produit : %s\nPrix    : %.2f €\nQuantité: %d pièces\nTotal   : %.2f €",
                        nomProduit, prixUnitaire, quantite, total
                )
        );
    }

    @FXML
    void afficherCalculatrice() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("calculer-view.fxml"));
        Parent utilis = loader.load();
        CalculerController cc = loader.getController();
        cc.afficher(totalPrix);
        Stage utilisStage = new Stage();
        utilisStage.setResizable(false);
        utilisStage.initModality(Modality.APPLICATION_MODAL);
        utilisStage.setTitle("Calculatrice !!!");
        utilisStage.setScene(new Scene(utilis));
        utilisStage.showAndWait();
    }

    void affichage() {
        bs = new bilanService();
        Bilan bilan = bs.findById(bilanHebdo.getId());
        List<Facturation> listFacture = bilan.getFacturations()
                .stream()
                .filter(facturation ->
                        facturation.getEtat().equals(Etat.Payée))
                .limit(5)
                .collect(Collectors.toList());
        Collections.reverse(listFacture);
        listFacture.forEach(fact -> ActivityItem(fact));
        int nombreFacture = Math.toIntExact(bilan.getFacturations()
                .stream().
                filter(facture -> facture.getEtat()
                        .equals(Etat.Payée))
                .count());
        revenueToday = 0.0;
        bilan.getFacturations().forEach(facturation1 -> {
            if (facturation1.getEtat().equals(Etat.Payée)) {
                revenueToday += facturation1.getTtc();
            }
        });
        countFacture.setText("Nombre de FACTURE: " + nombreFacture);
        sumFacture.setText("Total FACTURE :" + revenueToday + " CDF");
    }

    public void ActivityItem(Facturation factureBilan) {
        // Configuration du HBox principal

        HBox articleBox = new HBox();
        articleBox.getStyleClass().add("activity-item");
        articleBox.setAlignment(Pos.CENTER_LEFT);
        articleBox.setSpacing(10);
        articleBox.setPadding(new Insets(10));

        // Création de l'icône
        FontIcon cartIcon = new FontIcon();
        cartIcon.setIconLiteral("fa-shopping-cart");
        cartIcon.setIconSize(20);
        cartIcon.getStyleClass().add("activity-icon");

        // Création des labels
        Label titleLabel = new Label("Nouvelle commande " + factureBilan.getCodeReference());
        titleLabel.getStyleClass().add("activity-title");

        Label containLabel = new Label("Prix total: " + factureBilan.getTtc() + " CDF");
        titleLabel.getStyleClass().add("activity-title");

        Label timeLabel = new Label(factureBilan.getDateFacturation().toString());
        timeLabel.getStyleClass().add("activity-time");

        // Création du VBox pour les détails
        VBox detailsBox = new VBox(titleLabel, containLabel, timeLabel);
        detailsBox.getStyleClass().add("activity-details");
        detailsBox.setSpacing(5);

        // Création du bouton
        Button viewButton = new Button("Voir");
        viewButton.getStyleClass().add("small-button");
        viewButton.setOnAction(ae -> System.out.println(" -- "
                + factureBilan.getCodeReference()));

        articleBox.getChildren().addAll(cartIcon, detailsBox, viewButton);
        boxBilanItem.getChildren().clear();
        runLater(() -> boxBilanItem.getChildren().add(articleBox));
    }

    @FXML
    void verifierCode() throws IOException {
        String code = fieldVerifier.getText();
        fs = new facturationService();
        Optional<Facturation> fact = fs.findAll().stream().filter(factu -> code.toLowerCase().contains(factu.getCodeReference().toString().toLowerCase())).findFirst();
        if (fact.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("RECHERCHE FACTURE");
            alert.setContentText("votre facture n'existe pas");
            alert.showAndWait();
            throw new RuntimeException("recherche de la facture non trouvee!!!");
        }

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("facture-view.fxml"));
        Parent utilis = loader.load();
        factureController fcc = loader.getController();
        fcc.getDescription(fact.get());
        Stage utilisStage = new Stage();
        utilisStage.setResizable(false);
        utilisStage.initModality(Modality.APPLICATION_MODAL);
        utilisStage.setTitle("Apercus !!!");
        utilisStage.setScene(new Scene(utilis));
        utilisStage.showAndWait();


    }

    @FXML
    void scannerCode() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("scanner-view.fxml"));
        Parent utilis = loader.load();
        Stage utilisStage = new Stage();
        ScannerController scc = loader.getController();
        scc.setStage(utilisStage);
        utilisStage.setResizable(false);
        utilisStage.initModality(Modality.APPLICATION_MODAL);
        utilisStage.setTitle("Scanner QR !!!");
        utilisStage.setScene(new Scene(utilis));
        utilisStage.showAndWait();
    }

    void getAllspent() {
        List<Depense> depenseList = ds.findAll();
        colSpentDate.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        colSpentTitle.setCellValueFactory(new PropertyValueFactory<>("intitule"));
        colSpentPrice.setCellValueFactory(new PropertyValueFactory<>("Montant"));
        colSpentMotif.setCellValueFactory(new PropertyValueFactory<>("motif"));
        colSpentFor.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        colSpentAuthor.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else {
                    HBox boxBtn = new HBox(2);
                    Utilisateur uti = getTableView().getItems().get(getIndex()).getUtilisateur();
                    Label auteur = new Label(uti.getUsername());
                    boxBtn.getChildren().addAll(auteur);
                    setGraphic(boxBtn);

                }
            }
        });
        colSpentAction.setCellFactory(col -> new TableCell<>() {
            Button btnCancel = new Button("Annuler");
            Button btnValider = new Button("Valider");
            Label status = new Label();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);

                else {
                    HBox boxBtn = new HBox(2);
                    Depense dep = getTableView().getItems().get(getIndex());
                    {
                        btnCancel.setOnAction(actionEvent -> {
                            dep.setAnnulee(true);
                            ds.update(dep);
                            getAllspent();
                        });
                        btnValider.setOnAction(actionEvent -> {
                            dep.setValide(true);
                            ds.update(dep);
                            getAllspent();
                        });
                    }
                    if (!dep.estAnnulee() && !dep.estValide()) {

                        boxBtn.getChildren().addAll(btnValider, btnCancel);
                    }
                    if (dep.estAnnulee()) {
                        status.setText("Annulee");
                        boxBtn.getChildren().add(status);
                    }
                    if (dep.estValide()) {
                        status.setText("Validee");
                        boxBtn.getChildren().add(status);
                    }
                    setGraphic(boxBtn);

                }
            }
        });
        spentTable.getSelectionModel();

        spentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                apercusSpentText.setText(String.format(
                        "Date : %s\nTitre    : %s\nMontant: %.2f CDF pièces\nMotif   : %s\nPour   : %s\nAuteur   : %s",
                        newSelection.getDateCreation(),
                        newSelection.getIntitule(),
                        newSelection.getMontant(),
                        newSelection.getMotif(),
                        newSelection.getAuteur(),
                        newSelection.getUtilisateur().getUsername()
                ));
                if (!newSelection.estAnnulee() && !newSelection.estValide()) apercusSpent(newSelection);

            }
        });

        Task<ObservableList<Depense>> task = new Task<>() {

            @Override
            protected ObservableList<Depense> call() throws Exception {
                return FXCollections.observableArrayList(depenseList);
            }
        };
        task.setOnSucceeded(e -> spentTable.setItems(task.getValue()));
        new Thread(task).start();
        resetDepense();
    }

    @FXML
    void getSearchSpents() {
        List<Depense> depenseList = ds.findAll().stream().filter(depense -> depense.getAuteur().toLowerCase().contains(searchSpent.getText().toLowerCase()) ||
                depense.getMotif().toLowerCase().contains(searchSpent.getText().toLowerCase()) ||
                String.valueOf(depense.getMontant()).contains(searchSpent.getText().toLowerCase()) ||
                depense.getIntitule().toLowerCase().contains(searchSpent.getText().toLowerCase()) ||
                depense.getDateCreation().toString().contains(searchSpent.getText().toLowerCase())).toList();
        Task<ObservableList<Depense>> task = new Task<>() {

            @Override
            protected ObservableList<Depense> call() throws Exception {
                return FXCollections.observableArrayList(depenseList);
            }
        };
        task.setOnSucceeded(e -> spentTable.setItems(task.getValue()));
        new Thread(task).start();

    }

    @FXML
    void addSpent() {
        if (titleSpent.getText().isEmpty()) messageSpentErreur("le titre est vide!!!");
        if (priceSpent.getText().isEmpty()) messageSpentErreur("le prix est vide!!!");
        if (forSpent.getText().isEmpty()) messageSpentErreur("l'auteur est vide!!!");

        Depense depense = new Depense();
        depense.setIntitule(titleSpent.getText());
        depense.setMontant(Double.parseDouble(priceSpent.getText()));
        depense.setAuteur(forSpent.getText());
        if (!motifSpent.getText().isEmpty()) depense.setMotif(motifSpent.getText());
        depense.setBilan(bilanHebdo);
        depense.setUtilisateur(us.findById(idUser));
        ds.save(depense);
        getAllspent();
        initialize();
    }

    void apercusSpent(Depense depense) {
        titleSpent.setText(depense.getIntitule());
        priceSpent.setText(depense.getMontant().toString());
        forSpent.setText(depense.getAuteur());
        motifSpent.setText(depense.getMotif());
        btnModifierSpent.setVisible(true);
        btnCreateSpent.setVisible(false);
        btnModifierSpent.setOnAction(ae -> update(depense));
    }

    @FXML
    void update(Depense depense) {
        if (titleSpent.getText().isEmpty()) messageSpentErreur("le titre est vide!!!");
        if (priceSpent.getText().isEmpty()) messageSpentErreur("le prix est vide!!!");
        if (forSpent.getText().isEmpty()) messageSpentErreur("l'auteur est vide!!!");
        depense.setIntitule(titleSpent.getText());
        depense.setMontant(Double.parseDouble(priceSpent.getText()));
        depense.setAuteur(forSpent.getText());
        depense.setMotif(motifSpent.getText());
        depense.setUtilisateur(us.findById(idUser));
        ds.update(depense);
        getAllspent();
    }

    @FXML
    void resetDepense() {
        titleSpent.setText("");
        priceSpent.setText("");
        forSpent.setText("");
        motifSpent.setText("");
        btnModifierSpent.setVisible(false);
        btnCreateSpent.setVisible(true);
        apercusSpentText.setText("Sélectionnez une dépense...");
    }

    @FXML
    void getAllCloture() {
        List<Bilan> bilanList = bs.findAll();
        finalTotal = 0;
        finalDepense = 0;
        finalFacture = 0;
        colBilanDate.setCellValueFactory(new PropertyValueFactory<>("debutBilan"));
        colBilanRef.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBilanFacture.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) setGraphic(null);
                else {
                    AtomicReference<Double> totalFacture = new AtomicReference<>(0.0);
                    Long itemId = getTableView().getItems().get(getIndex()).getId();
                    Bilan bilanItem = bs.findById(itemId);
                    bilanItem.getFacturations().forEach(facturation1 -> {
                        if (facturation1.getEtat().equals(Etat.Payée)) {
                            totalFacture.updateAndGet(v -> v + facturation1.getTtc());
                        }
                    });
                    HBox boxBtn = new HBox(2);
                    Label total = new Label(String.valueOf(totalFacture));
                    boxBtn.getChildren().add(total);
                    setGraphic(boxBtn);
                }
            }
        });

        colBilanDepense.setCellFactory(col -> new TableCell<>() {

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else {
                    AtomicReference<Double> totalDepense = new AtomicReference<>(0.0);
                    Long itemId = getTableView().getItems().get(getIndex()).getId();
                    Bilan bilanItem = bs.findById(itemId);
                    bilanItem.getDepenses().forEach(depense1 -> {
                        if (depense1.estValide()) {
                            totalDepense.updateAndGet(v -> v + depense1.getMontant());
                        }
                    });
                    HBox boxBtn = new HBox(2);
                    Label total = new Label(String.valueOf(totalDepense.get()));
                    boxBtn.getChildren().add(total);
                    setGraphic(boxBtn);
                }
            }
        });

        colBilanTotal.setCellFactory(col -> new TableCell<>() {

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else {
                    AtomicReference<Double> totalDepense = new AtomicReference<>((double) 0);
                    AtomicReference<Double> totalFacture = new AtomicReference<>((double) 0);
                    Long itemId = getTableView().getItems().get(getIndex()).getId();
                    Bilan bilanItem = bs.findById(itemId);
                    bilanItem.getDepenses().forEach(depense1 -> {
                        if (depense1.estValide()) {
                            totalDepense.updateAndGet(v -> v + depense1.getMontant());
                        }
                    });
                    bilanItem.getFacturations().forEach(facturation1 -> {
                        if (facturation1.getEtat().equals(Etat.Payée)) {
                            totalFacture.updateAndGet(v -> v + facturation1.getTtc());
                        }

                    });
                    finalDepense += totalDepense.get();
                    finalFacture += totalFacture.get();
                    finalTotal += (totalFacture.get() - totalDepense.get());
                    System.out.println("******* facture:" + finalFacture + "\n******* depense:" + finalDepense + "\n*******" + finalTotal);

                    HBox boxBtn = new HBox(2);
                    Label total = new Label(String.valueOf(totalFacture.get() - totalDepense.get()));
                    boxBtn.getChildren().add(total);
                    setGraphic(boxBtn);
                }
            }
        });

        colBilanAction.setCellFactory(col -> new TableCell<>() {
            Button btnCommande = new Button("Apercus | commande");

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else {
                    Bilan blan=getTableView().getItems().get(getIndex());
                    HBox boxBtn = new HBox(2);
                    btnCommande.setOnAction(actionEvent -> getApercusBilan(blan));
                    boxBtn.getChildren().add(btnCommande);
                    setGraphic(boxBtn);
                }
            }
        });


        Task<ObservableList<Bilan>> task = new Task<>() {

            @Override
            protected ObservableList<Bilan> call() throws Exception {
                return FXCollections.observableArrayList(bilanList);
            }
        };
        task.setOnSucceeded(e -> {
            bilanTable.setItems(task.getValue());
            runLater(() -> bilansTotal.setText(String.format(
                    "Bilan total:\nFactures: %.2f CDF\nDepenses: %.2f CDF\nTotal: %.2f CDF",
                    finalFacture, finalDepense, finalTotal)));
        });

        new Thread(task).start();

    }

    @FXML
    void getSearchCloture() {
        String SearchBila = researchBilan.getText();
        List<Bilan> bilanList = bs.findAll().stream().filter(bilan -> bilan.getDebutBilan().toString().contains(SearchBila)
        ).toList();
        finalTotal = 0;
        finalDepense = 0;
        finalFacture = 0;
        colBilanDate.setCellValueFactory(new PropertyValueFactory<>("debutBilan"));
        colBilanRef.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBilanFacture.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) setGraphic(null);
                else {
                    AtomicReference<Double> totalFacture = new AtomicReference<>(0.0);
                    Long itemId = getTableView().getItems().get(getIndex()).getId();
                    Bilan bilanItem = bs.findById(itemId);
                    bilanItem.getFacturations().forEach(facturation1 -> {
                        if (facturation1.getEtat().equals(Etat.Payée)) {
                            totalFacture.updateAndGet(v -> v + facturation1.getTtc());
                        }
                    });
                    HBox boxBtn = new HBox(2);
                    Label total = new Label(String.valueOf(totalFacture));
                    boxBtn.getChildren().add(total);
                    setGraphic(boxBtn);
                }
            }
        });

        colBilanDepense.setCellFactory(col -> new TableCell<>() {

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else {
                    AtomicReference<Double> totalDepense = new AtomicReference<>(0.0);
                    Long itemId = getTableView().getItems().get(getIndex()).getId();
                    Bilan bilanItem = bs.findById(itemId);
                    bilanItem.getDepenses().forEach(depense1 -> {
                        if (depense1.estValide()) {
                            totalDepense.updateAndGet(v -> v + depense1.getMontant());
                        }
                    });
                    HBox boxBtn = new HBox(2);
                    Label total = new Label(String.valueOf(totalDepense.get()));
                    boxBtn.getChildren().add(total);
                    setGraphic(boxBtn);
                }
            }
        });

        colBilanTotal.setCellFactory(col -> new TableCell<>() {

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else {
                    AtomicReference<Double> totalDepense = new AtomicReference<>((double) 0);
                    AtomicReference<Double> totalFacture = new AtomicReference<>((double) 0);
                    Long itemId = getTableView().getItems().get(getIndex()).getId();
                    Bilan bilanItem = bs.findById(itemId);
                    bilanItem.getDepenses().forEach(depense1 -> {
                        if (depense1.estValide()) {
                            totalDepense.updateAndGet(v -> v + depense1.getMontant());
                        }
                    });
                    bilanItem.getFacturations().forEach(facturation1 -> {
                        if (facturation1.getEtat().equals(Etat.Payée)) {
                            totalFacture.updateAndGet(v -> v + facturation1.getTtc());
                        }

                    });
                    finalDepense += totalDepense.get();
                    finalFacture += totalFacture.get();
                    finalTotal += (totalFacture.get() - totalDepense.get());
                    System.out.println("******* facture:" + finalFacture + "\n******* depense:" + finalDepense + "\n*******" + finalTotal);

                    HBox boxBtn = new HBox(2);
                    Label total = new Label(String.valueOf(totalFacture.get() - totalDepense.get()));
                    boxBtn.getChildren().add(total);
                    setGraphic(boxBtn);
                }
            }
        });


        Task<ObservableList<Bilan>> task = new Task<>() {

            @Override
            protected ObservableList<Bilan> call() throws Exception {
                return FXCollections.observableArrayList(bilanList);
            }
        };
        task.setOnSucceeded(e -> {
            bilanTable.setItems(task.getValue());
            runLater(() -> bilansTotal.setText(String.format(
                    "Bilan total:\nFactures: %.2f CDF\nDepenses: %.2f CDF\nTotal: %.2f CDF",
                    finalFacture, finalDepense, finalTotal)));
        });

        new Thread(task).start();

    }

    void getBilanHebdo() {
        bilanHebdo = bs.findById(bilanHebdo.getId());
        factureTotal = 0.0;
        depenseTotal = 0.0;
        bilanHebdo.getFacturations().forEach(facturation1 -> {
            if (facturation1.getEtat().equals(Etat.Payée)) {
                factureTotal += facturation1.getTtc();
            }
        });
        bilanHebdo.getDepenses().forEach(depense1 -> {
            if (depense1.estValide()) depenseTotal += depense1.getMontant();
        });
        bilanSommeDepense.setText("DEPENSE: " + depenseTotal + " CDF");
        bilanSommeFacture.setText("FACTURE: " + factureTotal + " CDF");
        bilanDepenseElement.setText("DEPENSE: " + bilanHebdo.getDepenses().size());
        bilanFactureElement.setText("FACTURE: " + bilanHebdo.getFacturations().stream().filter(facturation1 -> facturation1.getEtat().equals(Etat.Payée)).toList().size());
        bilanDate.setText("DATE: " + bilanHebdo.getDebutBilan());
        bilanRef.setText("REF: " + bilanHebdo.getId());
    }

    void getApercusBilan(Bilan bila)  {

        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("apercusCommande-view.fxml"));
            Parent utilis = loader.load();
            ApercusCommandeController acc = loader.getController();
            bila=bs.findById(bila.getId());
            acc.take(bila);
            Stage utilisStage = new Stage();
            utilisStage.setResizable(false);
            utilisStage.initModality(Modality.APPLICATION_MODAL);
            utilisStage.setTitle("Apercus Commandes!!!");
            utilisStage.setScene(new Scene(utilis));
            utilisStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

