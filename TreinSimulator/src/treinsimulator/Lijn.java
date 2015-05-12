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
    private ArrayList<Integer> uurVertrek = new ArrayList<>();
    private ArrayList<Integer> uurPiekVertrek = new ArrayList<>();
    private int[] reisduren;
    public ArrayList<Trein> treinen =  new ArrayList<>();
    
    public Lijn(char richting, int id){
        this.richting = richting;
        this.id = id;
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
        for (int s : k.uurVertrek) {
            if (s == 0) {
                uurVertrek.add(s);
            } else {
                uurVertrek.add(60 - s);
            }
        }
        uurPiekVertrek = new ArrayList<>();
        for (int s : k.uurPiekVertrek) {
            int piek = s;
            int nieuwePiek;
            if ((1600 < piek && piek <= 1800) || (700 <= piek && piek < 900)) {
                nieuwePiek = piek - (piek % 100) - 100 + (60 - piek % 100);
                if (nieuwePiek % 100 == 60) {
                    nieuwePiek = nieuwePiek - 60;
                }
                uurPiekVertrek.add(nieuwePiek);
            }
        }
    }
    private void maakSegmenten(Lijn k){
        int aantal= k.getHaltes().length-1;
        Segment[] segArray = new Segment[aantal];
        for(int i=0; i<=(k.getHaltes().length)-2;i++){
            Segment seg = new Segment(k.haltes[i],k.haltes[i+1], 'B');
            segArray[i]=seg;
        }
        segmenten = segArray;
    }

    public Station[] getHaltes() {
        return haltes;
    }
    
    public Segment geefEersteSegment(char richting){
        if(richting == 'A'){
            return segmenten[0];
        }
        else{
            return segmenten[segmenten.length-1];
        }
    }

    @Override
    public String toString() {
        String zin = "\nLijn " + id + " rijdt over volgende trajecten :\n Volgens richting "+richting+"\n";
        int j = 0;
        for (int i = 0; i < haltes.length - 1; i++) {
            zin += "\t" + haltes[i].getStadsnaam() + "=>" + haltes[i + 1].getStadsnaam() + " voor een duur van " + reisduren[j] + " minuten.\n";
            j++;
        }
        zin += "\ncapaciteit :" + capaciteit + "\n";
        zin += "zitplaatsen: " + zitplaatsen + "\n";
        zin += "en rijdt om : \n";
        for (int i : uurVertrek) {
            zin += i + "u,";
        }
        zin += "\nEn heeft ook de volgende piekdiensten:\n ";
        for (int i : uurPiekVertrek) {
            zin += i + ",";
        }
        return zin;
    }

    public int getCapaciteit() {
        return capaciteit;
    }

    public int getZitplaatsen() {
        return zitplaatsen;
    }

    public ArrayList<Trein> getTreinen() {
        return treinen;
    }
    public ArrayList<Integer> getUurVertrek() {
        return uurVertrek;
    }
    public ArrayList<Integer> getUurPiekVertrek() {
        return uurPiekVertrek;
    }
    public void setSegmenten(Segment[] segmenten) {
        this.segmenten = segmenten;
    }

    public void setReisduren(int[] reisduren) {
        this.reisduren = reisduren;
    }

    public void setCapaciteit(int capaciteit) {
        this.capaciteit = capaciteit;
    }

    public void setHaltes(Station[] haltes) {
        this.haltes = haltes;
    }

    public void setZitplaatsen(int zitplaatsen) {
        this.zitplaatsen = zitplaatsen;
    }

    public int getId() {
        return id;
    }
    public Kruising getKruising(){ //Moet op een of andere manier door een passagier gevraagd worden aan Lijn
        throw new UnsupportedOperationException("Not yet implemented");
    }

    
    
}
