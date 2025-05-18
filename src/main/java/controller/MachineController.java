/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javafx.scene.control.TextArea;
import model.Machine;



import java.io.*;
import view.MachineView;

public class MachineController {
    
 public static void enregistrerMachine(Machine machine) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("machines.txt", true))) {
        writer.write(
            machine.getRefMachine() + " " +
            machine.getDMachine() + " " +
            machine.getType() + " " +
            machine.getCoutHoraire() + " " +
            machine.getX() + " " +
            machine.getY() + " En_marche\n"
        );
    } catch (IOException ex) {
        ex.printStackTrace();
    }

}

    public static void afficherMachines(TextArea outputArea) {
        try (BufferedReader reader = new BufferedReader(new FileReader("machines.txt"))) {
            String ligne;
            outputArea.appendText("Machines enregistrées :\n");
            while ((ligne = reader.readLine()) != null) {
                if (!ligne.startsWith("Gamme")) {
                    outputArea.appendText(ligne + "\n");
                }
            }
        } catch (IOException ex) {
            outputArea.appendText("Erreur lors de la lecture du fichier : " + ex.getMessage() + "\n");
        }
    }
     public static void viderFichier() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("suiviMaintenance.txt"))) {
            writer.write("");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
   public static void supprimerMachine(String ref) {
    File fichier = new File("machines.txt");
    File temp = new File("machines_temp.txt");

    try (BufferedReader reader = new BufferedReader(new FileReader(fichier));
         BufferedWriter writer = new BufferedWriter(new FileWriter(temp))) {

        String ligne;
        while ((ligne = reader.readLine()) != null) {
            if (!ligne.startsWith(ref + " ")) {
                writer.write(ligne + "\n");
            }
        }

    } catch (IOException e) {
        e.printStackTrace();
        return;
    }

    fichier.delete();
    temp.renameTo(fichier);
}


}
