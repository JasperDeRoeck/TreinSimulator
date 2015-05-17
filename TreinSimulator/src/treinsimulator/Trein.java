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
//Trein houdt geen statistische gegevens bij en mogen bij aankomst verdwijnen uit het systeem
//Trein wordt geïdentificeerd door een combinatie van de lijn ,richting en zijn vertrekuur
public class Trein {

//vtijd geeft aan op welk tijdstip de volgende gebeurtenis plaatsvindt
    int vtijd;
    Lijn lijn;
    Set<Reiziger> inzittenden = new HashSet<>();
    int tellerNietOpgestapt;
    boolean weg = false;
    boolean netaangemaakt = true;
    int positie = 0;
    String id;
    
    boolean isRijdend = false;
    
    Trein(int vtijd, Lijn l, String id) {
        this.vtijd = vtijd;
        this.lijn = l;
        tellerNietOpgestapt = 0;
        this.id = id;
    }

    void aankomst(int tijd) {
        if ((isRijdend) && (tijd == vtijd)) {
            ////System.out.println(this + " komt aan in " + lijn.getHalte(positie));
            Set<Reiziger> uitgesmeten = new HashSet<>();
            for (Reiziger reiziger : inzittenden) {
                if(reiziger.uitstappen(tijd)){
                    uitgesmeten.add(reiziger);
                }
            }
            for(Reiziger reiziger:uitgesmeten){
                inzittenden.remove(reiziger);
            }
            isRijdend = false;
            //vtijd = lijn.getSegmenten()[positie].getTijd();
        }
    }

    void vertrek(int tijd) {
        if ((!isRijdend) && (tijd == vtijd)) {
            ////System.out.println(this + " zal vertrekken.");
            if (netaangemaakt) {
                isRijdend = true;
                netaangemaakt = false;
                vtijd = Klok.som(vtijd, lijn.getSegmenten()[positie].getTijd());
                positie++;
                lijn.getSegmenten()[positie-1].addSegmentData(new Segmentdata(vtijd, inzittenden.size(), inzittenden.size() - lijn.getZitplaatsen() , tellerNietOpgestapt));
                ////System.out.println(this + " heeft zijn vtijd geupdate naar " + vtijd);
            } else if (positie == lijn.getSegmenten().length) {
                lijn.getSegmenten()[positie-1].addSegmentData(new Segmentdata(vtijd, inzittenden.size(), inzittenden.size() - lijn.getZitplaatsen() , tellerNietOpgestapt));
                vtijd = -1;
                ////System.out.println("Trein " + this + " heeft zijn eindbestemming bereikt want positie=" + positie + " vs " + lijn.getSegmenten().length);
            } else {
                isRijdend = true;
                vtijd = Klok.som(vtijd, lijn.getSegmenten()[positie].getTijd());
                ////System.out.println(this + " heeft zijn vtijd geupdate naar " + vtijd);
                positie++;
                lijn.getSegmenten()[positie-1].addSegmentData(new Segmentdata(vtijd, inzittenden.size(), inzittenden.size() - lijn.getZitplaatsen() , tellerNietOpgestapt));
            }
            
        }
    }
    
    /*
     * Voegt toe als er nog plaats is, anders returnt de methode false
     */
    boolean opstappen(Reiziger r) {
        if (inzittenden.size() < lijn.getCapaciteit()) {
            inzittenden.add(r);
            ////System.out.println(r + " zit nu op trein " + this);
            return true;
        } else {
            tellerNietOpgestapt++;
            return false;
        }
    }

    Kruising getKruising() {
        return lijn.getHalte(positie).getKruising();
    }

    public Lijn getLijn() {
        return lijn;
    }

    public int getVtijd() {
        return vtijd;
    }

    public int getAantalInzittenden() {
        int aantal = 0;
        for (Reiziger r : inzittenden) {
            aantal++;
        }
        return aantal;
    }

    @Override
    public String toString() {
        return ("Trein (" + id + ") op lijn " + lijn.getId() + " ");
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
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
        final Trein other = (Trein) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
}
//opmerking: is richting hier niet overbodig want het zit eigelijk al in de var. l ?
