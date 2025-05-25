/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

//attributs et getters

public class Equipement {
    private String refEquipement;
    private String dEquipement;

    public Equipement(String refEquipement, String dEquipement) {
        this.refEquipement = refEquipement;
        this.dEquipement = dEquipement;
    }

    public String getRefEquipement() {
        return refEquipement;
    }

    public String getDEquipement() {
        return dEquipement;
    }

    
}

