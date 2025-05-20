/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.OperationController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class OperationView {

    public static void ouvrirFenetreOperationPourMachine(String machineRef) {
        Stage stage = new Stage();
        stage.setTitle("Associer opérations à la machine : " + machineRef);

        String[] operations = { "Forer", "Usiner", "Chanfreiner", "Souder", "Découper","Percer","Polir" };

        VBox checkBoxContainer = new VBox(10);
        List<CheckBox> checkBoxes = new ArrayList<>();

        for (String op : operations) {
            CheckBox cb = new CheckBox(op);
            checkBoxes.add(cb);
            checkBoxContainer.getChildren().add(cb);
        }

        Button validerBtn = new Button("Valider");
        validerBtn.setOnAction(e -> {
            List<String> operationsSelectionnees = new ArrayList<>();
            for (CheckBox cb : checkBoxes) {
                if (cb.isSelected()) {
                    operationsSelectionnees.add(cb.getText());
                }
            }

            OperationController.enregistrerOperationsPourMachine(machineRef, operationsSelectionnees);
            stage.close();
        });

        VBox root = new VBox(15, new Label("Sélectionnez les opérations réalisables :"), checkBoxContainer, validerBtn);
        root.setPadding(new javafx.geometry.Insets(10));
        stage.setScene(new Scene(root));
        stage.show();
    }
}

