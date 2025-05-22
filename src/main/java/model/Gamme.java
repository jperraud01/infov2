/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

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
}

