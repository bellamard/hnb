package com.b2la.hnb;

import com.b2la.hnb.models.Utilisateur;
import com.b2la.hnb.services.utilisateurService;
import com.b2la.hnb.util.BcryptUtil;
import com.b2la.hnb.util.JPAUtil;
import com.b2la.hnb.util.Fonction;
import jakarta.persistence.EntityManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(fxmlLoader.load(), screenBounds.getWidth()/2, screenBounds.getHeight()/2);
        stage.setTitle("HNB APPLICATION");
        stage.centerOnScreen();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        EntityManager em = JPAUtil.getEntityManager();
        em.close();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        JPAUtil.shutdown();
        System.out.println("Application stopped and JPA resources released.");
    }


    public static void main(String[] args) {
        launch();
    }
}