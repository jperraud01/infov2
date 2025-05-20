/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author juper
 */


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.scene.control.TextArea;

public class GammeController {

    public static void afficherGammes(TextArea outputArea) {
        try (BufferedReader reader = new BufferedReader(new FileReader("gammes.txt"))) {
            String ligne;
            outputArea.appendText("Gammes enregistrées :\n");
            while ((ligne = reader.readLine()) != null) {
                if (ligne.startsWith("Gamme")) {
                    outputArea.appendText(ligne + "\n");
                }
            }
        } catch (IOException ex) {
        }
    }

   public static void enregistrerGamme(String... infos) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("gammes.txt", true))) {
        writer.write("Gamme " + String.join(" ", infos) + "\n");
    } catch (IOException ex) {
    }
}

}
