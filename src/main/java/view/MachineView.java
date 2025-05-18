package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import controller.MachineController;
import controller.OperateurController;
import model.Machine;
import model.Operateur;

public class MachineView {

    public static void ouvrirFenetreMachine(TextArea outputArea) {
        Stage stage = new Stage();
        stage.setTitle("Ajouter une Machine");

        TextField refField = new TextField();
        TextField designationField = new TextField();
        TextField typeField = new TextField();
        TextField coutField = new TextField();
        TextField abscisseField = new TextField();
        TextField ordonneeField = new TextField();

        VBox root = new VBox(10,
            new Label("Référence:"), refField,
            new Label("Désignation:"), designationField,
            new Label("Type:"), typeField,
            new Label("Coût Horaire:"), coutField,
            new Label("Abscisse:"), abscisseField,
            new Label("Ordonnée:"), ordonneeField
        );

        Button creerBtn = new Button("Créer");
        creerBtn.setOnAction(e -> {
            String ref = refField.getText();
            String designation = designationField.getText();
            String type = typeField.getText();
            float cout = Float.parseFloat(coutField.getText());
            int x = Integer.parseInt(abscisseField.getText());
            int y = Integer.parseInt(ordonneeField.getText());

            Machine machine = new Machine(ref, designation, type, cout, x, y);
            MachineController.enregistrerMachine(machine);

            OperationView.ouvrirFenetreOperationPourMachine(ref);

            stage.close();
        });

        root.getChildren().add(creerBtn);
        root.setPadding(new javafx.geometry.Insets(10));

        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void afficherPlanMachines() {
        Stage stage = new Stage();
        stage.setTitle("Plan des Machines");

        Pane planPane = new Pane();

        Map<String, String> operationsParMachine = new HashMap<>();
        try (BufferedReader opReader = new BufferedReader(new FileReader("operations_par_machine.txt"))) {
            String ligneOp;
            while ((ligneOp = opReader.readLine()) != null) {
                if (ligneOp.startsWith("Machine")) {
                    String[] parts = ligneOp.split(" : ");
                    if (parts.length == 2) {
                        String ref = parts[0].replace("Machine", "").trim();
                        String ops = parts[1].trim();
                        operationsParMachine.put(ref, ops);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Operateur operateur = OperateurController.getOperateurActif();
        List<String> toutesLesMachines = new ArrayList<>();

        try (BufferedReader baseReader = new BufferedReader(new FileReader("machines_base.txt"))) {
            String ligne;
            while ((ligne = baseReader.readLine()) != null) {
                toutesLesMachines.add(ligne);
            }
        } catch (IOException e) {
            System.err.println("Fichier machines_base.txt manquant (ce n'est pas bloquant).");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("machines.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                toutesLesMachines.add(ligne);
            }
        } catch (IOException e) {
            System.err.println("Fichier machines.txt manquant.");
        }

        for (String ligne : toutesLesMachines) {
            String[] parts = ligne.split(" ");
            if (parts.length >= 7) {
                String ref = parts[0];
                String designation = parts[1];
                String type = parts[2];
                float cout = Float.parseFloat(parts[3]);
                int x = Integer.parseInt(parts[4]);
                int y = Integer.parseInt(parts[5]);
                String etat = parts[6];

                String ops = operationsParMachine.getOrDefault(ref, "");
                boolean compatible = true;
                if (!ops.isEmpty() && operateur != null) {
                    String[] opList = ops.split(",\\s*");
                    for (String op : opList) {
                        if (!operateur.getCompetences().contains(op)) {
                            compatible = false;
                            break;
                        }
                    }
                }

                Color couleur;
                if (etat.equals("En_panne")) {
                    couleur = Color.TOMATO;
                } else if (etat.equals("Maintenance")) {
                    couleur = Color.BEIGE;
                } else if (!compatible) {
                    couleur = Color.LIGHTGRAY;
                } else {
                    couleur = Color.LIGHTGREEN;
                }


                Rectangle rect = new Rectangle(50, 50);
                rect.setFill(couleur);
                rect.setStroke(Color.BLACK);
                rect.setLayoutX(x);
                rect.setLayoutY(y);

                Text designationLabel = new Text(designation);
                designationLabel.setLayoutX(x + 5);
                designationLabel.setLayoutY(y + 35);

                Text refLabel = new Text(ref);
                refLabel.setLayoutX(x + 5);
                refLabel.setLayoutY(y + 20);

                planPane.getChildren().addAll(rect, designationLabel, refLabel);

                if (!ops.isEmpty()) {
                    Text opsLabel = new Text(ops);
                    opsLabel.setLayoutX(x + 5);
                    opsLabel.setLayoutY(y + 50);
                    planPane.getChildren().add(opsLabel);
                }
            }
        }

        Scene scene = new Scene(planPane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private static void changerEtatDansFichier(String ref, String nouvelEtat) {
        File original = new File("machines.txt");
        File temp = new File("machines_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(original));
             BufferedWriter writer = new BufferedWriter(new FileWriter(temp))) {

            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(" ");
                if (parts.length >= 6 && parts[0].equals(ref)) {
                    for (int i = 0; i < 6; i++) {
                        writer.write(parts[i] + " ");
                    }
                    writer.write(nouvelEtat + "\n");
                } else {
                    writer.write(ligne + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        original.delete();
        temp.renameTo(original);
    }

    public static void afficherListeMachines(TextArea outputArea) {
        Stage stage = new Stage();
        stage.setTitle("Liste des Machines");

        VBox machineList = new VBox(10);
        machineList.setPadding(new javafx.geometry.Insets(10));

        try (BufferedReader reader = new BufferedReader(new FileReader("machines.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(" ");
                if (parts.length >= 6) {
                    String ref = parts[0];
                    String designation = parts[1];

                    Button btnMachine = new Button(ref + " - " + designation);

                    btnMachine.setOnAction(e -> {
                        ContextMenu menu = new ContextMenu();

                        MenuItem modifierOp = new MenuItem("Modifier les opérations");
                        modifierOp.setOnAction(ev -> OperationView.ouvrirFenetreOperationPourMachine(ref));

                        MenuItem supprimer = new MenuItem("Supprimer la machine");
                        supprimer.setOnAction(ev -> {
                            MachineController.supprimerMachine(ref);
                            stage.close();
                            afficherListeMachines(outputArea);
                        });

                        MenuItem changerEtat = new MenuItem("Changer l'état");
                        changerEtat.setOnAction(ev -> {
                            Stage stageEtat = new Stage();
                            stageEtat.setTitle("État de " + ref);

                            ToggleGroup group = new ToggleGroup();
                            RadioButton marche = new RadioButton("En marche");
                            RadioButton maintenance = new RadioButton("Maintenance");
                            RadioButton panne = new RadioButton("En panne");

                            marche.setToggleGroup(group);
                            maintenance.setToggleGroup(group);
                            panne.setToggleGroup(group);
                            marche.setSelected(true);

                            Button valider = new Button("Valider");
                            valider.setOnAction(evt -> {
                                String nouvelEtat = "En_marche";
                                if (maintenance.isSelected()) nouvelEtat = "Maintenance";
                                else if (panne.isSelected()) nouvelEtat = "En_panne";

                                changerEtatDansFichier(ref, nouvelEtat);
                                stageEtat.close();
                            });

                            VBox box = new VBox(10, marche, maintenance, panne, valider);
                            box.setPadding(new javafx.geometry.Insets(10));
                            stageEtat.setScene(new Scene(box, 200, 200));
                            stageEtat.show();
                        });

                        menu.getItems().addAll(modifierOp, changerEtat, supprimer);
                        menu.show(btnMachine, Side.RIGHT, 0, 0);
                    });

                    machineList.getChildren().add(btnMachine);
                }
            }
        } catch (IOException ex) {
            outputArea.appendText("Erreur lors de la lecture des machines.\n");
        }

        ScrollPane scroll = new ScrollPane(machineList);
        Scene scene = new Scene(scroll, 400, 400);
        stage.setScene(scene);
        stage.show();
    }
}
