/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

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

    void setData(Segmentdata sd) {
        data.add(sd);
    }
    
}
