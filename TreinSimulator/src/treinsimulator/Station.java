/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

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
    Set<Station> buren = new HashSet<>();
    Set<Lijn>lijnen = new HashSet<>();
    Kruising kruising;
    
    public Station(String naam,int overstaptijd){
        this.stadsnaam = naam;
        this.overstaptijd=overstaptijd;
    }

    public String getStadsnaam() {
        return stadsnaam;
    }

    public int getOverstaptijd() {
        return overstaptijd;
    }
    
    public Set getBuren(){
        return buren;
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
    @Override
    public String toString(){
        return stadsnaam;
    }

    void setKruising(Kruising kr) {
        kruising = kr;
    }
    public Kruising getKruising(){
        return kruising;
    }
    
    
}
