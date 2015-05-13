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
//segment/deeltraject is de weg tussen twee opeenvolgende stations
public class Segment {
    Station vertrekStation;
    Station eindStation;
    Lijn lijn;
    Set<Segmentdata> data = new HashSet<>();
    int tijd;
    
    public Segment(Lijn lijn,Station vertrek, Station eind){
        this.lijn=lijn;
        vertrekStation = vertrek;
        eindStation = eind;

    }


    
    public Segmentdata maakSegmentData(Trein t){
        //ArrayList<Trein> treinen = lijn.getTreinen();
        //for(Trein t: treinen){
            Segmentdata segdata = new Segmentdata();
            int vtijd = t.getVtijd();
            segdata.setVtijd(vtijd);
            int aantalReizigers = t.getAantalInzittenden();
            segdata.setAantalReizigersPerTrein(aantalReizigers);
            int aantalzitplaatsen = lijn.getZitplaatsen(); //tussenwaarde om aantalRechtstaande te berekenen
            int aantalRechtstaande = aantalReizigers - aantalzitplaatsen;
            segdata.setAantalRechtstaandeReizigers(aantalRechtstaande);
            int aantalAchtergebleven=0;
            segdata.setAantalAchterGeblevenReizigers(aantalAchtergebleven);
            return segdata;
        //}
    }

    void setData(Segmentdata sd) {
        data.add(sd);
    }

    public Set<Segmentdata> getData() {
        return data;
    }
    
    @Override
    public String toString(){
        return ("Segment : "+vertrekStation.getStadsnaam()+" === "+eindStation.getStadsnaam());
    }
    
}
