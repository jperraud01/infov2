/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.projet2025;

import javafx.application.Application;
import javafx.stage.Stage;
import view.OperateurView;

//classe séparée pour ouvrir la fenetre pour opérateur avant le reste de l'interface
public class OperateurApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Ouvre d'abord la fenêtre opérateur, voir opérateurView
        OperateurView.ouvrirFenetreOperateur(primaryStage, () -> {
            App app = new App();
            try {
                app.start(primaryStage); // Lance l'app principale
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
