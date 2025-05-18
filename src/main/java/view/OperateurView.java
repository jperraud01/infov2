/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;


import controller.OperateurController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Operateur;

import java.util.ArrayList;
import java.util.List;

public class OperateurView {

    public static void ouvrirFenetreOperateur(Stage primaryStage, Runnable onSuccess) {
        Stage stage = new Stage();
        stage.setTitle("Identification de l'opérateur");

        TextField codeField = new TextField();
        TextField nomField = new TextField();

        String[] competencesDispo = { "Forer", "Usiner", "Chanfreiner", "Souder", "Découper" };
        VBox competenceBox = new VBox(5);
        List<CheckBox> checkboxes = new ArrayList<>();
        for (String comp : competencesDispo) {
            CheckBox cb = new CheckBox(comp);
            checkboxes.add(cb);
            competenceBox.getChildren().add(cb);
        }

        Button validerBtn = new Button("Valider");
        validerBtn.setOnAction(e -> {
            String code = codeField.getText();
            String nom = nomField.getText();
            List<String> competences = new ArrayList<>();
            for (CheckBox cb : checkboxes) {
                if (cb.isSelected()) {
                    competences.add(cb.getText());
                }
            }

            Operateur op = new Operateur(code, nom, competences);
            OperateurController.enregistrerOperateur(op);
            stage.close();
            onSuccess.run(); // Lance l'interface principale
        });

        VBox root = new VBox(10,
            new Label("Code opérateur :"), codeField,
            new Label("Nom opérateur :"), nomField,
            new Label("Compétences :"), competenceBox,
            validerBtn
        );
        root.setPadding(new javafx.geometry.Insets(15));

        stage.setScene(new Scene(root, 350, 400));
        stage.show();
    }
}

