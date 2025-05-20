/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

public class Gamme {
    private String refGamme;
    private String codeProduit;
    private float dureeTotale;
    private float coutTotal;
    private ArrayList<Operation> operations;

    public Gamme(String refGamme, String codeProduit, float dureeTotale, float coutTotal) {
        this.refGamme = refGamme;
        this.codeProduit = codeProduit;
        this.dureeTotale = dureeTotale;
        this.coutTotal = coutTotal;
        this.operations = new ArrayList<>();
    }

    public String getRefGamme() {
        return refGamme;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public float getDureeTotale() {
        return dureeTotale;
    }

    public float getCoutTotal() {
        return coutTotal;
    }

    public ArrayList<Operation> getOperations() {
        return operations;
    }

    public void ajouterOperation(Operation op) {
        operations.add(op);
        this.dureeTotale += op.getDureeOperation();
        // Si nécessaire, ajouter ici un calcul de coût basé sur l'équipement
    }

    public void supprimerOperation(Operation op) {
        if (operations.remove(op)) {
            this.dureeTotale -= op.getDureeOperation();
        }
    }


}
