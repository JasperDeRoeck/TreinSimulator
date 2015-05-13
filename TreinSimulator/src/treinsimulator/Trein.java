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
    
    boolean isRijdend = false;

    public void setData(Segmentdata sd) {

    }

    Trein(int vtijd, Lijn l) {
        this.vtijd = vtijd;
        this.lijn = l;
        tellerNietOpgestapt = 0;
    }

    void aankomst(int tijd) {
        if ((isRijdend) && (tijd == vtijd)) {
            System.out.println("Trein op lijn: " + lijn.getId() + " komt aan om " + vtijd);
            for (Reiziger reiziger : inzittenden) {
                reiziger.uitstappen(tijd);
            }
            isRijdend = false;
            vtijd += lijn.getSegmenten()[positie].getTijd();
        }
    }

    void vertrek(int tijd) {
        if ((!isRijdend) && (tijd == vtijd)) {
            if (netaangemaakt) {
                isRijdend = true;
                netaangemaakt = false;
                vtijd += lijn.getSegmenten()[positie].getTijd();
            } else if (positie == lijn.getSegmenten().length - 1) {
                vtijd = -1;
            } else {
                positie++;
                isRijdend = true;
                vtijd += lijn.getSegmenten()[positie].getTijd();
            }
            //Iets met "word", nog niet aan uit wat "word" hier plots komt doen

            Segmentdata sd = lijn.getSegmenten()[positie].maakSegmentData(this);
            sd.setAantalAchterGeblevenReizigers(tellerNietOpgestapt);
            lijn.getSegmenten()[positie].setData(sd);
        }
    }
    
    /*
     * Voegt toe als er nog plaats is, anders returnt de methode false
     */
    boolean opstappen(Reiziger r) {
        if (inzittenden.size() < lijn.getZitplaatsen()) {
            inzittenden.add(r);
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

}
//opmerking: is richting hier niet overbodig want het zit eigelijk al in de var. l ?
