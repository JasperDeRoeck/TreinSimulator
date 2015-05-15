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
    Station huidigStation;
    Station volgendStation;
    boolean gestrand = false;
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

    public void uitstappen(int t) {
        if(vtijd == t){
           
            moetUitstappen = false;
            if (aantalGenomenOverstappen == reis.getAantalOverstappen()) {
                //We zijn er -> data toevoegen
                reis.addTijd(Klok.verschil(Klok.getTijd(), beginTijd));
                vtijd = -1;
                System.out.println(this + "stapt uit en heeft zijn eindhalte bereikt.");
            } else {
                System.out.println("Reiziger met reis " + reis + "stapt uit en gaat op zoek naar een andere trein.");
                //We zijn er nog niet -> nieuwe trein zoeken
                juisteTrein = zoekTrein();
                vtijd = juisteTrein.getVtijd();
            }
        }
        
    }

    public Trein zoekTrein() {
        System.out.println(this + " zoekt een trein");
        Overstapdata data = huidigStation.juisteTrein(reis.getAantalOverstappen(), reis.getEindstation());
        volgendStation = data.getOverstap();
        System.out.println(volgendStation + " is volgend station");
        return data.getTrein();
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
                    juisteTrein = zoekTrein();
                    vtijd = juisteTrein.getLijn().geefEersteTreinUur(Klok.getTijd(), huidigStation);
                    System.out.println(this + " heeft vtijd geupdate naar " + vtijd + " want dan komt " + juisteTrein + " toe in " + huidigStation);
                    
            } else {
                System.out.println(this + " wil opstappen op de trein ");
                if (!juisteTrein.opstappen(this)) {
                    System.out.println("maar er was niet genoeg plaats");
                    //Als trein vol is, nieuwe trein zoeken, vtijd instellen op volgende vertrekuur
                    juisteTrein = zoekTrein();
                    vtijd = juisteTrein.getVtijd();
                } else {
                    System.out.println("en is daar in geslaagd.");
                    //juisteTrein.getKruising().addOverstaptijd(overstaptijd);  //Passagier geeft door aan kruising dat het niet meer op kruising bevindt.
                    moetUitstappen = true;                                    //Vanaf nu zit passagier op trein
                    
                    System.out.println(this + " wil zijn vtijd updaten want hij/zij moet uitstappen aan " + volgendStation);
                    vtijd += juisteTrein.getLijn().tijdTussenStations(huidigStation, volgendStation);
                    
                    System.out.println(this + " heeft vtijd geupdate naar " + vtijd + " want hij/zij moet uitstappen aan " + volgendStation);
                    huidigStation = volgendStation;
                    System.out.println(this + " is op de trein gestapt.");
                    volgendStation = null;
                }
            }
            return true;
        }
        else{
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
}
