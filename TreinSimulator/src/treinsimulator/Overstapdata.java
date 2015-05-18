/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

/**
 *
 * @author Wolfger
 */
public class Overstapdata {
    private Lijn lijn;
    private Station station;

    public Overstapdata(Lijn lijn, Station station) {
        this.lijn = lijn;
        this.station = station;
    }
    public Station getStation() {
        return station;
    }

    public Lijn getLijn() {
        return lijn;
    }
    
}
