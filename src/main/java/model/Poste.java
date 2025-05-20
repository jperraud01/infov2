/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author juper
 */


import java.util.ArrayList;

public class Poste {
    private String refPoste;
    private String dposte;
    private ArrayList<Machine> listeMachines;

    public Poste(String refPoste, String dposte) {
        this.refPoste = refPoste;
        this.dposte = dposte;
        this.listeMachines = new ArrayList<>();
    }

    public String getRefPoste() {
        return refPoste;
    }

    public String getDposte() {
        return dposte;
    }

    public ArrayList<Machine> getListeMachines() {
        return listeMachines;
    }

    private ArrayList<String> machinesTexte = new ArrayList<>();

    public void ajouterMachineTexte(String ref) {
        machinesTexte.add(ref);
    }

public ArrayList<String> getMachinesTexte() {
    return machinesTexte;
}

}


