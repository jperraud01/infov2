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


import java.io.*;
import java.util.List;

public class OperationController {
    

    
    public static void enregistrerOperationsPourMachine(String machineRef, List<String> operations) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("operations_par_machine.txt", true))) {
        writer.write("Machine " + machineRef + " : " + String.join(", ", operations) + "\n");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}