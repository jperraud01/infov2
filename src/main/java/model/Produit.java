/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.ArrayList;
import java.util.List;
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




import java.util.ArrayList;
import java.util.List;

public class Produit {
    private String codeProduit;
    private String designation;
    private List<GammeUsage> gammes = new ArrayList<>();

    /** Représente un usage de gamme (réf de gamme + coût associé). */
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

    /** Ajoute une gamme utilisée pour ce produit, avec son coût. */
    public void ajouterGamme(String refGamme, double cout) {
        gammes.add(new GammeUsage(refGamme, cout));
    }

    /** Renvoie la liste des gammes utilisées (avec leur coût). */
    public List<GammeUsage> getGammes() {
        return gammes;
    }
}


