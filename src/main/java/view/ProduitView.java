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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.CheckBox;


public class ProduitView {


    public static void ouvrirFenetreProduit(TextArea outputArea) {
        Stage stage = new Stage();
        stage.setTitle("Ajouter un Produit");

        TextField codeField = new TextField();
        TextField designationField = new TextField();

        // Récupérer la liste des machines existantes (y compris les machines de base)
        List<String> machinesDisponibles = getMachinesDisponibles();

        // Créer une VBox pour contenir les CheckBoxes et champs de saisie de durée
        VBox machinesBox = new VBox(5);
        Label machinesLabel = new Label("Machines utilisées :");

        // Créer une liste de CheckBoxes pour chaque machine disponible
        Map<String, TextField> machinesEtDuree = new HashMap<>(); // Pour lier les machines à leurs durées

        for (String machine : machinesDisponibles) {
            // Créer un CheckBox pour la machine
            CheckBox machineCheckBox = new CheckBox(machine);
            machinesBox.getChildren().add(machineCheckBox);

            // Ajouter un TextField pour la durée d'utilisation par machine
            TextField dureeField = new TextField();
            dureeField.setPromptText("Durée en heures");
            machinesBox.getChildren().add(dureeField);

            // Lier la durée au nom de la machine
            machinesEtDuree.put(machine, dureeField);
        }

        // Bouton pour ajouter le produit
        Button btnAjouter = new Button("Ajouter");
        btnAjouter.setOnAction(e -> {
            String code = codeField.getText();
            String designation = designationField.getText();

            // Créer un objet produit avec les informations saisies
            Produit produit = new Produit(code, designation);

            double coutTotal = 0; // Variable pour accumuler le coût total de production

            // Ajouter les machines sélectionnées et calculer le coût de production
            for (String machine : machinesDisponibles) {
                CheckBox checkBox = (CheckBox) machinesBox.lookup("#" + machine); // On cherche le CheckBox de la machine
                if (checkBox != null && checkBox.isSelected()) {
                    TextField dureeField = machinesEtDuree.get(machine); // Récupérer le TextField pour la durée

                    try {
                        double duree = Double.parseDouble(dureeField.getText()); // Récupérer la durée
                        double coutHoraire = ProduitController.getCoutHoraireForMachine(machine); // Récupérer le coût horaire de la machine
                        double coutMachine = duree * coutHoraire; // Calcul du coût pour cette machine

                        // Ajouter le coût pour cette machine au coût total
                        coutTotal += coutMachine;

                        // Ajouter la machine au produit
                        produit.ajouterMachineTexte(machine);
                    } catch (NumberFormatException ex) {
                        // Si la durée n'est pas un nombre valide, ignorer cette machine
                        outputArea.appendText("Durée invalide pour la machine " + machine + "\n");
                    }
                }
            }

            // Enregistrer le produit avec les machines associées
            ProduitController.enregistrerProduit(produit);

            // Fermer la fenêtre d'ajout de produit
            stage.close();
        });

        // Organiser les éléments dans un VBox
        VBox root = new VBox(10,
                new Label("Code produit :"), codeField,
                new Label("Désignation produit :"), designationField,
                machinesLabel, machinesBox, btnAjouter
        );
        root.setPadding(new javafx.geometry.Insets(10));

        // Créer la scène et l'afficher
        Scene scene = new Scene(root, 400, 600);
        stage.setScene(scene);
        stage.show();
    }

    // Méthode pour récupérer la liste des machines disponibles
    private static List<String> getMachinesDisponibles() {
        List<String> machines = new ArrayList<>();
        
        // Charger les machines depuis le fichier machines_base.txt (ou tout autre fichier source)
        try (BufferedReader reader = new BufferedReader(new FileReader("machines_base.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                machines.add(ligne.trim()); // Ajouter chaque machine à la liste
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Charger les machines supplémentaires depuis machines.txt (si nécessaire)
        try (BufferedReader reader = new BufferedReader(new FileReader("machines.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                machines.add(ligne.trim()); // Ajouter chaque machine à la liste
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return machines;
    }

}