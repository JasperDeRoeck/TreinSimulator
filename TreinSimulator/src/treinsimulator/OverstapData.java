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
public class OverstapData {
    
    public Lijn lijn;
    public Station station; 

    public OverstapData(Lijn lijn, Station station) {
        this.lijn = lijn;
        this.station = station;
    }
}
