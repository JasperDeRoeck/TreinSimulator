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

    int beginTijd;
    Trein juisteTrein;
    int vtijd;
    Reis reis;
    int aantalGenomenOverstappen;
    boolean moetUitstappen;
    int overstaptijd;
    boolean gestrand = false;
    Station huidigStation;
    String naam;                //PUUR voor debuggen
    
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
        //System.out.println(this + "heeft kans gehad");
        if (vtijd == t) {
            moetUitstappen = false;
            huidigStation = reis.getOverstapData(aantalGenomenOverstappen).station;
            aantalGenomenOverstappen++;
            if (aantalGenomenOverstappen >= reis.getAantalOverstappen()) {
                //We zijn er -> data toevoegen
                reis.addTijd(Klok.verschil(Klok.getTijd(), beginTijd));
                vtijd = -1;
                reis.incReizigers();
                if(voorwaarde()){
                    System.out.println(this + " stapt uit en heeft zijn eindhalte bereikt.");
                }
            } else {
                //System.out.println(this + " stapt uit en gaat op zoek naar een andere trein, want " + aantalGenomenOverstappen + " is nog niet gelijk aan " + reis.getAantalOverstappen());
                //We zijn er nog niet -> nieuwe trein zoeken
                ////System.out.println(this + " zoekt een trein");
                juisteTrein = reis.getOverstapData(aantalGenomenOverstappen).lijn.geefEersteTrein(Klok.getTijd(), huidigStation, voorwaarde());
                if(juisteTrein != null){
                    vtijd = juisteTrein.getLijn().geefEersteTreinUur(Klok.getTijd()+1, huidigStation, voorwaarde());
                     
                    if(voorwaarde()){
                         System.out.println(this + " heeft vtijd geupdate naar " + vtijd + " want hij/zij moet instappen in " + huidigStation + "met trein(" + juisteTrein.id+")");
                    }
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
                juisteTrein = reis.getOverstapData(aantalGenomenOverstappen).lijn.geefEersteTrein(Klok.getTijd()+1, huidigStation, voorwaarde());
                if(juisteTrein != null){
                     vtijd = juisteTrein.getLijn().geefEersteTreinUur(Klok.getTijd()+1, huidigStation, voorwaarde());
                        if(voorwaarde()){
                           System.out.println(this + " heeft vtijd geupdate naar " + vtijd + " want dan komt " + juisteTrein + " toe in " + huidigStation);
                        }
                }
                else{
                    //System.out.println("Gestrand!!!!!!!");
                    reis.incGestrandeReizigers();
                    gestrand = true;
                }
            } else {
                
                if(voorwaarde()){
                    System.out.println(this + " wil opstappen op de trein ");
                }
                if (!juisteTrein.opstappen(this)) {
                    //Als trein vol is, nieuwe trein zoeken, vtijd instellen op volgende vertrekuur
                    juisteTrein = reis.getOverstapData(aantalGenomenOverstappen).lijn.geefEersteTrein(Klok.getTijd()+1, huidigStation, voorwaarde());//data.getTrein();
                    vtijd = reis.getOverstapData(aantalGenomenOverstappen).lijn.geefEersteTreinUur(Klok.getTijd()+1, huidigStation, voorwaarde()); 
                    
                    if(voorwaarde()){
                        System.out.println("maar er was niet genoeg plaats");
                        System.out.println(this + " heeft vtijd geupdate naar " + vtijd + " want dan komt " + juisteTrein + " toe in " + huidigStation);
                    }
                    
                } else {
                    //System.out.println("en is daar in geslaagd.");
                    Kruising k;
                    if((k = juisteTrein.getKruising()) != null){
                        k.addOverstaptijd(overstaptijd);
                    }
                    moetUitstappen = true;                                    //Vanaf nu zit passagier op trein
                    vtijd = Klok.som(Klok.getTijd(), juisteTrein.getLijn().tijdTussenStations(huidigStation, reis.getOverstapData(aantalGenomenOverstappen).station, voorwaarde()));
                    
                    if(voorwaarde()){
                        //System.out.println("TOT HIER8!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        //System.out.println(huidigStation + " vs " + reis.getOverstapData(aantalGenomenOverstappen).station);
                        System.out.println("tijd tussen " + huidigStation + " en " + reis.getOverstapData(aantalGenomenOverstappen).station + " is " + juisteTrein.getLijn().tijdTussenStations(huidigStation, reis.getOverstapData(aantalGenomenOverstappen).station, false));
                        System.out.println(this + " heeft vtijd geupdate naar " + vtijd + " want hij/zij moet uitstappen aan " + reis.getOverstapData(aantalGenomenOverstappen).station + " op " + juisteTrein);
                        System.out.println(this + " is op " + juisteTrein + " gestapt.");
                    }
                    
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
    private boolean voorwaarde(){
        return (naam.equals("9879"));
    }
}
