/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

/**
 *
 * @author Bernard
 */
//segment/deeltraject is de weg tussen twee opeenvolgende stations
public class Segment {
    Station vertrekStation;
    Station eindStation;
    char richting;
    
    public Segment(){
        
    }
    
    public Segment(Station vertrek, Station eind, char richting){
        vertrekStation = vertrek;
        eindStation = eind;
        this.richting = richting;
    }
}
