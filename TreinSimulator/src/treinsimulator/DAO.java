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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bernard
 */
public class DAO {

    private static ArrayList<Station> stationLijst = new ArrayList<>();
    private static ArrayList<Lijn> lijnenLijst = new ArrayList<>();

    public static void readLists() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("stationlijst.ini")));
            String huidig = br.readLine();
            while (!huidig.equals("[Stations]")) {
                huidig = br.readLine();
            }
            huidig = br.readLine();
            // alle tekst tussen [Stations] en [Uurregeling] wordt in één string 
            //gestoken en daarna verwerkt zodat stationslijst geïnit kan worden
            String stationstekst = "";
            while (!huidig.contains("[Uurregeling]")) {
                if (!huidig.contains(";") && !huidig.isEmpty()) {
                    stationstekst += huidig;
                }
                huidig = br.readLine();
            }
            stationstekst = stationstekst.replaceAll("\\\\", "");
            String[] lines = stationstekst.split("min");
            for (String s : lines) {
                s = s.trim();
                String[] lines2 = s.split(" ");
                stationLijst.add(new Station(lines2[0], Integer.parseInt(lines2[1])));
            }
            String uurregelingstekst = "";
            huidig = br.readLine();
            //vanaf hier worden de lijnen ingelezen
            while (!huidig.contains("[Reizigers]")) {
                if (!huidig.contains(";") && !huidig.isEmpty()) {
                    uurregelingstekst += huidig;
                }
                huidig = br.readLine();
            }
            uurregelingstekst = uurregelingstekst.replaceAll("\\\\", "");
            System.out.println(uurregelingstekst);
            String[] lijnen = uurregelingstekst.split("/[Lijn]/");
            for (String s : lijnen) {
                lijnenLijst.add(verwerkLijnParagraaf(s));
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Een .ini bestand is niet gevonden.");
        } catch (IOException io) {
            System.out.println("Probleem met het inlezen van een .ini bestand.");
        }
    }

    private static Lijn verwerkLijnParagraaf(String s) {
        Lijn l = new Lijn();
        String[] lines = s.split("  ");
        l.setId(Integer.parseInt(lines[0].substring(lines[0].indexOf("=") + 1)));
        String[] stationsnamen = lines[1].substring(lines[1].indexOf("=") + 1).split("->");
        Station[] stationsarray = new Station[stationsnamen.length];
        for (int i = 0; i < stationsnamen.length; i++) {
            for (Station station : stationLijst) {
                if (station.getStadsnaam().equals(stationsnamen[i])) {
                    stationsarray[i] = station;
                }
            }
        }
        l.setHaltes(stationsarray);
        String[] reisdurenInString = lines[2].substring(lines[2].indexOf("=") + 1).split("\\+");
        int[] reisduren = new int[reisdurenInString.length];
        for (int i = 0; i < reisdurenInString.length; i++) {
            System.out.println("=>" + reisdurenInString[i]);
            reisduren[i] = Integer.parseInt(reisdurenInString[i]);
        }
        l.setReisduren(reisduren);
        l.setCapaciteit(Integer.parseInt(lines[3].substring(lines[3].indexOf("=") + 1)));
        l.setZitplaatsen(Integer.parseInt(lines[4].substring(lines[4].indexOf("=") + 1)));
        for (String uur : lines[5].substring(lines[5].indexOf("=") + 1).split(",")) {
            l.getUurVertrek().add(uur);
        }
        for (String uur : lines[6].substring(lines[5].indexOf("=") + 1).split(",")) {
            l.getUurPiekVertrek().add(uur);
        }
        l.maakSegmenten(); //segmenten aanmaken
        return l;
    }

    public static void schrijfStations() {
        for (Station s : stationLijst) {
            System.out.println(s.getStadsnaam() + " heeft " + s.getOverstaptijd() + " min overstaptijd.\n");
        }
    }
    
    public static void schrijfLijnen(){
        for (Lijn l : lijnenLijst){
            System.out.println(l.toString());
        }
    }

}
