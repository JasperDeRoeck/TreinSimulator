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
    private Station vertrekStation;
    private Station eindStation;
    private Lijn lijn;
    private Set<Segmentdata> data = new HashSet<>();
    private int tijd;
    
    public Segment(Lijn lijn,Station vertrek, Station eind){
        this(lijn, vertrek, eind, -1);
    }
    public Segment(Lijn lijn,Station vertrek, Station eind, int tijd){
        this.lijn=lijn;
        vertrekStation = vertrek;
        eindStation = eind;
        this.tijd = tijd;
    }

    public Station getVertrekStation() {
        return vertrekStation;
    }

    public Station getEindStation() {
        return eindStation;
    }
    
    void setTijd(int tijd){
        this.tijd=tijd;
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

    public int getTijd() {
        return tijd;
    }

    void addSegmentData(Segmentdata sd) {
        data.add(sd);
    }
    
}
