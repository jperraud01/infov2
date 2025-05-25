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

    public static void ouvrirFenetreMachine(TextArea outputArea) { //ouvre la fenetre qui permet d'ajouter une machine
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

 //méthode pour afficher le plan des machines 
    public static void afficherPlanMachines() {
    Stage stage = new Stage();
    stage.setTitle("Plan des Machines");

    Pane planPane = new Pane();

    Map<String, String> operationsParMachine = new HashMap<>(); //hashmap pour associer une machine a son opération
    //pour récupérer les infos sur chaque machine
    try (BufferedReader opReader = new BufferedReader(new FileReader("operations_par_machine.txt"))) {
        String ligneOp;
        while ((ligneOp = opReader.readLine()) != null) {
            if (ligneOp.startsWith("Machine")) {
                String[] parts = ligneOp.split(" : "); //sépare les élements du fichier selon les doubke point
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

    // Récupérer l'opérateur 
    Operateur operateur = OperateurController.getOperateurActif();
    List<String> toutesLesMachines = new ArrayList<>();

    // pour lire les machines depuis "machines_base.txt" et "machines.txt"
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
    //pour recupérer les coordonnées et autres infos sur la machine
    for (String ligne : toutesLesMachines) {
        String[] parts = ligne.split(" "); //on sépare le fichier selon les espaces
        if (parts.length >= 7) {
            String ref = parts[0]; //premiere colonne est la ref
            String designation = parts[1]; //deuxieme colonne est la désignation
            String type = parts[2]; //3eme est le type
            float cout = Float.parseFloat(parts[3]); //4me est le cout
            int x = Integer.parseInt(parts[4]); //5eme et 6eme sont les coordonnées
            int y = Integer.parseInt(parts[5]);
            String etat = parts[6];

            // pour récupérer les opérations requises pour la machine
            String ops = operationsParMachine.getOrDefault(ref, "");
            boolean compatible = true;

            // pour vérifier si l'opérateur a toutes les compétences nécessaires pour cette machine
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

            // Si l'opérateur a les compétences la couleur de la machine est en fonction de son état
            if (compatible) {
                if (etat.equals("En_panne")) {
                    couleur = Color.TOMATO;  // Rouge pour en panne
                } else if (etat.equals("Maintenance")) {
                    couleur = Color.BEIGE;  // Beige pour maintenance
                } else {
                    couleur = Color.LIGHTGREEN;  // Vert pour en marche
                }
            } else {
                // Si l'opérateur n'a pas les compétences la machine est grisée
                couleur = Color.LIGHTGRAY; 
            }

            // Création du rectangle représentant la machine
            Rectangle rect = new Rectangle(50, 50);
            rect.setFill(couleur);
            rect.setStroke(Color.BLACK);
            rect.setLayoutX(x);
            rect.setLayoutY(y);

            // Ajouter des informations sur la machine dans le plan (référence, désignation,opération)
            Text designationLabel = new Text(designation);
            designationLabel.setLayoutX(x + 5);
            designationLabel.setLayoutY(y + 35);

            Text refLabel = new Text(ref);
            refLabel.setLayoutX(x + 5);
            refLabel.setLayoutY(y + 20);

            planPane.getChildren().addAll(rect, designationLabel, refLabel);

            // Ajouter les opérations (si disponibles)
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
    //pour changer l'etat d'une machine et donc sa couleur
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

 //pour afficher les machines dans la outputArea
public static void afficherListeMachines(TextArea outputArea) {
    Stage stage = new Stage();
    stage.setTitle("Liste des Machines");

    VBox machineList = new VBox(10);
    machineList.setPadding(new javafx.geometry.Insets(10));

    // Lire les machines de base depuis 'machines_base.txt'
    try (BufferedReader reader = new BufferedReader(new FileReader("machines_base.txt"))) {
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            String[] parts = ligne.split(" ");
            if (parts.length >= 7) {
                String ref = parts[0];               // Référence de la machine
                String designation = parts[1];        // Désignation de la machine
                float coutHoraire = Float.parseFloat(parts[3]); // Coût horaire (4ème colonne)
                
                // Calcul du coût pour 1h d'utilisation
                float coutParHeure = coutHoraire * 1;  

                // Création du bouton avec la référence, la désignation et le coût
                Button btnMachine = new Button(ref + " - " + designation + " - Coût 1h: " + String.format("%.2f", coutParHeure) + " €");
                // Tout les boutons de l'interface concernant les machines
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
        outputArea.appendText("Erreur lors de la lecture des machines de base.\n");
    }

    // Lire les machines supplémentaires enregistrées par l'utilisateur depuis 'machines.txt'
    try (BufferedReader reader = new BufferedReader(new FileReader("machines.txt"))) {
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            String[] parts = ligne.split(" ");
            if (parts.length >= 6) {
                String ref = parts[0];               // Référence de la machine
                String designation = parts[1];        // Désignation de la machine
                float coutHoraire = Float.parseFloat(parts[3]); // Coût horaire (4ème colonne)
                
                // Calcul du coût pour 1h d'utilisation
                float coutParHeure = coutHoraire * 1;  

                // Création du bouton avec la référence, la désignation et le coût
                Button btnMachine = new Button(ref + " - " + designation + " - Coût par h: " + String.format("%.2f", coutParHeure) + " €");

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
        outputArea.appendText("Erreur lors de la lecture des machines supplémentaires.\n");
    }

    ScrollPane scroll = new ScrollPane(machineList);
   
    Scene scene = new Scene(scroll, 400, 400);
    stage.setScene(scene);
    stage.show();
}

}
