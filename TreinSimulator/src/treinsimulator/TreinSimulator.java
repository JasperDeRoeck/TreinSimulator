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
    private static ArrayList<Reiziger> reizigersLijst;

    // DAO mijnDAO; -----------> niet nodig, want zijn statische methodes..
    // to do : readlists updaten
    public static void main(String[] args) {
        //Set up:
        System.out.println("Initialiseren...");
        try {
            DAO.initialiseer(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Zorg dat er zeker een invoerbestand opgegeven werd.");
            System.exit(0);
        }
        stationLijst = DAO.getStationLijst();
        lijnenLijst = DAO.getLijnenLijst();
        reizigersLijst = DAO.getReizigersLijst();
        int STOPTIJD = 100;
        Klok.setTijd(400);
        System.out.println("Simuleren...");
        while (Klok.getTijd() != 100) {
            
            Set<Trein> alleTreinen = new HashSet<>();
            for (Lijn lijn : lijnenLijst) {
                for (Trein trein : lijn.getTreinen().values()) {
                    alleTreinen.add(trein);             // Tijdelijk treinen opslaan in een set, om niet twee keer
                    // iedere lijn te moeten afgaan om daar alle treinen uit te halen
                    trein.aankomst(Klok.getTijd());
                }
            }
            if (reizigersLijst.size() > Klok.getTijd()) {
                if (reizigersLijst.get(Klok.getTijd()) != null) {
                    for (Reiziger reiziger : reizigersLijst) {
                        if ((reiziger.getVtijd() > 100 && reiziger.getVtijd() < 400)) {
                            
                        }
                        if (!reiziger.isGestrand()) { //gestrande reizigers niet meer overlopen
                            reiziger.activeer(Klok.getTijd());
                        }
                    }
                }
            }
            
            for (Trein trein : alleTreinen) {
                trein.vertrek(Klok.getTijd());
            }
            Klok.incrementeer();
        }
        Statistiek st = new Statistiek(lijnenLijst, reizigersLijst, stationLijst);
        System.out.println("Afgewerkt.");
    }
    
}
