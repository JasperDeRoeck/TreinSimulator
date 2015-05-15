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

    public Overstapdata juisteTrein(int n, Station doel) {
        for (Lijn lijntje : lijnen) {
                int tijd = 0;
                for (int i = 0; i < lijntje.getHaltes().length; i++) {
                    if (!this.getStadsnaam().equals(lijntje.getHaltes()[i].getStadsnaam())) {
                        if (i < lijntje.reisduren.length) {
                            tijd += lijntje.getSegmenten()[i].tijd;
                            tijd += lijntje.getHaltes()[i].overstaptijd;
                        }
                    } else {
                        for (int j = i + 1; j < lijntje.getHaltes().length; j++) {
                            Reis r = new Reis(lijntje.getHaltes()[j], doel);
                            if ((r.bepaalAantalOverstappen() <= n - 1) || (r.bepaalAantalOverstappen()==0)) {
                                Station overstap = lijntje.getHaltes()[j];
                                Trein trein = lijntje.geefEersteTrein(tijd);
                                System.out.println("en heeft de trein gevonden: " + trein);
                                Overstapdata data = new Overstapdata(overstap, trein);
                                return data;

                            }
                        }

                    }
                }
            }
        
        return null;
    }
    @Override
    public String toString() {
        return stadsnaam;
    }

    void setKruising(Kruising kr) {
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

}
