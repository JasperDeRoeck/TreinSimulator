/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

/**
 *
 * @author Bernard
 */
public class Reiziger {
    int beginTijd;
    Trein juisteTrein;
    int vtijd;
    Reis reis;
    int aantalGenomenOverstappen;
    boolean moetUitstappen;
    int overstaptijd;
    Station huidigStation;
    Station volgendStation;
    
    // het tijdstip waarop de volgende gebeurtenis met betrekking tot de reiziger plaatsvindt
    // als een reiziger strandt of aankomt bij zijn eindstation dan wordt dit ingesteld op oneindig
    
     public Reiziger(int aankomstSysteem, Reis reis){
        beginTijd = aankomstSysteem;
        vtijd = aankomstSysteem;
        this.reis = reis;
        huidigStation = reis.getVertrekstation();
        juisteTrein = zoekTrein();       
    }
    
    public void uitstappen(int t){
        moetUitstappen = false;
        if(aantalGenomenOverstappen == reis.getAantalOverstappen()){
            //We zijn er -> data toevoegen
            reis.addTijd(Klok.decrementeer(Klok.getTijd(), beginTijd));
        }
        else{
            //We zijn er nog niet -> nieuwe trein zoeken
            juisteTrein = zoekTrein();
            vtijd = juisteTrein.getVtijd();
        }
    }
    
    public Trein zoekTrein(){
        reis.bepaalAantalOverstappen();
        Overstapdata data = huidigStation.juisteTrein(reis.getAantalOverstappen(), huidigStation, reis.getEindstation());
        volgendStation = data.getOverstap();
        Trein trein = data.getTrein();
        return trein;
    }
    
    public void activeer(int t){
        if(!moetUitstappen){
            overstaptijd++;
        }
        if(vtijd == t){
            if(juisteTrein == null){
                juisteTrein = zoekTrein();
                vtijd = juisteTrein.getVtijd();
                
            }
            else{
                if(!juisteTrein.opstappen(this)){
                    //Als trein vol is, nieuwe trein zoeken, vtijd instellen op volgende vertrekuur
                    juisteTrein = zoekTrein();
                    vtijd = juisteTrein.getVtijd();
                }
                else{
                    
                    
                    juisteTrein.getKruising().addOverstaptijd(overstaptijd);  //Passagier geeft door aan kruising dat het niet meer op kruising bevindt.
                    moetUitstappen = true;                  //Vanaf nu zit passagier op trein
                    huidigStation = volgendStation;
                    volgendStation = null;
                }
            }
            
        }
    }  
}
