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
    private String[] reisduren;
    public ArrayList<Trein> treinen =  new ArrayList<>();
    public Lijn(){
        
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
        reisduren = new String[k.reisduren.length];
        for (int i = 0; i < reisduren.length; i++) {
            reisduren[i] = k.reisduren[k.reisduren.length - 1 - i];
        }
        uurVertrek = new ArrayList<>();
        for (String s : k.uurVertrek) {
            if (Integer.parseInt(s) == 0) {
                uurVertrek.add(Integer.parseInt(s)+"");
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
    public int getZitplaatsen(){
        return zitplaatsen;
    }
    public ArrayList<Trein> getTreinen() {
        return treinen;
    }
    public Kruising getKruising(){ //Moet op een of andere manier door een passagier gevraagd worden aan Lijn
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    
    
}
