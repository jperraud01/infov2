/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import javafx.scene.control.*;
import java.io.BufferedReader;
import java.io.FileReader;
import model.Produit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class ProduitController {

    
   public static void enregistrerProduit(Produit produit) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("produits.txt", true))) {
        writer.write(produit.getCodeProduit() + " " + produit.getDproduit());
        for (String machine : produit.getMachinesTexte()) {
            writer.write(" " + machine);
        }
        writer.write("\n");
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}

    public static void afficherProduits(TextArea outputArea) {
        outputArea.appendText("Produits enregistrés :\n");

       
        try (BufferedReader reader = new BufferedReader(new FileReader("produit_base.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                outputArea.appendText(ligne + "\n");
            }
        } catch (IOException ex) {
            outputArea.appendText("Erreur lors de la lecture de produit_base.txt : " + ex.getMessage() + "\n");
        }

     
        try (BufferedReader reader = new BufferedReader(new FileReader("produits.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                outputArea.appendText(ligne + "\n");
            }
        } catch (IOException ex) {
            outputArea.appendText("Erreur lors de la lecture des produits : " + ex.getMessage() + "\n");
        }
    }

  
}

   
