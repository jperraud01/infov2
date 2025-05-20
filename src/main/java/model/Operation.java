/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author juper
 */


public class Operation {
    private String refOperation;
    private String dOperation;
    private String refEquipement;
    private float dureeOperation;

    public Operation(String refOperation, String dOperation, String refEquipement, float dureeOperation) {
        this.refOperation = refOperation;
        this.dOperation = dOperation;
        this.refEquipement = refEquipement;
        this.dureeOperation = dureeOperation;
    }

    public String getRefOperation() {
        return refOperation;
    }

    public String getDOperation() {
        return dOperation;
    }

    public String getRefEquipement() {
        return refEquipement;
    }

    public float getDureeOperation() {
        return dureeOperation;
    }

}
