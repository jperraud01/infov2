/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author juper
 */

import model.Operateur;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OperateurController {

    private static Operateur operateurActif;

    public static void enregistrerOperateur(Operateur op) {
        operateurActif = op;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("operateurs.txt", true))) {
            writer.write(op.getCode() + " " + op.getNom() + " : " + String.join(", ", op.getCompetences()) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Operateur getOperateurActif() {
        return operateurActif;
    }
}

