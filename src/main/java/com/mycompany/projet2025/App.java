
package com.mycompany.projet2025;

import controller.MachineController;
import controller.PosteController;
import controller.ProduitController;
import controller.GammeController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import controller.FiabiliteController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javafx.application.Platform;
import view.MachineView;
import view.GammeView;
import view.PosteView;
import view.ProduitView;

public class App extends Application {

    private TextArea outputArea;
  

    @Override
public void start(Stage primaryStage) {
    primaryStage.setTitle("Atelier de Fabrication - Gestion");

    // on créé la zone de texte pour afficher les informations
    outputArea = new TextArea(); 
    outputArea.setEditable(false);
    outputArea.setPrefHeight(200);
    
    // Affichage du message d'accueil dans la outputArea
    outputArea.appendText("Bienvenue dans l'application de gestion de l'atelier de fabrication !\n");
    outputArea.appendText("Utilisez le menu pour naviguer à travers les différentes options.\n");

    // Menu principal pour chaque objet
    MenuBar menuBar = new MenuBar();
    Menu produitMenu = new Menu("Produit");
    Menu posteMenu = new Menu("Poste");
    Menu machineMenu = new Menu("Machine");
    Menu gammeMenu = new Menu("Gamme");
    Menu optimisationMenu = new Menu("Optimisation");
    
    //menuItems= menus deroulants
    MenuItem ajouterMachine = new MenuItem("Ajouter une machine");
    MenuItem afficherMachines = new MenuItem("Afficher les machines");
    MenuItem ajouterGamme = new MenuItem("Ajouter une gamme");
    MenuItem afficherGammes = new MenuItem("Afficher les gammes");
    MenuItem ajouterProduit = new MenuItem("Ajouter un produit");
    MenuItem afficherProduits = new MenuItem("Afficher les produits");
    MenuItem ajouterPoste = new MenuItem("Ajouter un poste");
    MenuItem afficherPostes = new MenuItem("Afficher un poste");
    MenuItem gererPostes = new MenuItem("Gérer les postes");
    posteMenu.getItems().add(gererPostes);
    gererPostes.setOnAction(e -> PosteView.afficherListePostes(outputArea));
    MenuItem calculerFiabilite = new MenuItem("Calculer la fiabilité");
    MenuItem afficherPlan = new MenuItem("Afficher le plan");
    machineMenu.getItems().add(afficherPlan);
    MenuItem gererMachines = new MenuItem("Gérer les machines");
    machineMenu.getItems().add(gererMachines);
    gererMachines.setOnAction(e -> MachineView.afficherListeMachines(outputArea));
    machineMenu.getItems().addAll(ajouterMachine, afficherMachines);
    gammeMenu.getItems().addAll(ajouterGamme, afficherGammes);
    produitMenu.getItems().addAll(ajouterProduit, afficherProduits);
    posteMenu.getItems().addAll(ajouterPoste, afficherPostes);
    optimisationMenu.getItems().add(calculerFiabilite);

    menuBar.getMenus().addAll(machineMenu, posteMenu, produitMenu, gammeMenu, optimisationMenu);

    // Actions des menus — Utilise les contrôleurs de chaque classe
    ajouterMachine.setOnAction(e -> MachineView.ouvrirFenetreMachine(outputArea));
    afficherMachines.setOnAction(e -> MachineController.afficherMachines(outputArea));

    ajouterGamme.setOnAction(e -> GammeView.ouvrirFenetreGamme(outputArea));
    afficherGammes.setOnAction(e -> GammeController.afficherGammes(outputArea));
    
    ajouterProduit.setOnAction(e -> ProduitView.ouvrirFenetreProduit(outputArea));
    afficherProduits.setOnAction(e -> ProduitController.afficherProduits(outputArea));

    ajouterPoste.setOnAction(e -> PosteView.ouvrirFenetrePoste(outputArea));
    afficherPostes.setOnAction(e -> PosteController.afficherPostes(outputArea));

    calculerFiabilite.setOnAction(e -> FiabiliteController.calculerFiabilite(outputArea));
    afficherPlan.setOnAction(e -> MachineView.afficherPlanMachines());

    // Pour fermer la fenetre 
    primaryStage.setOnCloseRequest(event -> {
        System.out.println("Fermeture de la fenêtre...");
        stop();
        Platform.exit();  
    });
    
    // Disposition des différentes parties de l'interface
    BorderPane root = new BorderPane();
    root.setTop(menuBar);
    root.setCenter(new ScrollPane(outputArea));

    Scene scene = new Scene(root, 500, 200);
    primaryStage.setScene(scene);
    primaryStage.show();
}

    @Override
    public void stop() {
        // Appelé à la fermeture de l'application pour vider les fichiers 
        viderFichier("machines.txt");
        viderFichier("produits.txt");
        viderFichier("postes.txt");
        viderFichier("gammes.txt");
         viderFichier("operateurs.txt");
         System.exit(0);
        
       
}
  //méhtode appelée dans stop pour vider 
    private void viderFichier(String nomFichier) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier))) {
        writer.write("");
    } catch (IOException e) {
        System.err.println("Erreur en vidant " + nomFichier);
    }
}

  //le main qui lance l'application
public static void main(String[] args) {
    javafx.application.Application.launch(OperateurApp.class);
    
}

}
