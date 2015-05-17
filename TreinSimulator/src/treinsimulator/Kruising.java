/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

import java.util.HashMap;

/**
 *
 * @author Bernard
 */

// twee lijnen kunnen een gemeenschappelijk station hebben zodat overstappen van de ene lijn op de andere mogelijk is,dit mag een eindstation zijn
// deze klasse houdt relevante statistische data bij 
public class Kruising {
    // naam van een kruising is van de vorm "Lijn_n1xLijn_n2"
    private String naam;
    private int overstaptijd=0;
    private int aantalReizigers = 0;
    private Station[] stations;
    // onderstaande var : hm<naamRichting(Lijn1ALijn2B),hm<overstaptijd,aantal personen met zelfde overstaptijd>>
    private HashMap<String , HashMap<Integer,Integer> >overstappen = new HashMap<>();
            
    public Kruising (String naam ,Station[] stations){
        this.naam=naam;
        this.stations = stations;
        
        for(Station st: stations){
            st.setKruising(this);
        }
    }

    public int getAantalReizigers() {
        return aantalReizigers;
    }
    public int getOverstaptijd() {
        return overstaptijd;
    }

    public Station[] getStations() {
        return stations;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setOverstaptijd(int overstaptijd) {
        this.overstaptijd = overstaptijd;
    }
    public void setStations(Station[] stations) {
        this.stations = stations;
    }
    @Override
    public String toString(){
        String zin =naam+" kruist volgende stations :";
        for (Station station : stations){
            zin =zin + "\n" +station.getStadsnaam();
        }
        return (zin);
    }

    void addOverstaptijd(Lijn l1, Lijn l2, int overstaptijd) {
        this.overstaptijd += overstaptijd;
        String n = "Lijn" + l1.getId() + "-->" + "Lijn" + l2.getId();
        if(overstappen.get(n) == null){
            overstappen.put(n, new HashMap<>());
        }
        overstappen.get(n).put(overstaptijd, 0);
        int k = overstappen.get(n).get(overstaptijd) + 1;
        overstappen.get(n).put(overstaptijd, k);
        aantalReizigers++;
    }

    public HashMap<String, HashMap<Integer, Integer>> getOverstappen() {
        return overstappen;
    }
     
}
