/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Bernard
 */
public class TreinSimulator {
    
    private static ArrayList<Station> stationLijst;
    private static ArrayList<Lijn> lijnenLijst;
    private static HashMap<Integer,ArrayList<Reiziger>> reizigersLijst;
   
    // DAO mijnDAO; -----------> niet nodig, want zijn statische methodes..
    // to do : readlists updaten
    public static void main(String[] args) {
        //Set up:
            DAO.initialiseer();
                        
            stationLijst = DAO.getStationLijst();
            lijnenLijst = DAO.getLijnenLijst();
            reizigersLijst = DAO.getReizigersLijst();
            int STOPTIJD = 1440;
            
            Klok k = new Klok(400);
        //Klok:
            while(k.getTijd() <= STOPTIJD){
                Set<Trein> alleTreinen = new HashSet<>();
                for(Lijn lijn: lijnenLijst){
                    for(Trein trein : lijn.getTreinen().values()){
                        alleTreinen.add(trein);             // Tijdelijk treinen opslaan in een set, om niet twee keer
                                                            // iedere lijn te moeten afgaan om daar alle treinen uit te halen
                        trein.aankomst(k.getTijd());
                        System.out.println(k.getTijd());
                    }
                }
                if(reizigersLijst.get(k.getTijd()) != null){
                    for(Reiziger reiziger: reizigersLijst.get(k.getTijd())){
                             reiziger.activeer(k.getTijd());
                    }
                }
                for(Trein trein : alleTreinen){
                    trein.vertrek(k.getTijd());
                }
                k.incrementeer(k.getTijd(),1);
            }
        //Data-stuff:
            Statistiek st = new Statistiek(lijnenLijst);
    }
    
}
