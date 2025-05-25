/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import static controller.EquipementController.enregistrerEquipement;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Equipement;
import controller.EquipementController;


public class EquipementView {
    //ouvre une fenetre pour ajouter un equipement, pas utilisée dans le projet 
    public static void ouvrirFenetreEquipement(TextArea outputArea) {
        Stage stage = new Stage();
        stage.setTitle("Ajouter un Équipement");

        TextField refField = new TextField();
        TextField descField = new TextField();

        VBox root = new VBox(10,
            new Label("Référence:"), refField,
            new Label("Désignation:"), descField
        );

        Button creerBtn = new Button("Créer");
        creerBtn.setOnAction(e -> {
            String ref = refField.getText();
            String desc = descField.getText();

            Equipement eq = new Equipement(ref, desc);
            enregistrerEquipement(eq);
            outputArea.appendText("Équipement ajouté: " + ref + "\n");
            stage.close();
        });

        root.getChildren().add(creerBtn);
        root.setPadding(new javafx.geometry.Insets(10));

        stage.setScene(new Scene(root));
        stage.show();
    }

}
