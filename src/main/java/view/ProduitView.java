/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Produit;
import controller.ProduitController;

/**
 *
 * @author juper
 */
public class ProduitView {
    public static void ouvrirFenetreProduit(TextArea outputArea) {
        Stage stage = new Stage();
        stage.setTitle("Ajouter un Produit");

        TextField codeField = new TextField();
        TextField designationField = new TextField();

        VBox machinesBox = new VBox(5);
        Label machinesLabel = new Label("Machines utilisées :");
        TextField firstMachineField = new TextField();
        machinesBox.getChildren().add(firstMachineField);

        Button addMachineBtn = new Button("+ Ajouter une machine");
        addMachineBtn.setOnAction(e -> {
            TextField newMachineField = new TextField();
            machinesBox.getChildren().add(newMachineField);
        });

        VBox root = new VBox(10,
            new Label("Code produit :"), codeField,
            new Label("Désignation produit :"), designationField,
            machinesLabel, machinesBox, addMachineBtn
        );

        Button btnAjouter = new Button("Ajouter");
        btnAjouter.setOnAction(e -> {
            String code = codeField.getText();
            String designation = designationField.getText();

            Produit produit = new Produit(code, designation);

            for (javafx.scene.Node node : machinesBox.getChildren()) {
                if (node instanceof TextField) {
                    TextField tf = (TextField) node;
                    if (!tf.getText().isBlank()) {
                        produit.ajouterMachineTexte(tf.getText());
                    }
                }
            }

            ProduitController.enregistrerProduit(produit);
            stage.close();
        });

        root.getChildren().add(btnAjouter);
        root.setPadding(new javafx.geometry.Insets(10));

        stage.setScene(new Scene(root, 400, 400));
        stage.show();
    }

}
