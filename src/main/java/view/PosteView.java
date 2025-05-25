/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Poste;
import controller.PosteController;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;


public class PosteView {
    
   public static void ouvrirFenetrePoste(TextArea outputArea) {
        Stage stage = new Stage();
        stage.setTitle("Ajouter un Poste");

        TextField refField = new TextField();
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
            new Label("Référence du poste :"), refField,
            new Label("Désignation du poste :"), designationField,
            machinesLabel, machinesBox, addMachineBtn
        );

        Button btnAjouter = new Button("Ajouter");
        btnAjouter.setOnAction(e -> {
            String ref = refField.getText();
            String designation = designationField.getText();

            Poste poste = new Poste(ref, designation);

            // Lire toutes les machines entrées
            for (javafx.scene.Node node : machinesBox.getChildren()) {
                if (node instanceof TextField) {
                    TextField tf = (TextField) node;
                    if (!tf.getText().isBlank()) {
                         poste.ajouterMachineTexte(tf.getText());
                    }
                }     
                }
            
            PosteController.enregistrerPoste(poste); //envoie les infos entrées par l'utilisateur au controlleur pour les entrer dans le fichier 
            
            stage.close();
        });

        root.getChildren().add(btnAjouter);
        root.setPadding(new javafx.geometry.Insets(10));

        stage.setScene(new Scene(root, 400, 400));
        stage.show();
    }



    public static void afficherListePostes(TextArea outputArea) {
        Stage stage = new Stage();
        stage.setTitle("Liste des Postes");

        VBox posteList = new VBox(10);
        posteList.setPadding(new Insets(10));

        try (BufferedReader reader = new BufferedReader(new FileReader("postes.txt"))) { //recup infos dans fichier pour les afficher 
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(" "); //lit le fichier entre les espaces pour recup élements individuels
                if (parts.length >= 2) {
                    String ref = parts[0]; //récupere premier element: reference
                    String designation = parts[1]; //recupere deuxieme element: designation

                    Button btnPoste = new Button(ref + " - " + designation);

                    btnPoste.setOnAction(e -> {
                        ContextMenu menu = new ContextMenu();

                        MenuItem supprimer = new MenuItem("Supprimer le poste");
                        supprimer.setOnAction(ev -> {
                            supprimerPoste(ref);
                            
                            stage.close();
                            afficherListePostes(outputArea); 
                        });

                        menu.getItems().addAll( supprimer);
                        menu.show(btnPoste, Side.RIGHT, 0, 0);
                    });

                    posteList.getChildren().add(btnPoste);
                }
            }
        } catch (IOException e) {
            outputArea.appendText("Erreur lecture postes.txt\n");
        }

        Scene scene = new Scene(new ScrollPane(posteList), 400, 400);
        stage.setScene(scene);
        stage.show();
    }
    //méthode appelee au dessus pour supprimer un poste
    private static void supprimerPoste(String ref) {
        File input = new File("postes.txt");
        File temp = new File("postes_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(input));
             BufferedWriter writer = new BufferedWriter(new FileWriter(temp))) {

            String ligne;
            while ((ligne = reader.readLine()) != null) {
                if (!ligne.startsWith(ref + " ")) {
                    writer.write(ligne + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        input.delete();
        temp.renameTo(input);
    }
}

  
