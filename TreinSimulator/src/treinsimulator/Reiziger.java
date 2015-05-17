/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

/**
 *
 * @author Bernard
 */
public class Reiziger {

    private int beginTijd;
    private Trein juisteTrein;
    private int vtijd;
    private Reis reis;
    private int aantalGenomenOverstappen;
    private boolean moetUitstappen;
    private int overstaptijd;
    private boolean gestrand = false;
    private Station huidigStation;
    private String naam;                //PUUR voor debuggen
    
    // het tijdstip waarop de volgende gebeurtenis met betrekking tot de reiziger plaatsvindt
    // als een reiziger strandt of aankomt bij zijn eindstation dan wordt dit ingesteld op oneindig
    public Reiziger(int aankomstSysteem, Reis reis, String naam) {
        beginTijd = aankomstSysteem;
        vtijd = aankomstSysteem;
        this.reis = reis;
        huidigStation = reis.getVertrekstation();
        juisteTrein = null;
        this.naam = naam;
    }

    /**
     *
     * @param t
     * @return true if passenger exits train
     */
    public boolean uitstappen(int t) {
        //
        if (vtijd == t) {
            moetUitstappen = false;
            huidigStation = reis.getOverstapdata(aantalGenomenOverstappen).getStation();
            aantalGenomenOverstappen++;
            if (aantalGenomenOverstappen >= reis.getAantalOverstappen()) {
                //We zijn er -> data toevoegen
                reis.addTijd(Klok.verschil(Klok.getTijd(), beginTijd));
                vtijd = -1;
                reis.incReizigers();
            } else {
                //We zijn er nog niet -> nieuwe trein zoeken
                juisteTrein = reis.getOverstapdata(aantalGenomenOverstappen).getLijn().geefEersteTrein(Klok.getTijd(), huidigStation);
                if(juisteTrein != null){
                    vtijd = juisteTrein.getLijn().geefEersteTreinUur(Klok.getTijd()+1, huidigStation);
                }
                else{
                    reis.incGestrandeReizigers();
                    gestrand = true;
                }
            }
            return true;
        }
        return false;
    }
    /**
     *
     * @param t = huidige tijd
     * @return true if vtijd has changed
     */
    public boolean activeer(int t) {
        
        if (!moetUitstappen) {
            overstaptijd++;
        }
        if (vtijd == t) {
            if (juisteTrein == null) {
                juisteTrein = reis.getOverstapdata(aantalGenomenOverstappen).getLijn().geefEersteTrein(Klok.getTijd()+1, huidigStation);
                if(juisteTrein != null){
                     vtijd = juisteTrein.getLijn().geefEersteTreinUur(Klok.getTijd()+1, huidigStation);
                }
                else{
                    reis.incGestrandeReizigers();
                    gestrand = true;
                }
            } else {
                if (!juisteTrein.opstappen(this)) {
                    juisteTrein = reis.getOverstapdata(aantalGenomenOverstappen).getLijn().geefEersteTrein(Klok.getTijd()+1, huidigStation);   //Als trein vol is, nieuwe trein zoeken, vtijd instellen op volgende vertrekuur
                    vtijd = reis.getOverstapdata(aantalGenomenOverstappen).getLijn().geefEersteTreinUur(Klok.getTijd()+1, huidigStation); 
                } else {
                    Kruising k;
                    if((k = reis.getOverstapdata(aantalGenomenOverstappen).getStation().getKruising()) != null){
                        if(reis.getAantalOverstappen()!=aantalGenomenOverstappen){
                            k.addOverstaptijd(reis.getOverstapdata(aantalGenomenOverstappen).getLijn(), reis.getOverstapdata(aantalGenomenOverstappen+1).getLijn(), overstaptijd);
                        }
                    }
                    moetUitstappen = true;                                    //Vanaf nu zit passagier op trein
                    vtijd = Klok.som(Klok.getTijd(), juisteTrein.getLijn().tijdTussenStations(huidigStation, reis.getOverstapdata(aantalGenomenOverstappen).getStation()));
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public Station getHuidigStation() {
        return huidigStation;
    }

    public int getVtijd() {
        return vtijd;
    }

    @Override
    public String toString() {
        return "Reiziger (" + naam + ") met reis " + reis;
    }
    public boolean isGestrand() {
        return gestrand;
    }

    public Trein getJuisteTrein() {
        return juisteTrein;
    }
    
    
}
