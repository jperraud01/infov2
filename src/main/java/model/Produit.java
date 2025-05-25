/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.ArrayList;
import java.util.List;




import java.util.ArrayList;
import java.util.List;
//classe avec les attributs et getters pour produit
public class Produit {
    private String codeProduit;
    private String designation;
    private List<GammeUsage> gammes = new ArrayList<>(); //recup des gammes pour création d'un produit

    public static class GammeUsage {
        private final String refGamme;
        private final double cout;

    public GammeUsage(String refGamme, double cout) {
            this.refGamme = refGamme;
            this.cout     = cout;
        }

    public String getRefGamme() { return refGamme; }
    public double getCout()     { return cout; }
    }

    public Produit(String codeProduit, String designation) {
        this.codeProduit = codeProduit;
        this.designation = designation;
    }

    public String getCodeProduit() { return codeProduit; }
    public String getDesignation() { return designation; }

   
    public void ajouterGamme(String refGamme, double cout) {
        gammes.add(new GammeUsage(refGamme, cout));
    }

    public List<GammeUsage> getGammes() {
        return gammes;
    }
}


