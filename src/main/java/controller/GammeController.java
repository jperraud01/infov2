/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author juper
 */

package controller;

import javafx.scene.control.TextArea;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Gamme;

public class GammeController {

    // Affiche les gammes enregistrées (de base et ajoutées)
  public static void afficherGammes(TextArea outputArea) {
    outputArea.appendText("Gammes enregistrées :\n");

    // Affichage des gammes de base
    try (BufferedReader reader = new BufferedReader(new FileReader("gammes_base.txt"))) {
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            String[] parts = ligne.split(" ");
            if (parts.length >= 2) { // Vérifier qu'il y a au moins 2 éléments (code et désignation)
                String refGamme = parts[0]; // Référence de la gamme
                String designationGamme = parts[1]; // Désignation de la gamme

                // Calcul du coût total pour la gamme de base
                double coutTotal = 0;
                for (int i = 2; i < parts.length; i += 3) { // Chaque machine a 3 éléments : nom, durée, coût horaire
                    String machineCode = parts[i];
                    double duree = Double.parseDouble(parts[i + 1]);
                    double coutHoraire = MachineController.getCoutHoraireForMachine(machineCode); // Récupérer le coût horaire de la machine
                    coutTotal += duree * coutHoraire; // Calcul avec la durée d'utilisation
                }

                // Affichage de la gamme de base avec son coût de production
               

                outputArea.appendText("Gamme de base: " + refGamme + " - " + designationGamme + " - Coût de production: " + String.format("%.2f", coutTotal) + " €\n");
            }
        }
    } catch (IOException ex) {
        outputArea.appendText("Erreur lors de la lecture de gammes_base.txt : " + ex.getMessage() + "\n");
    }

    // Affichage des gammes ajoutées
    try (BufferedReader reader = new BufferedReader(new FileReader("gammes.txt"))) {
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            String[] parts = ligne.split(" ");
            if (parts.length >= 2) { // Vérifier qu'il y a au moins 2 éléments (code et désignation)
                String refGamme = parts[0]; // Référence de la gamme
                String designationGamme = parts[1]; // Désignation de la gamme

                // Calcul du coût total pour la gamme ajoutée
                double coutTotal = 0;
                for (int i = 2; i < parts.length; i += 3) { // Chaque machine a 3 éléments : nom, durée, coût horaire
                    String machineCode = parts[i];
                    double duree = Double.parseDouble(parts[i + 1]);
                    double coutHoraire = MachineController.getCoutHoraireForMachine(machineCode); // Récupérer le coût horaire de la machine
                    coutTotal += duree * coutHoraire; // Calcul avec la durée d'utilisation
                }

                // Affichage de la gamme ajoutée avec son coût de production
                outputArea.appendText("Gamme ajoutée: " + refGamme + " - " + designationGamme + " - Coût de production: " + String.format("%.2f", coutTotal) + " €\n");
            }
        }
    } catch (IOException ex) {
        outputArea.appendText("Erreur lors de la lecture des gammes : " + ex.getMessage() + "\n");
    }
}

   public static void enregistrerGamme(
    String refGamme,
    String codeProduit,
    List<Gamme.MachineUsage> usages
) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("gammes.txt", true))) {
        writer.write(refGamme + " " + codeProduit);
        for (Gamme.MachineUsage u : usages) {
            writer.write(" " + u.getMachineCode()
                         + " " + u.getDuree()
                         + " " + u.getCoutHoraire());
        }
        writer.write("\n");
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}


}