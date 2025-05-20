package controller;
import javafx.scene.control.*;
import model.Poste;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class PosteController {

    private static ArrayList<Poste> postes = new ArrayList<>();

   
    public static void enregistrerPoste(Poste poste) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("postes.txt", true))) {
        writer.write(poste.getRefPoste() + " " + poste.getDposte());
        for (String machine : poste.getMachinesTexte()) {
            writer.write(" " + machine);
        }
        writer.write("\n");
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}
public static void afficherPostes(TextArea outputArea) {
    try (BufferedReader reader = new BufferedReader(new FileReader("postes.txt"))) {
        String ligne;
        outputArea.appendText("Postes enregistrés :\n");
        while ((ligne = reader.readLine()) != null) {
            outputArea.appendText(ligne + "\n");
        }
    } catch (IOException ex) {
        outputArea.appendText("Erreur lors de la lecture des postes : " + ex.getMessage() + "\n");
    }
}


   

}
