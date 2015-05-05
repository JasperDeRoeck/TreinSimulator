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
            String[] lijnen = uurregelingstekst.split("\\[Lijn\\]");
            for (int i = 1; i < lijnen.length; i++) {
                Lijn l = verwerkLijnParagraaf(lijnen[i].replaceAll(" ", ""));
                lijnenLijst.add(l);
                lijnenLijst.add(new Lijn(l));
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Een .ini bestand is niet gevonden.");
        } catch (IOException io) {
            System.out.println("Probleem met het inlezen van een .ini bestand.");
        }
    }

    private static void maakStations(BufferedReader br) throws IOException {
        String huidig = br.readLine();
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
    }

    //neemt een ganse lijnparagraaf ,analyseert ze en geeft een uitgewerkt lijnobj terug

    private static Lijn verwerkLijnParagraaf(String s) {
        Lijn l = new Lijn();
        l.setRichting('A');
        l.setId(Integer.parseInt(s.substring(s.indexOf("nummer=") + 7, s.indexOf("traject="))));
        String[] stationsnamen = s.substring(s.indexOf("traject=") + 8, s.indexOf("Tijden=")).split("->");
        Station[] haltes = new Station[stationsnamen.length];
        for (int i = 0; i < stationsnamen.length; i++) {
            Segment seg = new Segment();
            for (Station station : stationLijst) {
                if (station.getStadsnaam().equals(stationsnamen[i])) {
                    haltes[i] = station;
                }
            }
        }
        Segment[] segmentarray = new Segment[stationsnamen.length - 1];
        for (int i = 0; i < segmentarray.length; i++) {
            Segment seg = new Segment();
            seg.vertrekStation = haltes[i];
            seg.eindStation = haltes[i + 1];
            segmentarray[i] = seg;
        }
        l.setSegmenten(segmentarray);
        l.setHaltes(haltes);
        String[] reisdurenInString = s.substring(s.indexOf("Tijden=") + 7, s.indexOf("CapaciteitPerTrein=")).split("\\+");
        int[] reisduren = new int[reisdurenInString.length];
        for (int i = 0; i < reisdurenInString.length; i++) {
            reisduren[i] = Integer.parseInt(reisdurenInString[i]);
        }
        l.setReisduren(reisduren);
        l.setCapaciteit(Integer.parseInt(s.substring(s.indexOf("CapaciteitPerTrein=") + 19, s.indexOf("ZitplaatsenPerTrein="))));
        l.setZitplaatsen(Integer.parseInt(s.substring(s.indexOf("ZitplaatsenPerTrein=") + 20, s.indexOf("UurvasteDienst="))));
        for (String uur : s.substring(s.indexOf("UurvasteDienst=") + 16, s.indexOf("Piekuurtreinen=")).split(",")) {
            l.getUurVertrek().add(uur.substring(uur.indexOf("+")));
        }
        for (String uur : s.substring(s.indexOf("Piekuurtreinen=") + 15).split(",")) {
            l.getUurPiekVertrek().add(uur.replace("u", ""));
        }
        return l;
    }

    public static void schrijfStations() {
        for (Station s : stationLijst) {
            System.out.println(s.getStadsnaam() + " heeft " + s.getOverstaptijd() + " min overstaptijd.\n");
        }
    }

    public static void schrijfLijnen() {
        for (Lijn l : lijnenLijst) {
            System.out.println(l.toString());
        }
    }

}
