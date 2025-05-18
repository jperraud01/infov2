/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author juper
 */

import model.Operation;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.*;
import java.util.List;

public class OperationController {
    

    private static void enregistrerOperation(Operation op) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("suiviMaintenance.txt", true))) {
            writer.write("Operation " + op.getDureeOperation() + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void enregistrerOperationsPourMachine(String machineRef, List<String> operations) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("operations_par_machine.txt", true))) {
        writer.write("Machine " + machineRef + " : " + String.join(", ", operations) + "\n");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}