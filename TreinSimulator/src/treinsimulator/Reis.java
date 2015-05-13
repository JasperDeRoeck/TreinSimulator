/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Bernard
 */
//combinatie van vertrek- en eindstation
//houdt statistisch belangrijke data bij 
public class Reis {
    private int aantalReizigers;
    private int aantalGestrandeReizigers;
    // totaal aantal minuten reisweg voor alle reizigers samen
    private int totaleReiswegTijd;
    private int aantalOverstappen;
    private Station vertrekstation;
    private Station eindstation;
    
    public Reis(Station vertrek,Station doel){
        vertrekstation = vertrek;
        eindstation = doel;
    }
    public void addTotaleReiswegTijd(int t){
        totaleReiswegTijd += t;
    }
    public int getAantalOverstappen(){
        return aantalOverstappen;
    }
    public void incGestrandeReizigers(){
        aantalGestrandeReizigers++;
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

    public int getAantalReizigers() {
        return aantalReizigers;
    }

    public int getAantalGestrandeReizigers() {
        return aantalGestrandeReizigers;
    }

    public int getTotaleReiswegTijd() {
        return totaleReiswegTijd;
    }

    public Station getVertrekstation() {
        return vertrekstation;
    }

    public Station getEindstation() {
        return eindstation;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.vertrekstation);
        hash = 43 * hash + Objects.hashCode(this.eindstation);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reis other = (Reis) obj;
        if (!Objects.equals(this.vertrekstation, other.vertrekstation)) {
            return false;
        }
        if (!Objects.equals(this.eindstation, other.eindstation)) {
            return false;
        }
        return true;
    }

    void addTijd(int decTijd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}