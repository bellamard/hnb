package com.b2la.hnb.controllers;

import com.b2la.hnb.HelloApplication;
import com.b2la.hnb.models.Commande;
import com.b2la.hnb.models.Facturation;
import com.b2la.hnb.services.facturationService;
import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.github.sarxos.webcam.Webcam.getDefault;
import static javafx.application.Platform.runLater;

public class ScannerController {

    Webcam webcam;
    ScheduledExecutorService executor;
    @FXML
    Label stateWebcam, detailQR;
    @FXML
    Button btnPrintQR;
    @FXML
    ImageView screen;
    @FXML
    facturationService fs;
    String detail;
    private Stage stage;

    public void initialize(){
        fs= new facturationService();
        startWebcam();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(event -> {
            stopCamera();
        });
    }


    void startWebcam(){
        if(executor!=null&& executor.isShutdown())return;
        if(webcam==null){
            webcam= getDefault();
            if(webcam==null){
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERREUR WEBCAM");
                alert.setHeaderText("Aucune webcam détectée.");
                alert.showAndWait();
                throw new RuntimeException("Aucune webcam détectée.");
            }
            webcam.setViewSize(new Dimension(176,144));
            webcam.open();
        }
        stateWebcam.setText("STATUT WEBCAM : EN COURS DE SCAN");
        executor= Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::grabAndDecode, 0, 40, TimeUnit.MILLISECONDS);
    }

    private void grabAndDecode() {
        if (webcam == null || !webcam.isOpen()) return;

        BufferedImage frame = webcam.getImage();
        if (frame == null) return;

        // Affichage dans l'UI
        Image fxImg = SwingFXUtils.toFXImage(frame, null);
        Platform.runLater(() -> screen.setImage(fxImg));

        // Tentative de décodage
        try {
            LuminanceSource source = new BufferedImageLuminanceSource(frame);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Map<DecodeHintType, Object> hints = Map.of(
                    DecodeHintType.TRY_HARDER, Boolean.TRUE,
                    DecodeHintType.POSSIBLE_FORMATS, java.util.List.of(BarcodeFormat.QR_CODE)
            );

            Result result = new MultiFormatReader().decode(bitmap, hints);
            if (result != null) {
                String text = result.getText();

//                Platform.runLater(() -> stateWebcam.setText("QR détecté: " + text));
                verifierDataQR(text);
                // Option: arrêter après première détection
                stopCamera();
            }
        } catch (NotFoundException ignored) {
            // Aucun code trouvé sur cette frame: normal
        } catch (Exception ex) {
            Platform.runLater(() -> stateWebcam.setText("Erreur: " + ex.getMessage()));
        }
    }

    private void stopCamera() {
        if (executor != null) {
            webcam.close();
            webcam=null;
            executor.shutdownNow();
            executor = null;
        }
        stateWebcam.setText("Statut: caméra arrêtée");
    }

    private void verifierDataQR(String codeQR){
        fs= new facturationService();
        Optional<Facturation> fact = fs.findAll().stream().filter(factu -> codeQR.toLowerCase().contains(factu.getCodeReference().toString().toLowerCase())).findFirst();
        if(fact.isEmpty()){
            Platform.runLater(() -> stateWebcam.setText("QR code Incorrect!!!"));
            throw new RuntimeException("recherche de la facture non trouvee!!!");
        }

        Platform.runLater(()->stateWebcam.setText("Statut: "+codeQR));
        detail="Facture Ref: "+fact.get().getCodeReference()+
                "\nTotal: "+fact.get().getTtc()+" CDF \n";
        facturationService fs= new facturationService();
        Facturation factu= fs.findById(fact.get().getId());
        List<Commande> commandeList=factu.getCommandes();
        commandeList.forEach(commande -> detail+=commande.getProduit().getNom()+" | "+commande.getNombre()+" | "+commande.getPrixTotal()+" CDF \n");
        runLater(()->detailQR.setText(detail));
        btnPrintQR.setOnAction(ae->printFacture(factu));
    }

    private void printFacture(Facturation factur){
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("impression-view.fxml"));
            Parent rootPrint = loader.load();
            ImpressionController ic = loader.getController();
            ic.imprimer(factur);
            ic.lancerImpression(rootPrint);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
