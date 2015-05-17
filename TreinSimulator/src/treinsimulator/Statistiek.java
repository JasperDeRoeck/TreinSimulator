/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

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
    private ArrayList<Reis> alleReizen = new ArrayList<>();
    private HashMap<Reis, Double> gestrandeReizigers;
    private ArrayList<Kruising> alleKruisingen;
    private HashMap<Segment, Integer> rechtstaandeReizigers;
    private ArrayList<Lijn> lijnenLijst;
    private ArrayList<Station> stationLijst;

    public Statistiek(ArrayList<Lijn> lijnenLijst, ArrayList<Reiziger> reizigersLijst, ArrayList<Station> stationLijst) {
        alleReizen.addAll(DAO.getAlleReizen());
        alleKruisingen = DAO.getKruisingLijst();
        this.lijnenLijst = lijnenLijst;
        //alleReizen en segmentenLijst nog initialiseren.
        this.stationLijst = stationLijst;
        this.wachttijdReiziger = berekenWachttijdReiziger();
        this.gestrandeReizigers = bepaalAantalGestrandeReizigersPerReis();
        this.rechtstaandeReizigers = bepaalStaandeReizigersPerDeeltraject();
        schrijfGegevensWeg();
    }

    public void schrijfGegevensWeg() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        schrijfWachttijdPerReiziger(workbook);
        schrijfGestrandeReizigers(workbook);
        schrijfRechtstaandeReizigers(workbook);
        try {
            FileOutputStream out = new FileOutputStream(new File("Stat.xls"));
            workbook.write(out);
            out.close();
            //System.out.println("Excel written successfully..");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void schrijfWachttijdPerReiziger(HSSFWorkbook wb) {
        HSSFSheet sheet = wb.createSheet("Wachttijd per Reis");

        Cell[][] cellen = new Cell[stationLijst.size() + 1][stationLijst.size() + 1];
        for (int i = 0; i < stationLijst.size() + 1; i++) {
            Row r = sheet.createRow(i);
            for (int j = 0; j < stationLijst.size() + 1; j++) {
                cellen[i][j] = r.createCell(j);
            }
        }
        //init alle hoofdcriteria en bijcriteria
        cellen[0][0].setCellValue("Wachttijd per Reis");
        int teller = 1;
        for (Station s : stationLijst) {
            cellen[teller][0].setCellValue(s.getStadsnaam());
            cellen[0][teller].setCellValue(s.getStadsnaam());
            teller++;
        }
        //onderstaande var nodig voor de index te vragen van een bepaald station in de stationLijst
        HashMap<Station, Integer> indexStation = new HashMap<>();
        //begint aan 1 omdat er in cel 0 0 ,de naam komt van het tabblad
        int index = 1;
        for (Station s : stationLijst) {
            indexStation.put(s, index++);
        }
        for (Map.Entry m : wachttijdReiziger.entrySet()) {
            cellen[indexStation.get(((Reis) m.getKey()).getVertrekstation())][indexStation.get(((Reis) m.getKey()).getEindstation())].setCellValue((double) ((int) m.getValue()));
        }
        //resized alle columns zodat alle inhoud duidelijk te lezen is 
        for (int i = 0; i < stationLijst.size() + 1; i++) {
            sheet.autoSizeColumn(i);
        }

    }

    private void schrijfGestrandeReizigers(HSSFWorkbook wb) {
        HSSFSheet sheet = wb.createSheet("Gestrande Reizigers");

        Cell[][] cellen = new Cell[stationLijst.size() + 1][stationLijst.size() + 1];
        for (int i = 0; i < stationLijst.size() + 1; i++) {
            Row r = sheet.createRow(i);
            for (int j = 0; j < stationLijst.size() + 1; j++) {
                cellen[i][j] = r.createCell(j);
            }
        }
        //init alle hoofdcriteria en bijcriteria
        cellen[0][0].setCellValue("Wachttijd per Reis");
        int teller = 1;
        for (Station s : stationLijst) {
            cellen[teller][0].setCellValue(s.getStadsnaam());
            cellen[0][teller].setCellValue(s.getStadsnaam());
            teller++;
        }
        //onderstaande var nodig voor de index te vragen van een bepaald station in de stationLijst
        HashMap<Station, Integer> indexStation = new HashMap<>();
        //begint aan 1 omdat er in cel 0 0 ,de naam komt van het tabblad
        int index = 1;
        for (Station s : stationLijst) {
            indexStation.put(s, index++);
        }
        for (Map.Entry m : gestrandeReizigers.entrySet()) {
            ////System.out.println("stuff");
            cellen[indexStation.get(((Reis) m.getKey()).getVertrekstation())][indexStation.get(((Reis) m.getKey()).getEindstation())].setCellValue(String.valueOf(m.getValue()));
        }
        //resized alle columns zodat alle inhoud duidelijk te lezen is 
        for (int i = 0; i < stationLijst.size() + 1; i++) {
            sheet.autoSizeColumn(i);
        }

    }

    private void schrijfRechtstaandeReizigers(HSSFWorkbook wb) {
        HSSFSheet sheet = wb.createSheet("Rechtstaande Reizigers");
        int totaalAantalSegmenten = 0;
        for (Lijn l : lijnenLijst) {
            totaalAantalSegmenten += l.getSegmenten().length;
        }
        Cell[][] cellen = new Cell[totaalAantalSegmenten + 1][50];
        for (int i = 0; i < totaalAantalSegmenten + 1; i++) {
            Row r = sheet.createRow(i);
            for (int j = 0; j < 50; j++) {
                cellen[i][j] = r.createCell(j);
            }
        }
        //init alle hoofdcriteria en bijcriteria
        cellen[0][0].setCellValue("Rechtstaande Reizigers per Deeltraject");
        for (int i=400;i<2500;i=i+100){
            cellen[0][bepaalPositieKolom(i)].setCellValue(bepaalTijd(i));
            cellen[0][bepaalPositieKolom(i+35)].setCellValue(bepaalTijd(i+30));
        }
        int rijteller = 1;
        for (Lijn l : lijnenLijst) {
            for (Segment s : l.getSegmenten()) {
                cellen[rijteller][0].setCellValue("Lijn" + l.getId() + "" + l.getRichting() + " : " + s.vertrekStation.getStadsnaam() + "-" + s.eindStation.getStadsnaam());
                for (Segmentdata sd : s.getData()) {
                    cellen[rijteller][bepaalPositieKolom(sd.getVtijd())].setCellValue(sd.getAantalRechtstaandeReizigers());
                }
                rijteller++;
            }
        }
        for (int i = 0; i < 50; i++) {
            sheet.autoSizeColumn(i);
        }

    }
    
    private String bepaalTijd(int i){
        int eerste = Klok.som(i, 0);
        int tweede = Klok.som(i, 30);
        return (eerste+"-"+tweede);
    }

    private int bepaalPositieKolom(int vtijd) {
        //System.out.print(vtijd);
        int uur = vtijd / 100;
        if (uur == 0) {
            uur = 24;
        } else if (uur == 1) {
            uur = 25;
        }

        int min = vtijd % 100;
        if (min < 30) {
            return (1 + ((2 * uur) - 8));
        } else {
            return (1 + ((2 * uur) - 8) + 1);
        }
    }

    public HashMap<Reis, Integer> berekenWachttijdReiziger() {
        HashMap<Reis, Integer> wachttijd = new HashMap<>();
        for (Reis r : alleReizen) {
            int tijd = r.getTotaleReiswegTijd();
            wachttijd.put(r, tijd);
        }
        return wachttijd;
    }

    public HashMap<Reis, Double> bepaalAantalGestrandeReizigersPerReis() {
        HashMap<Reis, Double> aantalGestrandeReizigers = new HashMap<>();
        for (Reis r : alleReizen) {
            if (r.getAantalReizigers() != 0) {
                double gestrand = r.getAantalGestrandeReizigers();
                //System.out.println("Gestrand: " + gestrand);
                double totaalP = r.getAantalReizigers();
                //System.out.println("Totaal: " + gestrand);
                double percentage = gestrand / totaalP * 100;
               aantalGestrandeReizigers.put(r, percentage);
            }
        }
        return aantalGestrandeReizigers;
    }

    public HashMap<Kruising, Double> bepaalGemiddeldeOverstapTijdPerKruising() { //Het aantal stations opvragen en dan totale tijd delen door het aantal stations
        HashMap<Kruising, Double> gemiddeldeTijd = new HashMap<>();

        for (Kruising k : alleKruisingen) {
            double gemiddelde = k.getOverstaptijd() / k.getAantalReizigers();
            gemiddeldeTijd.put(k, gemiddelde);
        }
        return gemiddeldeTijd;
    }

    public HashMap<Kruising, Integer> bepaalTotaleOverstaptijdPerKruising() {
        HashMap<Kruising, Integer> totaleTijd = new HashMap<>();
        for (Kruising k : alleKruisingen) {
            int overstaptijd = k.getOverstaptijd();
            totaleTijd.put(k, overstaptijd);
        }
        return totaleTijd;
    }

    public HashMap<Reis, Double> bepaalPercentageGestrandeReizigers() {
        HashMap<Reis, Double> hm = new HashMap<>();
        for (Reis r : alleReizen) {
            double percentage = r.getAantalGestrandeReizigers() / r.getAantalReizigers();
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
    public int bepaalReizigersAantal() {
        int aantalReizigers = 0;
        for (Reis r : alleReizen) {
            int aantal = r.getAantalReizigers();
            aantalReizigers += aantal;
        }
        return aantalReizigers;
    }

    public HashMap<Kruising, Integer> bepaalReizigersAantalPerKruising() {
        HashMap<Kruising, Integer> aantal = new HashMap<>();
        for (Kruising k : alleKruisingen) {
            int aant; //Kruising moet het reizigersaantal krijgen, diagram 12, maar enkel Reis houdt dit bij.
            //aantal.put(k, aant);
        }
        return aantal;
    }

    public HashMap<Segment, Integer> bepaalStaandeReizigersPerDeeltraject() {
        HashMap<Segment, Integer> aantalRecht = new HashMap<>();
        int aantal;
        for (Lijn l : lijnenLijst) {
            aantal = 0;
            Segment s = l.getSegmenten()[0];
            for (Segmentdata sd : s.getData()) {
                aantal += sd.getAantalAchterGeblevenReizigers();
            }
            aantalRecht.put(s, aantal);
        }
        return aantalRecht;
    }

}
