/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Bernard
 */
// treden alleen actief op bij het bepalen van de volgende trein die een reiziger wil nemen
// anders enkel nodig als verbindingspunt
public class Station {
    String stadsnaam;
    Set<Station> buren;
    int overstaptijd;
    Set<Station> buren = new HashSet<>();
    Set<Lijn>lijnen;
    
    public Station(String naam,int overstaptijd){
        this.stadsnaam = naam;
        this.overstaptijd=overstaptijd;
        buren = new HashSet<>();
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
    
    @Override
    public String toString(){
        return stadsnaam;
    }
    
}
