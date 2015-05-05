/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Bernard
 */
public class TreinSimulator {
    
    private static ArrayList<Station> stationLijst;
    private static ArrayList<Lijn> lijnenLijst;
    private static ArrayList<Reiziger>  reizigerLijst;
   
    DAO mijnDAO;
    // to do : readlists updaten
    public static void main(String[] args) {
        //Set up:
        
            DAO.readLists();
            stationLijst = DAO.getStationLijst();
            lijnenLijst = DAO.getLijnenLijst();
            reizigerLijst = DAO.getReizigerLijst();
            int STOPTIJD = 1440;
            
            int t = 0;
        //Klok:
            while(t <= STOPTIJD){
                Set<Trein> alleTreinen = new HashSet<>(); 
                for(Lijn lijn: lijnenLijst){
                    for(Trein trein : lijn.getTreinen()){
                        alleTreinen.add(trein);             // Tijdelijk treinen opslaan in een set, om niet twee keer
                                                            // iedere lijn te moeten afgaan om daar alle treinen uit te halen
                        trein.aankomst(t);
                    }
                }
                for(Reiziger reiziger: reizigerLijst){
                    reiziger.activeer(t);
                }
                for(Trein trein : alleTreinen){
                    trein.vertrek(t);
                }
                
                t++;
            }
        //Data-stuff:
            
    }
    
}
