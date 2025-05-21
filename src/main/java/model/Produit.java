/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.ArrayList;
/**
 *
 * @author juper
 */


/*public class Produit {
    private String codeProduit;
    private String dproduit;
    

private ArrayList<String> machinesTexte = new ArrayList<>();

public void ajouterMachineTexte(String ref) {
    machinesTexte.add(ref);
}

public ArrayList<String> getMachinesTexte() {
    return machinesTexte;
}


    public Produit(String codeProduit, String dproduit) {
        this.codeProduit = codeProduit;
        this.dproduit = dproduit;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public String getDproduit() {
        return dproduit;
    }

    
}*/
public class Produit {
    private String codeProduit;
    private String dproduit;
    private ArrayList<Machine> machines = new ArrayList<>(); // Contient des objets Machine

    // Classe interne pour représenter les machines et leurs durées
    public static class Machine {
        private String nom;
        private double duree;
        private double coutHoraire;

        public Machine(String nom, double duree, double coutHoraire) {
            this.nom = nom;
            this.duree = duree;
            this.coutHoraire = coutHoraire;
        }

        public String getNom() {
            return nom;
        }

        public double getDuree() {
            return duree;
        }

        public double getCoutHoraire() {
            return coutHoraire;
        }
    }

    public void ajouterMachine(String nom, double duree, double coutHoraire) {
        machines.add(new Machine(nom, duree, coutHoraire));
    }

    public ArrayList<Machine> getMachines() {
        return machines;
    }

    public Produit(String codeProduit, String dproduit) {
        this.codeProduit = codeProduit;
        this.dproduit = dproduit;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public String getDproduit() {
        return dproduit;
    }
}

