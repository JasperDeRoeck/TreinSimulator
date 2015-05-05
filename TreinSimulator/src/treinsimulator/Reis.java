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
//combinatie van vertrek- en eindstation
//houdt statistisch belangrijke data bij 
public class Reis {
    int aantalReizigers;
    int aantalGestrandeReizigers;
    // totaal aantal minuten reisweg voor alle reizigers samen
    int totaleReiswegTijd;
    String naamVertrekStation;
    String naamDoelStation;
    
    public String getNaamVertrekStation() {
        return naamVertrekStation;
    }

    public String getNaamDoelStation() {
        return naamDoelStation;
    }

    public int getAantalGestrandeReizigers() {
        return aantalGestrandeReizigers;
    }

    public int getAantalReizigers() {
        return aantalReizigers;
    }
    
    public int berekenWachttijdInReis(String vertrek, String doel){ //nog niet af
        return 0;
    }
}
