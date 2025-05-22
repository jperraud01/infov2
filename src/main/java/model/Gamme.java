/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*package model;

import java.util.ArrayList;
import java.util.List;

public class Gamme {
    private String refGamme;
    private String codeProduit;
    private List<MachineUsage> usages = new ArrayList<>();

    public static class MachineUsage {
        private String machineCode;
        private double duree;
        private double coutHoraire;

        public MachineUsage(String machineCode, double duree, double coutHoraire) {
            this.machineCode = machineCode;
            this.duree        = duree;
            this.coutHoraire  = coutHoraire;
        }
        public String getMachineCode() { return machineCode; }
        public double getDuree()       { return duree; }
        public double getCoutHoraire() { return coutHoraire; }
    }

    public Gamme(String refGamme, String codeProduit) {
        this.refGamme    = refGamme;
        this.codeProduit = codeProduit;
    }

    public String getRefGamme()    { return refGamme; }
    public String getCodeProduit() { return codeProduit; }
    public List<MachineUsage> getUsages() { return usages; }

    public void ajouterMachine(String code, double duree, double coutH) {
        usages.add(new MachineUsage(code, duree, coutH));
    }
}*/
package model;

import java.util.ArrayList;
import java.util.List;

public class Gamme {
    private String refGamme;
    private String codeProduit;
    private List<MachineUsage> usages = new ArrayList<>(); // Liste des machines et de leur usage dans la gamme
    private double coutTotal = 0; // Le coût total de la gamme, calculé à partir des machines et de leurs durées

    public static class MachineUsage {
        private String machineCode;
        private double duree;
        private double coutHoraire;

        public MachineUsage(String machineCode, double duree, double coutHoraire) {
            this.machineCode = machineCode;
            this.duree = duree;
            this.coutHoraire = coutHoraire;
        }

        public String getMachineCode() {
            return machineCode;
        }

        public double getDuree() {
            return duree;
        }

        public double getCoutHoraire() {
            return coutHoraire;
        }
    }

    public Gamme(String refGamme, String codeProduit) {
        this.refGamme = refGamme;
        this.codeProduit = codeProduit;
    }

    public String getRefGamme() {
        return refGamme;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public List<MachineUsage> getUsages() {
        return usages;
    }

    public double getCoutTotal() {
        return coutTotal;
    }

    /**
     * Méthode pour ajouter une machine à la gamme, avec son coût et sa durée.
     * Elle met également à jour le coût total de la gamme.
     *
     * @param code      : Code de la machine.
     * @param duree     : Durée d'utilisation de la machine dans la gamme.
     * @param coutHoraire : Coût horaire de la machine.
     */
    public void ajouterMachine(String code, double duree, double coutHoraire) {
        MachineUsage usage = new MachineUsage(code, duree, coutHoraire);
        usages.add(usage);
        // Calcul du coût total de la gamme
        this.coutTotal += duree * coutHoraire; 
    }

    /**
     * Méthode pour ajouter une gamme avec le code produit et le coût total.
     *
     * @param refGamme : La référence de la gamme.
     * @param codeProduit : Le code du produit associé à la gamme.
     */
    public void ajouterGamme(String refGamme, String codeProduit) {
        this.refGamme = refGamme;
        this.codeProduit = codeProduit;
    }
}


