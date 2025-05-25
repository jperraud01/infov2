/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


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

        // charger toutes les gammes (de base + ajoutées) avec leur coût (méthode si dessous)
        Map<String, Double> gammeCostMap = chargerCoutGammes();

        // créer la liste de CheckBoxes pour chaque gamme
        VBox gammesBox = new VBox(5);
        gammesBox.getChildren().add(new Label("Gammes utilisées :"));

        Map<String, CheckBox> checkboxes = new HashMap<>(); //hashmap pour associer ref de la gamme et son cout
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
            // parcourt les gammes cochées et somme les coûts
            for (Map.Entry<String, CheckBox> entry : checkboxes.entrySet()) {
                if (entry.getValue().isSelected()) {
                    String ref = entry.getKey();
                    double coutG = gammeCostMap.getOrDefault(ref, 0.0);
                    coutTotal += coutG;
                    produit.ajouterGamme(ref, coutG);
                }
            }

            // appelle la méthode du controller pour enregistrer le produit (avec liste des gammes et leur coût)
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

    
     //Lit les fichiers 'gammes_base.txt' et 'gammes.txt' et renvoie une paire ref et cout pour chaque gamme dans une map
    private static Map<String, Double> chargerCoutGammes() {
        Map<String, Double> map = new LinkedHashMap<>();
        // Lecture des gammes de base
        lireGammesDepuis("gammes_base.txt", map);
        // Lecture des gammes ajoutées
        lireGammesDepuis("gammes.txt",     map);
        return map;
    }

    //méthode pour lire les gammes et recup les infos, utilisée ci dessus
    private static void lireGammesDepuis(String fichier, Map<String, Double> map) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) { //parcourt le fichier tant qu'il y a du texte
                String[] parts = ligne.split(" "); //sépare les lignes selon les espaces
                if (parts.length < 2) continue;
                String ref    = parts[0];
             
                double total = 0;
                for (int i = 2; i + 2 < parts.length; i += 3) { //si il y a toutes les infos (3 colonnes)
                    double duree      = Double.parseDouble(parts[i + 1]); //recupere durée
                    double coutHoraire= Double.parseDouble(parts[i + 2]); //recupere coutHoraire
                    total += duree * coutHoraire;
                }
                map.put(ref, total);
            }
        } catch (IOException e) {
           
        }
    }
}


