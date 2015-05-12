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
    
    
    //richting kan oftewel 'A' of 'B' zijn 
    //A ,van voor naar achter in de lijst van stations. B vice versa
    boolean isRijdend;
    
    Trein(int vtijd, Lijn l, char richting){
        this.vtijd = vtijd;
        this.lijn = l;
        this.richting = richting;
    }
    void aankomst(int tijd){
        if((!isRijdend)&&(tijd == vtijd)){
            System.out.println("Trein op lijn: " + lijn.getId() +  " komt aan om " + vtijd);
            for(Reiziger reiziger : inzittenden){
                reiziger.uitstappen(tijd);
            }
            vtijd = lijn.getVolgendeVertrekuur(tijd, this);
        }
    }
    void vertrek(int tijd){
        if((isRijdend)&&(tijd == vtijd)){
            if(huidigSegment == null){
                huidigSegment = lijn.geefEersteSegment(richting);
            }
            else{
                huidigSegment = huidigSegment.geefVolgendeSegment(richting);
            }
            
            //Iets met "word", nog niet aan uit wat "word" hier plots komt doen
            Segmentdata sd = new Segmentdata();
            huidigSegment.addData(sd);
        }
    }
    /*
     * Voegt toe als er nog plaats is, anders returnt de methode false
     */
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
}
//opmerking: is richting hier niet overbodig want het zit eigelijk al in de var. l ?
