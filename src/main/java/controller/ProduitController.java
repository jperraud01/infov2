/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controller;
import model.Produit;
import javafx.scene.control.TextArea;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProduitController {

   
    //lit toutes les gammes (de base et ajoutées) et renvoie le cout total de chaque machine (hashmap connecte ref de la machine et son cout)
    private static Map<String, Double> chargerCoutGammes() {
        Map<String, Double> map = new LinkedHashMap<>();
        lireGammesDepuis("gammes_base.txt", map);
        lireGammesDepuis("gammes.txt",      map);
        return map;
    }

    private static void lireGammesDepuis(String fichier, Map<String, Double> map) { //lit les gammes pour pouvoir les séectionner a la création d'un produit
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(" ");
                if (parts.length < 2) continue;
                String ref = parts[0];
                double total = 0;
                
                for (int i = 2; i + 2 < parts.length; i += 3) {
                    double duree       = Double.parseDouble(parts[i + 1]); //recupere durée de chaque machine par gamme
                    double coutHoraire = Double.parseDouble(parts[i + 2]); //recupere coutHoraire par machine
                    total += duree * coutHoraire;
                }
                map.put(ref, total);
            }
        } catch (IOException e) {
        }
    }

    public static void afficherProduits(TextArea outputArea) {
        outputArea.appendText("Produits enregistrés :\n");
        Map<String, Double> gammeMap = chargerCoutGammes();

        // Affichage des produits de base
        try (BufferedReader reader = new BufferedReader(new FileReader("produit_base.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(" ");
                if (parts.length < 2) continue;
                String codeProduit = parts[0];
                String designation = parts[1];

                // Somme des coûts de chaque gamme 
                double coutTotal = 0;
                for (int i = 2; i < parts.length; i++) {
                    coutTotal += gammeMap.getOrDefault(parts[i], 0.0);
                }

                outputArea.appendText(
                  "Produit de base: " + codeProduit +
                  " - " + designation +
                  " - Coût total: " + String.format("%.2f", coutTotal) + " €\n"
                );
            }
        } catch (IOException ex) {
            outputArea.appendText("Erreur lecture produit_base.txt : " + ex.getMessage() + "\n");
        }

        //  Affichage des produits ajoutés par l'utilisateur
        try (BufferedReader reader = new BufferedReader(new FileReader("produits.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(" ");
                if (parts.length < 2) continue;
                String codeProduit = parts[0];
                String designation = parts[1];

                
                double coutTotal = 0;
                for (int i = 2; i + 1 < parts.length; i += 2) {
                    coutTotal += Double.parseDouble(parts[i + 1]);
                }

                outputArea.appendText(
                  "Produit ajouté: " + codeProduit +
                  " - " + designation +
                  " - Coût total: " + String.format("%.2f", coutTotal) + " €\n"
                );
            }
        } catch (IOException ex) {
            outputArea.appendText("Erreur lecture produits.txt : " + ex.getMessage() + "\n");
        }
    }
    
    //ecrit les infos sur chaque produit entrée par l'utilisateur dans fichier texte
     public static void enregistrerProduit(Produit produit) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("produits.txt", true))) {
        writer.write(produit.getCodeProduit() + " " + produit.getDesignation());

        // Ajouter chaque gamme utilisée et son coût
        for (Produit.GammeUsage gammeUsage : produit.getGammes()) {
            writer.write(" " + gammeUsage.getRefGamme() + " " + gammeUsage.getCout());
        }

        writer.write("\n");
        System.out.println("Produit enregistré dans produits.txt : " + produit.getCodeProduit() + " - " + produit.getDesignation());
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}
}

