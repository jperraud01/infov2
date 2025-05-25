/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

//attributs et getters pour machine
public class Machine {
    private String refMachine;
    private String dmachine;
    private String type;
    private float coutHoraire;
    private int x;
    private int y;

    public Machine(String refMachine, String dmachine, String type, float coutHoraire, int x, int y) {
        this.refMachine = refMachine;
        this.dmachine = dmachine;
        this.type = type;
        this.coutHoraire = coutHoraire;
        this.x = x;
        this.y = y;
    }

    public String getRefMachine() {
        return refMachine;
    }

    public String getDMachine() {
        return dmachine;
    }

    public String getType() {
        return type;
    }

    public float getCoutHoraire() {
        return coutHoraire;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float coutUtilisation(float duree) {
        return coutHoraire * duree;
    }
   
}
