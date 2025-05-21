/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package controller;

import javafx.scene.control.*;
import model.Produit;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitController {

    public static void enregistrerProduit(Produit produit) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("produits.txt", true))) {
            writer.write(produit.getCodeProduit() + " " + produit.getDproduit());

            // Ajouter chaque machine avec la durée et le coût horaire
            for (Produit.Machine machine : produit.getMachines()) {
                writer.write(" " + machine.getNom() + " " + machine.getDuree() + " " + machine.getCoutHoraire());
            }
            writer.write("\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void afficherProduits(TextArea outputArea) {
        outputArea.appendText("Produits enregistrés :\n");

        // Affichage des produits de base
        try (BufferedReader reader = new BufferedReader(new FileReader("produit_base.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(" ");
                if (parts.length >= 2) {
                    String codeProduit = parts[0];
                    String designationProduit = parts[1];
                    List<String> machines = new ArrayList<>();
                    for (int i = 2; i < parts.length; i++) {
                        machines.add(parts[i]);
                    }

                    // Calcul du coût de production pour le produit de base
                    double coutTotal = 0;
                    for (String machine : machines) {
                        double coutHoraire = getCoutHoraireForMachine(machine);
                        coutTotal += coutHoraire; 
                    }

                    // Affichage du produit de base avec son coût de production
                    outputArea.appendText("Produit de base: " + codeProduit + " - " + designationProduit + " - Coût de production: " + String.format("%.2f", coutTotal) + " €\n");
                }
            }
        } catch (IOException ex) {
            outputArea.appendText("Erreur lors de la lecture de produit_base.txt : " + ex.getMessage() + "\n");
        }

        // Affichage des produits ajoutés
        try (BufferedReader reader = new BufferedReader(new FileReader("produits.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(" ");
                if (parts.length >= 2) {
                    String codeProduit = parts[0];
                    String designationProduit = parts[1];
                    List<String> machines = new ArrayList<>();
                    List<Double> durees = new ArrayList<>();

                    for (int i = 2; i < parts.length; i += 3) {
                        if (i + 2 < parts.length) {
                            machines.add(parts[i]);
                            try {
                                durees.add(Double.parseDouble(parts[i + 1]));
                            } catch (NumberFormatException ex) {
                                outputArea.appendText("Erreur : durée invalide pour la machine " + parts[i] + "\n");
                            }
                        }
                    }

                    double coutTotal = 0;
                    for (int i = 0; i < machines.size(); i++) {
                        double coutHoraire = getCoutHoraireForMachine(machines.get(i));
                        coutTotal += coutHoraire * durees.get(i); 
                    }

                    outputArea.appendText("Produit ajouté: " + codeProduit + " - " + designationProduit + " - Coût de production: " + String.format("%.2f", coutTotal) + " €\n");
                }
            }
        } catch (IOException ex) {
            outputArea.appendText("Erreur lors de la lecture des produits : " + ex.getMessage() + "\n");
        }
    }

    public static double getCoutHoraireForMachine(String machine) {
        try (BufferedReader reader = new BufferedReader(new FileReader("machines_base.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(" ");
                if (parts.length >= 4 && parts[0].equals(machine)) {
                    return Double.parseDouble(parts[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("machines.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(" ");
                if (parts.length >= 4 && parts[0].equals(machine)) {
                    return Double.parseDouble(parts[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0; 
    }
}
