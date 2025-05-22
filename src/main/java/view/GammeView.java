/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;


import javafx.scene.control.*;


import java.util.*;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import controller.GammeController;
import model.Gamme;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javafx.scene.layout.VBox;

public class GammeView {

    public static void ouvrirFenetreGamme(TextArea outputArea) {
        Stage gammeStage = new Stage();
        gammeStage.setTitle("Ajouter une Gamme");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField refField = new TextField();
        TextField codeProduitField = new TextField();

        List<String> machinesDisponibles = getMachinesDisponibles();
        VBox machinesBox = new VBox(5);
        machinesBox.getChildren().add(new Label("Machines utilisées :"));

        Map<String, CheckBox> machinesCheckBoxes = new HashMap<>();
        Map<String, TextField> machinesEtDuree = new HashMap<>();

        // Création des checkboxes et champs de durée pour chaque machine
        for (String codeMachine : machinesDisponibles) {
            CheckBox cb = new CheckBox(codeMachine);
            machinesBox.getChildren().add(cb);
            machinesCheckBoxes.put(codeMachine, cb);

            TextField dureeField = new TextField();
            dureeField.setPromptText("Durée (h)");
            machinesBox.getChildren().add(dureeField);
            machinesEtDuree.put(codeMachine, dureeField);
        }

        Button btnAjouter = new Button("Ajouter");
        btnAjouter.setOnAction(e -> {
            String refGamme = refField.getText();
            String codeProduit = codeProduitField.getText();
            double coutTotal = 0;

            // Création d'une liste pour stocker les usages de machines (nom, durée, coût horaire)
            List<Gamme.MachineUsage> usages = new ArrayList<>();

            // Pour chaque machine sélectionnée, on ajoute son usage à la liste
            for (String codeMachine : machinesDisponibles) {
                CheckBox cb = machinesCheckBoxes.get(codeMachine);
                if (cb.isSelected()) {
                    String dureeTxt = machinesEtDuree.get(codeMachine).getText();
                    try {
                        double duree = Double.parseDouble(dureeTxt);
                        double coutH  = getCoutHoraireForMachine(codeMachine); // Récupérer le coût horaire
                        usages.add(new Gamme.MachineUsage(codeMachine, duree, coutH));
                        coutTotal += duree * coutH; // Calcul du coût total
                    } catch (NumberFormatException ex) {
                        outputArea.appendText("Durée invalide pour " + codeMachine + "\n");
                    }
                }
            }

            // Enregistrement de la gamme avec ses machines et durées
            GammeController.enregistrerGamme(refGamme, codeProduit, usages);

           
            gammeStage.close();
        });

        // Ajouter les éléments dans la fenêtre (les champs de texte et la liste des machines)
        VBox root = new VBox(10,
                new Label("Référence gamme :"), refField,
                new Label("Designation Gamme :"), codeProduitField,
                machinesBox,
                btnAjouter
        );
        root.setPadding(new javafx.geometry.Insets(10));

        gammeStage.setScene(new Scene(root, 400, 600));
        gammeStage.show();
    }

    // Méthode pour récupérer les machines disponibles (en utilisant les fichiers machines_base.txt et machines.txt)
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

    // Méthode pour récupérer le coût horaire d'une machine donnée
    private static double getCoutHoraireForMachine(String machineCode) {
        // Lire d'abord les machines de base
        try (BufferedReader reader = new BufferedReader(new FileReader("machines_base.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(" "); // Séparer la ligne par espaces
                if (parts.length >= 4 && parts[0].equals(machineCode)) { // Vérifier que le nom de la machine correspond
                    return Double.parseDouble(parts[3]); // Retourner le coût horaire (4ème colonne)
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Si la machine n'est pas trouvée dans machines_base.txt, vérifier dans machines.txt
        try (BufferedReader reader = new BufferedReader(new FileReader("machines.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(" "); // Séparer la ligne par espaces
                if (parts.length >= 4 && parts[0].equals(machineCode)) { // Vérifier que le nom de la machine correspond
                    return Double.parseDouble(parts[3]); // Retourner le coût horaire (4ème colonne)
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0; // Retourner 0 si la machine n'est pas trouvée
    }
}


