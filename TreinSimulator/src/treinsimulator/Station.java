/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Bernard
 */
// treden alleen actief op bij het bepalen van de volgende trein die een reiziger wil nemen
// anders enkel nodig als verbindingspunt
public class Station {
    String stadsnaam;
    int overstaptijd;
    ArrayList<Station> buren;
    Set<Lijn>lijnen;
    
    public Station(String naam,int overstaptijd){
        this.stadsnaam = naam;
        this.overstaptijd=overstaptijd;
    }
    
    /*public Trein juisteTrein( int n){
        Set<Station>bereikbareStations = new HashSet<>();
        Set<Station>buurStations = new HashSet<>();
        
        while(n>0){
        for(Lijn lijntje: lijnen){
            for(Station statie : lijntje.getHaltes()){
                
            }
        }
        n--;
    }
    }*/

    public String getStadsnaam() {
        return stadsnaam;
    }

    public int getOverstaptijd() {
        return overstaptijd;
    }
    
    public ArrayList getBuren(){
        return buren;
    }
    
}
