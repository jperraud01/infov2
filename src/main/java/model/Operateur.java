/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.util.List;


//attributs d'operateur, getters
public class Operateur {
    private String code;
    private String nom;
    private List<String> competences;

    public Operateur(String code, String nom, List<String> competences) {
        this.code = code;
        this.nom = nom;
        this.competences = competences;
    }

    public String getCode() { return code; }
    public String getNom() { return nom; }
    public List<String> getCompetences() { return competences; }
}


