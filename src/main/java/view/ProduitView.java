/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/*package view;

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
}*/
package view;

import controller.ProduitController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Produit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ProduitView {

    public static void ouvrirFenetreProduit(TextArea outputArea) {
        Stage stage = new Stage();
        stage.setTitle("Ajouter un Produit");

        TextField codeField = new TextField();
        TextField designationField = new TextField();

        // 1. Charger toutes les gammes (de base + ajoutées) avec leur coût
        Map<String, Double> gammeCostMap = chargerCoutGammes();

        // 2. Créer la liste de CheckBoxes pour chaque gamme
        VBox gammesBox = new VBox(5);
        gammesBox.getChildren().add(new Label("Gammes utilisées :"));

        Map<String, CheckBox> checkboxes = new HashMap<>();
        for (String refGamme : gammeCostMap.keySet()) {
            CheckBox cb = new CheckBox(refGamme);
            gammesBox.getChildren().add(cb);
            checkboxes.put(refGamme, cb);
        }

        // Bouton Ajouter
        Button btnAjouter = new Button("Ajouter");
        btnAjouter.setOnAction(e -> {
            String code = codeField.getText();
            String designation = designationField.getText();
            Produit produit = new Produit(code, designation);

            double coutTotal = 0;
            // 3. Parcourir les gammes cochées et sommer les coûts
            for (Map.Entry<String, CheckBox> entry : checkboxes.entrySet()) {
                if (entry.getValue().isSelected()) {
                    String ref = entry.getKey();
                    double coutG = gammeCostMap.getOrDefault(ref, 0.0);
                    coutTotal += coutG;
                    produit.ajouterGamme(ref, coutG);
                }
            }

            // 4. Enregistrer le produit (avec liste des gammes et leur coût)
            ProduitController.enregistrerProduit(produit);

            stage.close();
        });

        VBox root = new VBox(10,
            new Label("Code produit :"), codeField,
            new Label("Désignation :"), designationField,
            gammesBox,
            btnAjouter
        );
        root.setPadding(new Insets(10));

        stage.setScene(new Scene(root, 400, 600));
        stage.show();
    }

    /**
     * Lit les fichiers 'gammes_base.txt' et 'gammes.txt', calcule
     * le coût de chaque gamme (durée×coût horaire) et renvoie
     * une map refGamme→coûtTotal.
     */
    private static Map<String, Double> chargerCoutGammes() {
        Map<String, Double> map = new LinkedHashMap<>();
        // Lecture des gammes de base
        lireGammesDepuis("gammes_base.txt", map);
        // Lecture des gammes ajoutées
        lireGammesDepuis("gammes.txt",     map);
        return map;
    }

    private static void lireGammesDepuis(String fichier, Map<String, Double> map) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(" ");
                if (parts.length < 2) continue;
                String ref    = parts[0];
                // parts[1] est le codeProduit, qu'on ignore ici
                // Chaque machine occupe 3 champs : codeMachine, duree, coûtHoraire
                double total = 0;
                for (int i = 2; i + 2 < parts.length; i += 3) {
                    double duree      = Double.parseDouble(parts[i + 1]);
                    double coutHoraire= Double.parseDouble(parts[i + 2]);
                    total += duree * coutHoraire;
                }
                map.put(ref, total);
            }
        } catch (IOException e) {
            // Silencieux si fichier absent
        }
    }
}


