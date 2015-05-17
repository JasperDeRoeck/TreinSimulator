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
// treden alleen actief op bij het bepalen van de volgende trein die een reiziger wil nemen
// anders enkel nodig als verbindingspunt
public class Station {

    String stadsnaam;
    int overstaptijd;
    Set<Station> buren = new HashSet<>();
    Set<Lijn> lijnen = new HashSet<>();
    Kruising kruising;

    public Station(String naam, int overstaptijd) {
        this.stadsnaam = naam;
        this.overstaptijd = overstaptijd;
    }

    public String getStadsnaam() {
        return stadsnaam;
    }

    public int getOverstaptijd() {
        return overstaptijd;
    }

    public Set getBuren() {
        return buren;
    }
    
    @Override
    public String toString() {
        return stadsnaam;
    }

    void setKruising(Kruising kr
    ) {
        kruising = kr;
    }

    public Kruising getKruising() {
        return kruising;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.stadsnaam);
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
        final Station other = (Station) obj;
        if (!Objects.equals(this.stadsnaam, other.stadsnaam)) {
            return false;
        }
        return true;
    }

    public boolean bepaalLijnSequentie(int n, Station doel, Stack<OverstapData> sequentie) {
        //System.out.println("zoek van " + this + " naar " + doel);
        ////System.out.println("De n is : " + n);
        if(n == 0){
            for(Lijn lijn : lijnen){
                boolean isOk = false;
                for (int i = 0; i < lijn.getHaltes().length; i++) {
                    if(lijn.getHaltes()[i].equals(this)){
                        isOk = true;
                    }
                    if(lijn.getHaltes()[i].equals(doel) && isOk){
                         sequentie.push(new OverstapData(lijn, lijn.getHaltes()[i]));
                         return true;
                    }
                }
            }
            return false;
        }
        for(Lijn lijn : lijnen){
            boolean isOk = false;
            for (int i = 0; i < lijn.getHaltes().length; i++) {
                if(lijn.getHaltes()[i].equals(this)){
                    isOk = true;
                }
                if(isOk && lijn.getHaltes()[i].getKruising() != null){
                    if(lijn.getHaltes()[i].bepaalLijnSequentie(n-1, doel, sequentie)){
                        sequentie.push(new OverstapData(lijn, lijn.getHaltes()[i]));
                        return true;
                    }
                }
            }
        }
        //System.out.println("NO route...");
        return false;
    }

}
