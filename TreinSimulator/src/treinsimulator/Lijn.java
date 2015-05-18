/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 * @author Bernard
 */
public class Lijn {

    private int id;
    private Station[] haltes;
    private Segment[] segmenten;
    char richting;
    private int capaciteit;
    private int zitplaatsen;
    private ArrayList<Integer> uurVertrek = new ArrayList<>();
    private ArrayList<Integer> uurPiekVertrek = new ArrayList<>();
    public TreeMap<Integer, Trein> treinen = new TreeMap<>();

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
        int[] reisduren = new int[k.getSegmenten().length];
        for (int i = 0; i < reisduren.length; i++) {
            reisduren[i] = k.getSegmenten()[k.getSegmenten().length - 1 - i].getTijd();
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
        int aantal = k.getHaltes().length - 1;
        Segment[] segArray = new Segment[aantal];
        for (int i = 0; i <= (k.getHaltes().length) - 2; i++) {
            Segment seg = new Segment(this, k.haltes[i], k.haltes[i + 1], reisduren[i]);
            segArray[i] = seg;
        }
        segmenten = segArray;
        maakTreinen();
    }

    public void maakTreinen() {
        for (int i : uurPiekVertrek) {
            treinen.put(i, new Trein(i, this, i + "P"));
        }
        for (int i : uurVertrek) {
            for (int j = 400; j < 2200; j += 100) {
                treinen.put(j + i, new Trein(j + i, this, (j + i) + "R" + id + richting));
            }
        }
    }

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

    @Override
    public String toString() {
        return "Lijn " + id + richting;
    }

    public TreeMap<Integer, Trein> getTreinen() {
        return treinen;
    }

    public int getId() {
        return id;
    }

    public Trein geefEersteTrein(int tijd, Station st) {
        int tijdTussenStations = tijdTussenStations(haltes[0], st);
        for (int vertrek : treinen.keySet()) {
            if (Klok.som(vertrek, tijdTussenStations) >= tijd) {
                return treinen.get(vertrek);
            }
        }
        return null;
    }

    public int geefEersteTreinUur(int tijd, Station st) {
        int tijdTussenStations = tijdTussenStations(haltes[0], st);
        for (int vertrek : treinen.keySet()) {
            if (Klok.som(vertrek, tijdTussenStations) >= tijd) {
                return Klok.som(vertrek, tijdTussenStations);
            }
        }
        return -1;
    }

    public int tijdTussenStations(Station st1, Station st2) {
        boolean moetOptellen = false;
        boolean mustLoop = true;
        int t = 0;
        int i = 0;
        if (st1.equals(st2)) {
            return 0;
        }
        while (mustLoop) {
            if (moetOptellen) {
                t += segmenten[i - 1].getTijd();
            }
            if (st1.equals(haltes[i]) || st2.equals(haltes[i])) {
                if (moetOptellen == false) {
                    moetOptellen = true;
                } else {
                    mustLoop = false;
                }
            }
            i++;
        }
        return t;
    }
}
