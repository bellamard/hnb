package com.b2la.hnb.controllers;

import com.b2la.hnb.models.Utilisateur;
import com.b2la.hnb.services.utilisateurService;
import com.b2la.hnb.util.BcryptUtil;
import com.b2la.hnb.util.Fonction;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class utilisateurController {

    utilisateurService us;
    @FXML
    TableColumn<Utilisateur, String> username, phone, email,action;
    @FXML
    TableColumn<Utilisateur, Fonction> fonction;

    @FXML
    TextField fieldUsername, fieldPhone, fieldEmail, password, confirme;
    @FXML
    ComboBox<Fonction> comboFonction;
    @FXML Button btnModifierUser;
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
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
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


        });

    }

    private void getDataUser(Utilisateur utilisateur){
        id= utilisateur.getId();
        fieldUsername.setText(utilisateur.getUsername());
        fieldEmail.setText(utilisateur.getEmail());
        fieldPhone.setText(utilisateur.getTelephone());
        password.setText(utilisateur.getMotDePasse());
        confirme.setText("");
        comboFonction.setValue(Fonction.valueOf(String.valueOf(utilisateur.getFonction())));
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


}
