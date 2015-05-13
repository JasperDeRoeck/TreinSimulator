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
    private HashMap<Reis,Double> gestrandeReizigers;
    private ArrayList<Kruising> alleKruisingen;
    private HashMap<Segment,Integer> rechtstaandeReizigers;
    
    public Statistiek(){
        this.alleKruisingen = DAO.getKruisingLijst();
        //alleReizen en segmentenLijst nog initialiseren.
        
        this.wachttijdReiziger = berekenWachttijdReiziger();
        this.gestrandeReizigers = bepaalAantalGestrandeReizigersPerReis();
        this.rechtstaandeReizigers=bepaalStaandeReizigersPerDeeltraject();
    }
    
    public HashMap<Reis,Integer> berekenWachttijdReiziger(){
        HashMap<Reis, Integer> wachttijd = new HashMap<>();
        for(Reis r: alleReizen){
//            Station vertrek = r.getVertrekstation();
//            Station doel = r.getEindstation();
            int tijd = r.getTotaleReiswegTijd(); 
            wachttijd.put(r, tijd);
        }
        return wachttijd;
    }
    
    public HashMap<Reis, Double> bepaalAantalGestrandeReizigersPerReis(){
	HashMap<Reis, Double> aantalGestrandeReizigers = new HashMap<>();
	for(Reis r: alleReizen){
		int gestrand = r.getAantalGestrandeReizigers();
		int totaalP = r.getAantalReizigers();
		double percentage = gestrand/totaalP * 100;
		aantalGestrandeReizigers.put(r,percentage);
	}
	return aantalGestrandeReizigers;
    }
    
    public HashMap<Kruising,Double> bepaalGemiddeldeOverstapTijdPerKruising(){ //Het aantal stations opvragen en dan totale tijd delen door het aantal stations
	HashMap<Kruising, Double> gemiddeldeTijd = new HashMap<>();
	
	for(Kruising k: alleKruisingen){
		int aantalStation = 0;
		int totaleTijd =0;	
		Station[] st = k.getStations();
		for(int i=0; i<st.length;i++){
			totaleTijd += st[i].getOverstaptijd();
			aantalStation++;
		}
		double gemiddelde = totaleTijd/aantalStation;
		gemiddeldeTijd.put(k,gemiddelde);
	}
	return gemiddeldeTijd;
    }
    
    public HashMap<Reis,Double> bepaalPercentageGestrandeReizigers(){
        HashMap<Reis,Double> hm = new HashMap<>();
        for (Reis r : alleReizen){
            double percentage = r.getAantalGestrandeReizigers()/r.getAantalReizigers();
            hm.put(r, percentage);
        }
        return hm;
    }
    
     //Is voor alle kruisingen in het algemeen, maar moet per kruising 
//    public double bepaalGemiddeldeOverstaptijd(){
//        int tijd = 0;
//        int aantalKruisingen = 0;
//        for(Kruising k: alleKruisingen){
//            int tijdKruising = k.getOverstaptijd();
//            tijd += tijdKruising;
//            aantalKruisingen++;
//        }
//        aantalKruisingen++; //want er bestaat geen 0de kruisig om het gemiddelde te berekenen
//        double gemiddelde = tijd/aantalKruisingen;
//        return gemiddelde;
//        
//    }
    
    
//is voor totale, maar moet per Kruising    
//    public double bepaalAantalGestrandeReizigers(){
//        int gestrandePassagiers = 0;
//        //int aantalReizen = 0; Toch niet direct nodig.
//        int totaalAantalPassagiers = 0;
//        for(Reis r: alleReizen){
//            int gestrand =r.getAantalGestrandeReizigers();
//            gestrandePassagiers += gestrand;
//            int totaalP = r.getAantalReizigers();
//            totaalAantalPassagiers += totaalP;
//            //aantalReizen++;
//        }
//        //aantalReizen++; //zodat het aantal reizen begint te tellen bij 1, nodig voor percentageberekening
//        double percentage = gestrandePassagiers/totaalAantalPassagiers *100;        
//        return percentage;
//    }
//    
    public int bepaalReizigersAantal(){
        int aantalReizigers = 0;
        for(Reis r: alleReizen){
            int aantal = r.getAantalReizigers();
            aantalReizigers += aantal;
        }
        return aantalReizigers;
    }
    public HashMap<Kruising, Integer> bepaalReizigersAantalPerKruising(){
        HashMap<Kruising, Integer> aantal = new HashMap<>();
        for(Kruising k: alleKruisingen){
            int aant; //Kruising moet het reizigersaantal krijgen, diagram 12, maar enkel Reis houdt dit bij.
            //aantal.put(k, aant);
        }
        return aantal;
    }

    
    
    public HashMap<Segment,Integer> bepaalStaandeReizigersPerDeeltraject(){
        HashMap<Segment,Integer> aantalRecht = new HashMap<>();
//        for(Segment s: segmentenLijst){
//            Set<Segmentdata> data = s.getData();
//            int aantal =0; //Weet niet zo goed hoe die segmentdata in elkaar zit, moet nog ingevuld worden.
//            aantalRecht.put(s, aantal);
//        }
        return aantalRecht;
    }
   
}