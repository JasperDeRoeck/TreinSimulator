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
    int aankomstSysteem;
    Station vertrekStation;
    Station eindStation;
    Trein juisteTrein;
    int vtijd;
    Reis reis;
    int aantalGenomenOverstappen;
    boolean moetUitstappen;
    
    // het tijdstip waarop de volgende gebeurtenis met betrekking tot de reiziger plaatsvindt
    // als een reiziger strandt of aankomt bij zijn eindstation dan wordt dit ingesteld op oneindig
    
    Reiziger(){
        juisteTrein = zoekTrein();
    }
    
    public void uitstappen(int t){
        moetUitstappen = false;
        if(aantalGenomenOverstappen == reis.aantalOverstappen){
            //We zijn er -> data toevoegen
            
        }
        else{
            //We zijn er nog niet -> nieuwe trein zoeken
            juisteTrein = zoekTrein();
            vtijd = juisteTrein.lijn.getVolgendeVertrekuur(t, juisteTrein);
        }
    }
    
    public Trein zoekTrein(){
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public void activeer(int t){
        if(vtijd == t){
            if(juisteTrein == null){
                juisteTrein = zoekTrein();
                vtijd = juisteTrein.lijn.getVolgendeVertrekuur(t, juisteTrein);
            }
            else{
                    
                if(!juisteTrein.opstappen(this)){
                    //Als trein vol is, nieuwe trein zoeken, vtijd instellen op volgende vertrekuur
                    juisteTrein = zoekTrein();
                    vtijd = juisteTrein.lijn.getVolgendeVertrekuur(t, juisteTrein);
                }
                else{
                    //Als er nog plaats is, nieuwe data toevoegen aan .. Kruising? (--> moet dat niet segment zijn?)
                    moetUitstappen = true;
                    Kruising kruising = juisteTrein.getKruising();
                    kruising.voegDataToe(); //Welke data moet toegevoegd worden?
                }
            }
            
        }
    }
    
    
    
    public Reiziger(int aankomstSysteem, Station vertrekStation , Station eindStation){
        this.aankomstSysteem= aankomstSysteem;
        this.vertrekStation = vertrekStation;
        this.eindStation = eindStation;
    }
}
