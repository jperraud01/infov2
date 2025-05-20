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


public class Produit {
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

    
}
