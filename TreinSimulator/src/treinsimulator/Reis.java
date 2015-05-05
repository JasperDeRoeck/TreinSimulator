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
//combinatie van vertrek- en eindstation
//houdt statistisch belangrijke data bij 
public class Reis {
    int aantalReizigers;
    int aantalGestrandeReizigers;
    // totaal aantal minuten reisweg voor alle reizigers samen
    int totaleReiswegTijd;
    int aantalOverstappen;
    Station vertrekstation;
    Station eindstation;
    
    public int getAantalOverstappen(){
        return aantalOverstappen;
    }
    
    public int bepaalAantalOverstappen(){
        int n = -1;
        Set<Station> afgewerkt = new HashSet<>();
        afgewerkt.add(vertrekstation);
        Set<Station> nietafgewerkt = new HashSet<>();
        nietafgewerkt.addAll(vertrekstation.getBuren());
        while(!nietafgewerkt.contains(eindstation)){
            n+=1;
            Set<Station> nieuwnietafgewerkt = new HashSet<>();
            for(Station statie: nietafgewerkt){
                nieuwnietafgewerkt.addAll(statie.getBuren());
            }
            afgewerkt.addAll(nietafgewerkt);
            nietafgewerkt.clear();
            nietafgewerkt.addAll(nieuwnietafgewerkt);
        }
        return n;
    }
   
}
