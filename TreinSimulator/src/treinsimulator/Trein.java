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
    Segment huidigSegment;
    char richting;
    boolean weg = false;
    boolean netaangemaakt = true;
    
    //richting kan oftewel 'A' of 'B' zijn 
    //A ,van voor naar achter in de lijst van stations. B vice versa
    boolean isRijdend=false;
    
    void aankomst(int tijd){
        if((isRijdend)&&(tijd == vtijd)){
            for(Reiziger reiziger : inzittenden){
                reiziger.uitstappen();
            }
            isRijdend=false;
        }
    }
    void vertrek(int tijd){
        if((!isRijdend)&&(tijd == vtijd)){
            if(netaangemaakt){
                huidigSegment = lijn.geefEersteSegment(richting);
                isRijdend= true;
                netaangemaakt = false;
                vtijd += huidigSegment.tijd;
            }
            else if(huidigSegment.geefVolgendeSegment(richting) == null){
                weg = true;
                vtijd = -1;
            }
            else{
                huidigSegment = huidigSegment.geefVolgendeSegment(richting);
                isRijdend= true;
                vtijd += huidigSegment.tijd;
            }
            
            
            //Iets met "word", nog niet aan uit wat "word" hier plots komt doen
            Segmentdata sd = new Segmentdata();
            huidigSegment.setData(sd);
        }
    }

    boolean opstappen(Reiziger r) {
        if(inzittenden.size() < lijn.getZitplaatsen()){
            inzittenden.add(r);
            return true;
        }
        else{
            return false;
        }
    }

    Kruising getKruising() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public Lijn getLijn(){
        return lijn;
    }

    public int getVtijd() {
        return vtijd;
    }
    public int getAantalInzittenden(){
        int aantal = 0;
        for(Reiziger r: inzittenden){
            aantal++;
        }
        return aantal;
    }
}


//opmerking: is richting hier niet overbodig want het zit eigelijk al in de var. l ?
