/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Bernard
 */
// Er moeten versch. statistieken opgemaakt worden die elk een tabblad innemen. 
// Elke statistiek heeft zijn eigen specifieke eign., een eigen zoekweg naar de juiste data  
// met bijgevolg een eigen sequentiediagram en eigen code.
// Er is voor gekozen om deze eign. samen te brengen in één enkel object met een operatie voor elke statistiek omdat
// dit het meest flexibele model oplevert.

public class Statistiek {
 private HashMap<Reis, Integer> wachttijdReiziger;
    private ArrayList<Reis> alleReizen;
    private double gestrandeReizigers;
    private ArrayList<Kruising> alleKruisingen;
    private ArrayList<Segment> segmentenLijst;
    
    public Statistiek(){
        this.wachttijdReiziger = berekenWachttijdReiziger();
        this.gestrandeReizigers = bepaalAantalGestrandeReizigers();
    }
    
    public HashMap berekenWachttijdReiziger(){
        HashMap<Reis, Integer> wachttijd = new HashMap<>();
        for(Reis r: alleReizen){
            String vertrek = r.getNaamVertrekStation();
            String doel = r.getNaamDoelStation();
            int tijd = r.berekenWachttijdInReis(vertrek,doel);
            wachttijd.put(r, tijd);
        }
        return wachttijd;
    }
    
    public double bepaalAantalGestrandeReizigers(){
        int gestrandePassagiers = 0;
        //int aantalReizen = 0; Toch niet direct nodig.
        int totaalAantalPassagiers = 0;
        for(Reis r: alleReizen){
            int gestrand =r.getAantalGestrandeReizigers();
            gestrandePassagiers += gestrand;
            int totaalP = r.getAantalReizigers();
            totaalAantalPassagiers += totaalP;
            //aantalReizen++;
        }
        //aantalReizen++; //zodat het aantal reizen begint te tellen bij 1, nodig voor percentageberekening
        double percentage = gestrandePassagiers/totaalAantalPassagiers *100;        
        return percentage;
    }
    
    public int bepaalReizigersAantal(){
        int aantalReizigers = 0;
        for(Reis r: alleReizen){
            int aantal = r.getAantalReizigers();
            aantalReizigers += aantal;
        }
        return aantalReizigers;
    }
    
    public double bepaalGemiddeldeOverstaptijdPerKruising(){
        int tijd = 0;
        int aantalKruisingen = 0;
        for(Kruising k: alleKruisingen){
            int tijdKruising = k.getOverstaptijd();
            tijd += tijdKruising;
            aantalKruisingen++;
        }
        aantalKruisingen++; //want er bestaat geen 0de kruisig om het gemiddelde te berekenen
        double gemiddelde = tijd/aantalKruisingen;
        return gemiddelde;
        
    }
    
    public double bepaalTotaleOverstaptijdKruising(){ //Voor alle kruisingen samen, of voor 1 enkele kruising en dan terug in een container plaatsen?
        int tijd = 0;
        for(Kruising k: alleKruisingen){
            
        }
        return 0;
    }
    
    public double bepaalStaandeReizigersPerDeeltraject(){
        return 0;
        
    }
   
}
