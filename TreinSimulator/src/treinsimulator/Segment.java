/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author Bernard
 */
//segment/deeltraject is de weg tussen twee opeenvolgende stations
public class Segment {
    Station vertrekStation;
    Station eindStation;
    Lijn lijn;
    Segment volgendSegment;
    Segment vorigSegment;
    char richting;
    Set<Segmentdata> data = new HashSet<>();
    
    public Segment(Station vertrek, Station eind, char richting){
        vertrekStation = vertrek;
        eindStation = eind;
        this.richting = richting;
    }

    public Segment() {
        
    }
    public Segment geefVolgendeSegment(char richting){
        if(richting == 'A'){
            return volgendSegment;
        }
        else{
            return vorigSegment;
        }
    }
    
    public void maakSegmentData(){
        ArrayList<Trein> treinen = lijn.getTreinen();
        for(Trein t: treinen){
            Segmentdata segdata = new Segmentdata();
            int vtijd = t.getVtijd();
            segdata.setVtijd(vtijd);
            int aantalReizigers = t.getAantalInzittenden();
            segdata.setAantalReizigersPerTrein(aantalReizigers);
            int aantalzitplaatsen = lijn.getZitplaatsen(); //tussenwaarde om aantalRechtstaande te berekenen
            int aantalRechtstaande = aantalReizigers - aantalzitplaatsen;
            segdata.setAantalRechtstaandeReizigers(aantalRechtstaande);
            int aantalAchtergebleven;
            data.add(segdata);
        }
    }

    void setData(Segmentdata sd) {
        data.add(sd);
    }

    public Set<Segmentdata> getData() {
        return data;
    }
    
}
