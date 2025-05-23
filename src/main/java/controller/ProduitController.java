/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/*package controller;

import javafx.scene.control.*;
import model.Produit;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitController {

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
}*/
package controller;
import model.Produit;
import javafx.scene.control.TextArea;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProduitController {

    /** Lit les gammes (base + ajoutées) et retourne ref→coûtTotal. */
    private static Map<String, Double> chargerCoutGammes() {
        Map<String, Double> map = new LinkedHashMap<>();
        lireGammesDepuis("gammes_base.txt", map);
        lireGammesDepuis("gammes.txt",      map);
        return map;
    }

    private static void lireGammesDepuis(String fichier, Map<String, Double> map) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(" ");
                if (parts.length < 2) continue;
                String ref = parts[0];
                double total = 0;
                // parts[1] = codeProduit, on l'ignore ici
                for (int i = 2; i + 2 < parts.length; i += 3) {
                    double duree       = Double.parseDouble(parts[i + 1]);
                    double coutHoraire = Double.parseDouble(parts[i + 2]);
                    total += duree * coutHoraire;
                }
                map.put(ref, total);
            }
        } catch (IOException e) {
            // silencieux si fichier manquant
        }
    }

    public static void afficherProduits(TextArea outputArea) {
        outputArea.appendText("Produits enregistrés :\n");
        Map<String, Double> gammeMap = chargerCoutGammes();

        // 1) Affichage des produits de base
        try (BufferedReader reader = new BufferedReader(new FileReader("produit_base.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(" ");
                if (parts.length < 2) continue;
                String codeProduit = parts[0];
                String designation = parts[1];

                // Somme des coûts de chaque gamme listée à partir de l'index 2
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

        // 2) Affichage des produits ajoutés
        try (BufferedReader reader = new BufferedReader(new FileReader("produits.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(" ");
                if (parts.length < 2) continue;
                String codeProduit = parts[0];
                String designation = parts[1];

                // paires [refGamme, coût] à partir de l'index 2
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

