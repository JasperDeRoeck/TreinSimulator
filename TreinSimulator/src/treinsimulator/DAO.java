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
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author Bernard
 */
public class DAO {
    
    private static ArrayList<Station> stationLijst = new ArrayList<>();
    private static ArrayList<Lijn> lijnenLijst = new ArrayList<>();
    private static HashMap<Integer,ArrayList<Reiziger>> reizigersLijst = new HashMap<>();
    private static ArrayList<Kruising> kruisingLijst = new ArrayList<>();

    //enige functie die nodig is om DAO te initialiseren en zijn gegevens te kunne gebruiken
    public static void initialiseer(){
        leesIni();
        maakDeductieStructuren();
    }
    
    public static void readLists() {
        
    }
    public Station getTrein(int i){
        return stationLijst.get(i);
    }
    
    //leest .ini bestand in met stationsinfo, lijninfo en passagiersinfo
    private static void leesIni() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("input.ini")));
            String huidig = br.readLine();
            while (!huidig.equals("[Stations]")) {
                huidig = br.readLine();
            }
            br = maakStations(br);
            br = maakLijnen(br);
            maakReizigers(br);
        } catch (FileNotFoundException fnfe) {
            System.out.println("Een .ini bestand is niet gevonden.");
        } catch (IOException io) {
            System.out.println("Probleem met het inlezen van een .ini bestand.");
        }
    }
    //gaat adhv ingelezen gegevens de buren van de stations , kruisingen ... deduceren 
    private static void maakDeductieStructuren(){
        geefStationsBuren();
        maakKruisingen();
    }
    //initialiseert de stations in stationLijst,wordt opgeroepen door leesIni()
    private static BufferedReader maakStations(BufferedReader br) throws IOException {
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
        return br;
    }

    public static ArrayList<Station> getStationLijst() {
        return stationLijst;
    }
    //initialiseert de lijnen in lijnenLijst,wordt opgeroepen door leesIni()
    private static BufferedReader maakLijnen(BufferedReader br) throws IOException {
        String uurregelingstekst = "";
        String huidig = br.readLine();
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
        return br;
    }
    //initialiseert de reizigers in reizigersLijst,wordt opgeroepen door leesIni()
    private static void maakReizigers(BufferedReader br) throws IOException{
        String huidig = br.readLine();
        String passagiersLijst= "";
        while (huidig!=null){
            if (!huidig.isEmpty() && !huidig.contains(";") && !huidig.contains("\\")){
                passagiersLijst= passagiersLijst + huidig+ " ";
            } else if (!huidig.isEmpty() && !huidig.contains(";")){
                passagiersLijst= passagiersLijst + huidig;
            }
            huidig =br.readLine();
        }
        passagiersLijst = passagiersLijst.replaceAll("\\\\", "");
        String [] lines = passagiersLijst.split(" ");
        
        for (int i =0;i<lines.length;i=i+4){
            Station beginstation=null ;
            Station doelstation=null;
            int tijd = Integer.parseInt(lines[i].replace("u", ""));
            for (Station s : stationLijst){
                if (s.getStadsnaam().equals(lines[i+2])){
                    beginstation =s;
                } else if (s.getStadsnaam().equals(lines[i+3])){
                    doelstation =s;
                }
            }
            
            for (int j =0;j< Integer.parseInt(lines[i+1]);j++){
                if (reizigersLijst.containsKey(tijd)){
                    reizigersLijst.get(tijd).add(new Reiziger(tijd,beginstation,doelstation));
                } else{
                    ArrayList<Reiziger> lijst = new ArrayList<>();
                    lijst.add(new Reiziger(tijd,beginstation,doelstation));
                    reizigersLijst.put(tijd,lijst);
                }
            }
        }
        
    }
    //neemt een ganse lijnparagraaf ,analyseert ze en geeft een uitgewerkt lijnobj terug
    private static Lijn verwerkLijnParagraaf(String s) {
                 /* Kleine aanpassing aangebracht: om het aantal getters/setters toch een beetje te reduceren,
                  * heb ik de constructor van Lijn aangepast om richting en id al aan te nemen. Deze worden dan
                  * onmiddellijk meegegeven.
                  */
        int id = Integer.parseInt(s.substring(s.indexOf("nummer=") + 7, s.indexOf("traject=")));
        Lijn l = new Lijn('A', id);
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
        l.setZitplaatsen(Integer.parseInt(s.substring(s.indexOf("ZitplaatsenPerTrein=") + 20, s.indexOf("Uurvaste"))));
        for (String uur : s.substring(s.indexOf("Uurvaste") + 16, s.indexOf("Piekuurtreinen=")).split(",")) {
            l.getUurVertrek().add(Integer.parseInt(uur.substring(uur.indexOf("+"))));
        }
        for (String uur : s.substring(s.indexOf("Piekuurtreinen=") + 15).split(",")) {
            l.getUurPiekVertrek().add(Integer.parseInt(uur.replace("u", "")));
        }
        return l;
    }

    public static ArrayList<Lijn> getLijnenLijst() {
        return lijnenLijst;
    }
    //initialiseert de buren van elk station in de stationslijst,wordt opgeroepen door maakDeductieStructuren()
    private static void geefStationsBuren(){
        for (Lijn l : lijnenLijst){
            for (int i =0;i<l.getHaltes().length-1;i++){
                l.getHaltes()[i].getBuren().add(l.getHaltes()[i+1]);
                l.getHaltes()[i+1].getBuren().add(l.getHaltes()[i]);
            }
        }
    }
    //initialiseert de kruisingen in kruisingenLijst,wordt opgeroepen door maakDeductieStructuren()
    private static void maakKruisingen(){
        for (Lijn l : lijnenLijst){
            for (Lijn m: lijnenLijst){
                if (l.getId()!= m.getId() && !checkAanwezigheidKruising(l.getId(),m.getId())){
                    Stack<Station> st = new Stack<>();
                    for (int i =0;i<l.getHaltes().length;i++){
                        for (int j=0;j<m.getHaltes().length;j++){
                            if (l.getHaltes()[i].stadsnaam.equals(m.getHaltes()[j].getStadsnaam())){
                                st.push(m.getHaltes()[j]);
                            }
                        }
                    }
                    if (st.size()>0){
                        Kruising k = new Kruising("Lijn"+l.getId()+"xLijn"+m.getId(),st.toArray(new Station[st.size()]));
                        kruisingLijst.add(k);
                    }
                }
            }
        }
    }
    //hulpfunctie voor maakKruisingen ,zorgt ervoor dat er geen duplicaatkruisingen gecreeerd kunnen worden cnf. Lijn1xLijn2<=>Lijn2xLijn1
    private static boolean checkAanwezigheidKruising(int naamLijn1,int naamLijn2){
        for (Kruising k : kruisingLijst){
            String [] lines = k.getNaam().replaceAll("Lijn", "").split("x");
            int eersteLijnID = Integer.parseInt(lines[0]);
            int laatsteLijnID = Integer.parseInt(lines[1]);
            if ((eersteLijnID==naamLijn1 || eersteLijnID==naamLijn2) && (laatsteLijnID==naamLijn1 || laatsteLijnID==naamLijn2)){
                return true;
            }
        }
        return false;
    }
    
    public static void schrijfStations() {
        for (Station s : stationLijst) {
            System.out.println(s.getStadsnaam() + " heeft " + s.getOverstaptijd() + " min overstaptijd.");
            System.out.println("Heeft de volgende buren: ");
            s.getBuren().forEach(System.out::println);
            System.out.println("");
        }
    }
    public static void schrijfLijnen() {
        for (Lijn l : lijnenLijst) {
            System.out.println(l.toString());
        }
    }
    
    public static void schrijfPassagiers(){
        for (Map.Entry<Integer,ArrayList<Reiziger>> s: reizigersLijst.entrySet()){
            System.out.println("Om "+s.getKey()+" vertrekken er "+s.getValue().size()+ " reizigers.\n");
        }
    }
    
    public static void schrijfKruisingen(){
        for (Kruising k : kruisingLijst){
            System.out.println(k.toString());
        }
    }
    public static HashMap<Integer,ArrayList<Reiziger>> getReizigersLijst() {
        return reizigersLijst;
    }
    
}
