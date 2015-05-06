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
    Segment volgendSegment;
    Segment vorigSegment;
    Set<Segmentdata> data = new HashSet<>();
    
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
