/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import controller.GammeController;


/**
 *
 * @author juper
 */
public class GammeView {
    public static void ouvrirFenetreGamme(TextArea outputArea) {
        Stage gammeStage = new Stage();
        gammeStage.setTitle("Ajouter une gamme");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField refField = new TextField();
        TextField codeProduitField = new TextField();
        TextField dureeField = new TextField();
        TextField coutField = new TextField();

        grid.add(new Label("Référence :"), 0, 0);
        grid.add(refField, 1, 0);
        grid.add(new Label("Code Produit :"), 0, 1);
        grid.add(codeProduitField, 1, 1);
        grid.add(new Label("Durée :"), 0, 2);
        grid.add(dureeField, 1, 2);
        grid.add(new Label("Coût :"), 0, 3);
        grid.add(coutField, 1, 3);

        Button creerBtn = new Button("Créer");
        creerBtn.setOnAction(e -> {
            String ref = refField.getText();
            String codeProduit = codeProduitField.getText();
            String duree = dureeField.getText();
            String cout = coutField.getText();

            GammeController.enregistrerGamme(ref, codeProduit, duree, cout);
            
            gammeStage.close();
        });

        grid.add(creerBtn, 1, 4);

        Scene scene = new Scene(grid, 350, 250);
        gammeStage.setScene(scene);
        gammeStage.show();
    }
}
