/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Bernard
 */
public class DAO {
    
    private static ArrayList<Station> stationLijst = new ArrayList<>();
    private static ArrayList<Lijn> lijnenLijst = new ArrayList<>();
    private static ArrayList<Reiziger>  reizigerLijst = new ArrayList<>();
    
    public static void readLists() {
        
    }
    public Station getTrein(int i){
        return stationLijst.get(i);
    }

    public static ArrayList<Station> getStationLijst() {
        return stationLijst;
    }

    public static ArrayList<Lijn> getLijnenLijst() {
        return lijnenLijst;
    }

    public static ArrayList<Reiziger> getReizigerLijst() {
        return reizigerLijst;
    }
    
}
