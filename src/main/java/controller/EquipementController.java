/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author juper
 */


import model.Equipement;



import java.io.*;

public class EquipementController {
    
    public static void enregistrerEquipement(Equipement eq) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("suiviMaintenance.txt", true))) {
            writer.write("Equipement " + eq.getRefEquipement() + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}