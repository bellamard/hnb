package com.b2la.hnb.controllers;

import com.b2la.hnb.models.Utilisateur;
import com.b2la.hnb.services.utilisateurService;
import com.b2la.hnb.util.BcryptUtil;
import com.b2la.hnb.util.Fonction;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class utilisateurController {

    utilisateurService us;
    @FXML
    TableView<Utilisateur> tableView;
    @FXML
    TableColumn<Utilisateur, String> username, phone, email,action;
    @FXML
    TableColumn<Utilisateur, Fonction> fonction;

    @FXML
    TextField fieldUsername, fieldPhone, fieldEmail;

    @FXML
    PasswordField password, confirme;

    @FXML
    ComboBox<Fonction> comboFonction;
    @FXML Button btnModifierUser, btnAjouter;
    Long id;


    public void initialize() {
        getTableauUsers();
    }

    @FXML
    public void addUtilisateur(){
        String password="123456";
        Utilisateur user=new Utilisateur(
                "admin", Fonction.Admin,
                "895127236",
                BcryptUtil.hashPassword(password),
                "belamard@gmail.com"
        );
        us= new utilisateurService();
        us.save(user);
        getTableauUsers();
    }

    public void getTableauUsers(){
        us= new utilisateurService();
        List<Utilisateur> utilisateursList = us.findAll();
        comboFonction.getItems().addAll(Fonction.values());
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        phone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        fonction.setCellValueFactory(new PropertyValueFactory<>("fonction"));
        action.setCellFactory(col->new TableCell<>(){
            Button btnM= new Button("Mod");
            Button btnS= new Button("Sup");
            {
                btnM.setOnAction(e->{
                    Utilisateur utili=getTableView().getItems().get(getIndex());
                    getDataUser(utili);
                    System.out.println(utili.getUsername());
                    btnModifierUser.setVisible(true);
                });

                btnS.setOnAction(e->{
                    Utilisateur utili=getTableView().getItems().get(getIndex());
                    System.out.println(utili.getUsername());
                    askSupressionUser(utili.getId());
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(empty)setGraphic(null);
                else{
                    HBox boxBtn=new HBox(2);
                    boxBtn.getChildren().addAll(btnS,btnM);
                    setGraphic(boxBtn);

                }
            }
        });

        Task<ObservableList<Utilisateur>> task= new Task<>(){

            @Override
            protected ObservableList<Utilisateur> call() throws Exception {
                return FXCollections.observableArrayList(utilisateursList);
            }
        };
        task.setOnSucceeded(e->tableView.setItems(task.getValue()));
        new Thread(task).start();
        reset();


    }

    private void getDataUser(Utilisateur utilisateur){
        id= utilisateur.getId();
        fieldUsername.setText(utilisateur.getUsername());
        fieldEmail.setText(utilisateur.getEmail());
        fieldPhone.setText(utilisateur.getTelephone());
        password.setText(utilisateur.getMotDePasse());
        password.setEditable(false);
        password.setDisable(true);
        confirme.setText("");
        comboFonction.setValue(Fonction.valueOf(String.valueOf(utilisateur.getFonction())));
        btnAjouter.setVisible(false);
        btnModifierUser.setVisible(true);
    }
    @FXML
    public void modifier(){
        if(!BcryptUtil.checkPassword(confirme.getText(),password.getText())){
            throw new RuntimeException("Vos Mots des passe ne sont pas identique");

        }
        if(fieldPhone.getText().length()<9&& fieldPhone.getText().length()>14){
            throw new RuntimeException("Votre numero de telephone est incorrect");

        }
        if(fieldUsername.getText().isEmpty()){
            throw new RuntimeException("Votre nom est incorrect");

        }
        Utilisateur utilis= new Utilisateur();
        utilis.setMotDePasse(BcryptUtil.hashPassword(confirme.getText()));
        utilis.setTelephone(fieldPhone.getText());
        utilis.setUsername(fieldUsername.getText());

        if(!fieldEmail.getText().isEmpty()&&
                !fieldEmail.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            throw new RuntimeException("Votre email est incorrect");

        }
        if(!fieldEmail.getText().isEmpty()){
            utilis.setEmail(fieldEmail.getText());
        }
        utilis.setFonction(comboFonction.getValue());
        utilis.setId(id);
        us= new utilisateurService();
        us.update(utilis);
        getTableauUsers();

    }

    @FXML
    public void addDataUser(){
        if(!confirme.getText().equals(password.getText())){
            throw new RuntimeException("Vos Mots des passe ne sont pas identique");

        }
        if(fieldPhone.getText().length()<9&& fieldPhone.getText().length()>14){
            throw new RuntimeException("Votre numero de telephone est incorrect");

        }
        if(fieldUsername.getText().isEmpty()){
            throw new RuntimeException("Votre nom est incorrect");

        }
        Utilisateur utilis= new Utilisateur();
        utilis.setMotDePasse(BcryptUtil.hashPassword(confirme.getText()));
        utilis.setTelephone(fieldPhone.getText());
        utilis.setUsername(fieldUsername.getText());

        if(!fieldEmail.getText().isEmpty()&&
                !fieldEmail.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            throw new RuntimeException("Votre email est incorrect");

        }
        if(!fieldEmail.getText().isEmpty()){
            utilis.setEmail(fieldEmail.getText());
        }
        utilis.setFonction(comboFonction.getValue());

        us= new utilisateurService();
        us.save(utilis);
        getTableauUsers();
    }

    private void askSupressionUser(Long utilisateurId){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous Supprimer ?");
        alert.setContentText("Cliquez sur Ok pour confirmer la suppression.");
        Optional<ButtonType> result= alert.showAndWait();
        if(result.isPresent()&& result.get()== ButtonType.OK){
            us.delete(utilisateurId);
            System.out.println("suppression valider");
            getTableauUsers();
        }
    }

    private void reset(){
        fieldEmail.setText("");
        fieldUsername.setText("");
        fieldPhone.setText("");
        password.setText("");
        password.setEditable(true);
        password.setDisable(false);
        confirme.setText("");
        id= Long.valueOf(0);
        btnAjouter.setVisible(true);
        btnModifierUser.setVisible(false);

    }
}
