/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

import java.util.ArrayList;

/**
 *
 * @author Bernard
 */
public class Lijn {

    private int id;
    private Station[] haltes;
    private Segment[] segmenten;
    //richting kan oftewel 'A' of 'B' zijn 
    //A ,van voor naar achter in de lijst van haltes. B vice versa
    char richting;

    private int capaciteit;
    private int zitplaatsen;
    private ArrayList<String> uurVertrek = new ArrayList<>();
    private ArrayList<String> uurPiekVertrek = new ArrayList<>();
    private int[] reisduren;

    public Lijn() {

    }

    public Lijn(Lijn k) {
        id = k.id;
        id = 'B';
        capaciteit = k.capaciteit;
        zitplaatsen = k.zitplaatsen;
        haltes = new Station[k.getHaltes().length];
        for (int i = 0; i < haltes.length; i++) {
            haltes[i] = k.haltes[k.haltes.length - 1 - i];
        }
        reisduren = new int[k.reisduren.length];
        for (int i = 0; i < reisduren.length; i++) {
            reisduren[i] = k.reisduren[k.reisduren.length - 1 - i];
        }
        uurVertrek = new ArrayList<>();
        for (String s : k.uurVertrek) {
            if (Integer.parseInt(s) == 0) {
                uurVertrek.add(Integer.parseInt(s) + "");
            } else {
                uurVertrek.add(60 - Integer.parseInt(s) + "");
            }
        }
        uurPiekVertrek = new ArrayList<>();
        for (String s : k.uurPiekVertrek) {
            int piek = Integer.parseInt(s);
            int nieuwePiek;
            if ((1600 < piek && piek <= 1800) || (700 <= piek && piek < 900)) {
                nieuwePiek = piek - (piek % 100) - 100 + (60 - piek % 100);
                if (nieuwePiek % 100 == 60) {
                    nieuwePiek = nieuwePiek - 60;
                }
                uurPiekVertrek.add(nieuwePiek + "");
            }
        }
        k.maakSegmenten(); //segmenten in B-richting aanmaken
    }
    
    public Segment maakSegment(Station s1, Station s2, char richting){
        Segment seg = new Segment(s1, s2, richting);
        return seg;
    }
    
    public void maakSegmenten(){
        int aantal= this.getHaltes().length-1;
        Segment[] segArray = new Segment[aantal];
        for(int i=0; i<=(this.getHaltes().length)-2;i++){
            Segment seg = new Segment(this.haltes[i],this.haltes[i+1], this.richting);
            segArray[i]=seg;
        }
        this.setSegmenten(segArray);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Station[] getHaltes() {
        return haltes;
    }

    public void setHaltes(Station[] haltes) {
        this.haltes = haltes;
    }

    public Segment[] getSegmenten() {
        return segmenten;
    }

    public void setSegmenten(Segment[] segmenten) {
        this.segmenten = segmenten;
    }

    public char getRichting() {
        return richting;
    }

    public void setRichting(char richting) {
        this.richting = richting;
    }

    public int getCapaciteit() {
        return capaciteit;
    }

    public void setCapaciteit(int capaciteit) {
        this.capaciteit = capaciteit;
    }

    public int getZitplaatsen() {
        return zitplaatsen;
    }

    public void setZitplaatsen(int zitplaatsen) {
        this.zitplaatsen = zitplaatsen;
    }

    public ArrayList<String> getUurVertrek() {
        return uurVertrek;
    }

    public void setUurVertrek(ArrayList<String> uurVertrek) {
        this.uurVertrek = uurVertrek;
    }

    public ArrayList<String> getUurPiekVertrek() {
        return uurPiekVertrek;
    }

    public void setUurPiekVertrek(ArrayList<String> uurPiekVertrek) {
        this.uurPiekVertrek = uurPiekVertrek;
    }

    public int[] getReisduren() {
        return reisduren;
    }

    public void setReisduren(int[] reisduren) {
        this.reisduren = reisduren;
    }

    @Override
    public String toString() {
        String zin = "\nLijn " + id + " rijdt over volgende trajecten:\n ";
        int j = 0;
        for (int i = 0; i < haltes.length - 1; i++) {
            zin += "\t" + haltes[i].getStadsnaam() + "=>" + haltes[i + 1].getStadsnaam() + " voor een duur van " + reisduren[j] + " minuten.\n";
            j++;
        }
        zin += "\ncapaciteit :" + capaciteit + "\n";
        zin += "zitplaatsen: " + zitplaatsen + "\n";
        zin += "en rijdt om : \n";
        for (String i : uurVertrek) {
            zin += i + "u,";
        }
        zin += "\nEn heeft ook de volgende piekdiensten:\n ";
        for (String i : uurPiekVertrek) {
            zin += i + ",";
        }
        return zin;
    }
}
