/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controller;

import javafx.scene.control.TextArea;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FiabiliteController {
    // constructeurs de la fiabilité
    static class Evenement {
        LocalTime heure;
        String type; // "A" ou "D"

        public Evenement(LocalTime heure, String type) {
            this.heure = heure;
            this.type = type;
        }
    }

    //classe de calcul de la fiabilité
    public static void calculerFiabilite(TextArea outputArea) {
        Map<String, List<Evenement>> evenementsParMachine = new HashMap<>(); //associe un evenement a une machine
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); //formatter les heures de debut et fin d'utilisation des machines

        try (BufferedReader reader = new BufferedReader(new FileReader("suiviMaintenance.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                if (ligne.trim().isEmpty()) continue;

                String[] tokens = ligne.trim().split("\\s+"); //ligne divisée
                if (tokens.length < 4) {
                    outputArea.appendText("Ligne ignorée, format incorrect: " + ligne + "\n");
                    continue;
                }
                String heureStr = tokens[1].replace(":", "");
                LocalTime heure = LocalTime.parse(heureStr, DateTimeFormatter.ofPattern("HHmm")); //heure extraite et formatée pour faciliter le calcul
                String machine = tokens[2]; //extraction de la machine
                String type = tokens[3]; //extraction du type d'evenement (A ou D)

                evenementsParMachine.computeIfAbsent(machine, k -> new ArrayList<>())
                        .add(new Evenement(heure, type));
            }
        } catch (IOException e) {
            outputArea.appendText("Erreur de lecture : " + e.getMessage() + "\n");
            return;
        }

        Map<String, Double> fiabilites = new HashMap<>(); //chaque machine associée a une fiabilité

        for (String machine : evenementsParMachine.keySet()) {
            List<Evenement> liste = evenementsParMachine.get(machine);
            liste.sort(Comparator.comparing(e -> e.heure)); // Tri des événements par heure

            LocalTime heureDebut = LocalTime.of(6, 0); // Heure de début de la journée de fonctionnement
            LocalTime heureFin = LocalTime.of(20, 0); // Heure de fin de la journée de fonctionnement

            long totalFonctionnement = 0; //comptabilise le temps de fonctionnement et de panne
            long totalPanne = 0;
            LocalTime dernierDebut = heureDebut;
            boolean enPanne = false;

            //calcul du temps total de fonctionnement selon les temos de panne
            for (Evenement ev : liste) {
                if (ev.type.equals("A") && !enPanne) {
                    totalFonctionnement += Duration.between(dernierDebut, ev.heure).toMinutes();
                    enPanne = true;
                    dernierDebut = ev.heure;
                } else if (ev.type.equals("D") && enPanne) {
                    totalPanne += Duration.between(dernierDebut, ev.heure).toMinutes();
                    enPanne = false;
                    dernierDebut = ev.heure;
                }
            }

            if (!enPanne) {
                totalFonctionnement += Duration.between(dernierDebut, heureFin).toMinutes();
            } else {
                totalPanne += Duration.between(dernierDebut, heureFin).toMinutes();
            }

            long total = totalFonctionnement + totalPanne;
            double fiabilite = total > 0 ? ((double) totalFonctionnement / total) : 0.0;
            fiabilites.put(machine, fiabilite);
        }

        // Tri par ordre de fiabilité et affichage
        List<Map.Entry<String, Double>> listeTriee = new ArrayList<>(fiabilites.entrySet());
        listeTriee.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        outputArea.appendText("Fiabilité des machines :\n");
        for (Map.Entry<String, Double> entry : listeTriee) {
            outputArea.appendText(entry.getKey() + " : " + String.format("%.2f", entry.getValue() * 100) + " %\n");
        }
    }
}
