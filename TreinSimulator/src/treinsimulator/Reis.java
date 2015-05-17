/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Bernard
 */
//combinatie van vertrek- en eindstation
//houdt statistisch belangrijke data bij 
public class Reis {
    private int aantalReizigers;
    private int aantalGestrandeReizigers = 0;
    // totaal aantal minuten reisweg voor alle reizigers samen
    private int totaleReiswegTijd;
    private int aantalOverstappen = -1;     //Op -1 instellen om ongeïnitialiseerd voor te stellen
    private Station vertrekstation;
    private Station eindstation;
    private OverstapData[] lijnsequentie;
    
    public Reis(Station vertrek,Station doel){
        vertrekstation = vertrek;
        eindstation = doel;
    }
    public void addTotaleReiswegTijd(int t){
        totaleReiswegTijd += t;
    }
    public int getAantalOverstappen(){
        if(aantalOverstappen == -1){
           bepaalLijnSequentie();
        }
        return aantalOverstappen;
    }
    public void incGestrandeReizigers(){
        
        aantalGestrandeReizigers++;
        aantalReizigers++;
        //System.out.println(this + " heeft nu " + aantalGestrandeReizigers + " gestrande reizigers.");
    }
    public void incReizigers(){
        aantalReizigers++;
    }
    public void bepaalLijnSequentie(){
        int n = -1;
        Set<Station> afgewerkt = new HashSet<>();
        afgewerkt.add(vertrekstation);
        Set<Station> nietafgewerkt = new HashSet<>();
        nietafgewerkt.addAll(vertrekstation.getBuren());
        while(!nietafgewerkt.contains(eindstation)){
            n++;
            Set<Station> nieuwnietafgewerkt = new HashSet<>();
            for(Station statie: nietafgewerkt){
                nieuwnietafgewerkt.addAll(statie.getBuren());
            }
            afgewerkt.addAll(nietafgewerkt);
            nietafgewerkt.clear();
            nietafgewerkt.addAll(nieuwnietafgewerkt);
        }
        aantalOverstappen = n+1;
        Stack<OverstapData> ls = new Stack<OverstapData>();
        if(vertrekstation.bepaalLijnSequentie(aantalOverstappen, eindstation, ls)){
            //ls.pop();    //Laatst gepushte lijn zal een nullpointer zijn.
            lijnsequentie = new OverstapData[ls.size()];
           // //System.out.println("Route from " + vertrekstation + " to " + eindstation + " is");
            for (int i = 0; i < lijnsequentie.length; i++) {
                lijnsequentie[i] = ls.pop();
                //System.out.println(lijnsequentie[i].lijn + " en overstappen in " + lijnsequentie[i].station);
            }
        }
        else{
            //System.out.println("No route to destination: " + vertrekstation + eindstation);
        }
    }
    public OverstapData getOverstapData(int i){
        if(lijnsequentie == null){
            bepaalLijnSequentie();
        }
        return lijnsequentie[i];
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

    @Override
    public String toString() {
        return  vertrekstation + " - " + eindstation;
    }
    void addTijd(int getal) {
        totaleReiswegTijd += getal;
    }
    
}
