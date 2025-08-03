package com.b2la.hnb;

import com.b2la.hnb.util.Stockage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button connexion, fermer;


    @FXML
    protected void onClose() {
        Stockage.reset();
        System.exit(0);
    }


}