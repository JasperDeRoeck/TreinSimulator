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

    public Trein juisteTrein(int n, Station vertrek, Station doel) {

        int tijd = 0;
        Station overstap;
        Trein trein = new Trein();

        for (Lijn lijntje : lijnen) {
            for (int i = 0; i < lijntje.getHaltes().length; i++) {
                while (!vertrek.getStadsnaam().equals(lijntje.getHaltes()[i].getStadsnaam())) {
                    tijd += lijntje.getSegmenten()[i].tijd;
                    tijd += lijntje.getHaltes()[i].overstaptijd;
                }
                if (vertrek.getStadsnaam().equals(lijntje.getHaltes()[i].getStadsnaam())) {
                    for (int j = i + 1; j < lijntje.getHaltes().length; j++) {
                        Reis r = new Reis(lijntje.getHaltes()[j], doel);
                        if (r.bepaalAantalOverstappen() <= n - 1) {
                            overstap = lijntje.getHaltes()[j];
                            trein = lijntje.geefEersteTrein(tijd);
                        }
                    }

                } else {
                    tijd = 0;
                }
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
