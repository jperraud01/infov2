/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Produit;
import controller.ProduitController;
import java.io.*;
import java.util.*;

public class ProduitView {

    public static void ouvrirFenetreProduit(TextArea outputArea) {
        Stage stage = new Stage();
        stage.setTitle("Ajouter un Produit");

        TextField codeField = new TextField();
        TextField designationField = new TextField();

        // Récupérer les codes des machines
        List<String> machinesDisponibles = getMachinesDisponibles();

        VBox machinesBox = new VBox(5);
        machinesBox.getChildren().add(new Label("Machines utilisées :"));

        Map<String, CheckBox> machinesCheckBoxes = new HashMap<>();
        Map<String, TextField> machinesEtDuree = new HashMap<>();

        // Création des Checkboxes pour chaque machine et de leurs durées
        for (String codeMachine : machinesDisponibles) {
            CheckBox cb = new CheckBox(codeMachine);
            machinesBox.getChildren().add(cb);
            machinesCheckBoxes.put(codeMachine, cb);

            TextField dureeField = new TextField();
            dureeField.setPromptText("Durée (h)");
            machinesBox.getChildren().add(dureeField);
            machinesEtDuree.put(codeMachine, dureeField);
        }

        // Bouton pour ajouter le produit
        Button btnAjouter = new Button("Ajouter");
        btnAjouter.setOnAction(e -> {
            Produit produit = new Produit(codeField.getText(), designationField.getText());
            double coutTotal = 0;

            // Traitement des machines et des durées
            for (String codeMachine : machinesDisponibles) {
                CheckBox cb = machinesCheckBoxes.get(codeMachine);
                if (cb.isSelected()) {
                    String dureeTxt = machinesEtDuree.get(codeMachine).getText();
                    try {
                        double duree = Double.parseDouble(dureeTxt);
                        double coutH = ProduitController.getCoutHoraireForMachine(codeMachine);
                        produit.ajouterMachine(codeMachine, duree, coutH);
                        coutTotal += duree * coutH;
                    } catch (NumberFormatException ex) {
                        outputArea.appendText("Durée invalide pour " + codeMachine + "\n");
                    }
                }
            }

            // Enregistrer le produit
            ProduitController.enregistrerProduit(produit);

            outputArea.appendText("Produit ajouté: " +
                                  produit.getCodeProduit() +
                                  " - " + produit.getDproduit() +
                                  " - Coût: " + String.format("%.2f", coutTotal) + " €\n");

            stage.close();
        });

        // Organiser les éléments de la scène
        VBox root = new VBox(10,
            new Label("Code produit :"), codeField,
            new Label("Désignation :"), designationField,
            machinesBox,
            btnAjouter
        );
        root.setPadding(new javafx.geometry.Insets(10));

        // Créer la scène et l'afficher
        stage.setScene(new Scene(root, 400, 600));
        stage.show();
    }

    private static List<String> getMachinesDisponibles() {
        List<String> codes = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader("machines_base.txt"))) {
            String ligne;
            while ((ligne = r.readLine()) != null) {
                String[] p = ligne.split(" ");
                if (p.length > 0) codes.add(p[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader r = new BufferedReader(new FileReader("machines.txt"))) {
            String ligne;
            while ((ligne = r.readLine()) != null) {
                String[] p = ligne.split(" ");
                if (p.length > 0) codes.add(p[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return codes;
    }
}
