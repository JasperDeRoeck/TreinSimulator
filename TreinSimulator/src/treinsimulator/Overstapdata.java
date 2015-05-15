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
public class Overstapdata {
    private Station overstap;
    private Trein trein;
    public Overstapdata(Station overstap, Trein trein) throws NullPointerException{
        if(trein==null){
            throw new NullPointerException();
        }
        this.overstap= overstap;
        this.trein=trein;
    }

    public Station getOverstap() {
        return overstap;
    }

    public Trein getTrein() {
        return trein;
    }
}
