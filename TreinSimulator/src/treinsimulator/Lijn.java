/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

import java.util.ArrayList;
import java.util.HashMap;

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
    public int[] reisduren;
    public HashMap<Integer, Trein> treinen = new HashMap<>();

    public Lijn(char richting, int id) {
        this.richting = richting;
        this.id = id;
    }

    public Lijn(Lijn k) {
        id = k.id;
        richting = 'B';
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
        for (Integer e : k.uurVertrek) {
            if (e == 0) {
                uurVertrek.add(e);
            } else {
                uurVertrek.add(60 - e);
            }
        }
        uurPiekVertrek = new ArrayList<>();
        for (Integer e : k.uurPiekVertrek) {
            int piek = e;
            int nieuwePiek;
            if ((1600 < piek && piek <= 1800) || (700 <= piek && piek < 900)) {
                nieuwePiek = piek - (piek % 100) - 100 + (60 - piek % 100);
                if (nieuwePiek % 100 == 60) {
                    nieuwePiek = nieuwePiek - 60;
                }
                uurPiekVertrek.add(nieuwePiek);
            }
        }
        maakSegmenten(k);
        maakTreinen();
    }

    public void maakTreinen() {
        //Aanmaken treinen:
        for (int i : uurPiekVertrek) {
            treinen.put(i, new Trein(i, this));
            //System.out.println("Nieuwe piekuurtrein aangemaakt op lijn " + haltes[0] + " - " + haltes[haltes.length-1] + " met vertrekuur: " + i);
        }
        for (int i : uurVertrek) {
            for (int j = 0; j < 2400; j += 100) {
                treinen.put(j + i, new Trein(j + i, this));
                //System.out.println("Nieuwe regelmatige trein aangemaakt op lijn " + haltes[0] + " - " + haltes[haltes.length-1] + " met vertrekuur: " + (i + j));
            }
        }
    }

    private void maakSegmenten(Lijn k) {
        int aantal = k.getHaltes().length - 1;
        Segment[] segArray = new Segment[aantal];
        for (int i = 0; i <= (k.getHaltes().length) - 2; i++) {
            Segment seg = new Segment(this, k.haltes[i], k.haltes[i + 1]);
            segArray[i] = seg;
        }
        segmenten = segArray;
    }

    /*public Kruising getKruising(){
        
     }
     */
    public Station[] getHaltes() {
        return haltes;
    }

    public Station getHalte(int i) {
        return haltes[i];
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

    public ArrayList<Integer> getUurVertrek() {
        return uurVertrek;
    }

    public void setUurVertrek(ArrayList<Integer> uurVertrek) {
        this.uurVertrek = uurVertrek;
    }

    public ArrayList<Integer> getUurPiekVertrek() {
        return uurPiekVertrek;
    }

    public void setUurPiekVertrek(ArrayList<Integer> uurPiekVertrek) {
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
        String zin = "\nLijn " + id + " rijdt over volgende trajecten :\n Volgens richting " + richting + "\n";
        int j = 0;
        for (int i = 0; i < haltes.length - 1; i++) {
            zin += "\t" + haltes[i].getStadsnaam() + "=>" + haltes[i + 1].getStadsnaam() + " voor een duur van " + reisduren[j] + " minuten.\n";
            j++;
        }
        zin += "\ncapaciteit :" + capaciteit + "\n";
        zin += "zitplaatsen: " + zitplaatsen + "\n";
        zin += "en rijdt om : \n";
        for (Integer i : uurVertrek) {
            zin = zin + " XX." + i;
        }
        zin += "\nEn heeft ook de volgende piekdiensten:\n ";
        for (Integer i : uurPiekVertrek) {
            zin += i + ",";
        }
        for (Segment s : segmenten) {
            zin += s.toString();
        }
        return zin;
    }

    public HashMap<Integer, Trein> getTreinen() {
        return treinen;
    }

    public int getId() {
        return id;
    }
    public Kruising getKruising(){ //Moet op een of andere manier door een passagier gevraagd worden aan Lijn
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Trein geefEersteTrein(int tijd) {
        Trein trein = new Trein();
        for (int vertrek : treinen.keySet()) {
            if ((Klok.incrementeer(vertrek, tijd)>= Klok.getSimulatietijd())) {
                trein= treinen.get(vertrek);
                return trein;
            } else {
                trein = null;
            }
        }
        return trein;

    }

}
