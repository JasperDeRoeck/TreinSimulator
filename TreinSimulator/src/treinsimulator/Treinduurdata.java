/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

/**
 *
 * @author Jasper
 */
public class Treinduurdata {
    private Trein trein;
    private int aankomst;
    public Treinduurdata(Trein trein, int aankomst){
        this.trein=trein;
        this.aankomst=aankomst;
    }

    public Trein getTrein() {
        return trein;
    }

    public int getAankomst() {
        return aankomst;
    }
    
}
