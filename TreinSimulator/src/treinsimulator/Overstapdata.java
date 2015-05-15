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
    private Treinduurdata treindata;
    public Overstapdata(Station overstap, Treinduurdata treindata) throws NullPointerException{
        if(treindata.getTrein()==null){
            throw new NullPointerException();
        }
        this.overstap= overstap;
        this.treindata=treindata;
    }

    public Station getOverstap() {
        return overstap;
    }

    public Treinduurdata getTreindata() {
        return treindata;
    }
}
