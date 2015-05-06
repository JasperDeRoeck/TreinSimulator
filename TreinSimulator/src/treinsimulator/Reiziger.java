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
    
    // het tijdstip waarop de volgende gebeurtenis met betrekking tot de reiziger plaatsvindt
    // als een reiziger strandt of aankomt bij zijn eindstation dan wordt dit ingesteld op oneindig
    
    Reiziger(){
        juisteTrein = zoekTrein();
    }
    public void uitstappen(){
        
    }
    public Trein zoekTrein(){
        return new Trein(); //Gewoon tijdelijk om foutmeldingen te voorkomen
    }
    
    public void activeer(int t){
        if(vtijd == t){
            if(!juisteTrein.opstappen(this)){
                juisteTrein = zoekTrein();
            }
            else{
                Kruising kruising = juisteTrein.getKruising();
                kruising.voegDataToe(); //Welke data moet toegevoegd worden?
            }
        }
    }
    
    
    
    public Reiziger(int aankomstSysteem, Station vertrekStation , Station eindStation){
        this.aankomstSysteem= aankomstSysteem;
        this.vertrekStation = vertrekStation;
        this.eindStation = eindStation;
    }
}
