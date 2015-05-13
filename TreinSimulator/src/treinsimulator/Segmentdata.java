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
// elke trein die een segment aflegt maakt een reeks segmentdata over aan het segment
public class Segmentdata {
    //het moment waarop de trein vertrekt uit het beginstation
    int vtijd;
    int aantalReizigersPerTrein;
    int aantalRechtstaandeReizigers;
    int aantalAchterGeblevenReizigers;
    
    public Segmentdata(/*int vtijd, int aantalReizigersPerTrein, int aantalRechtstaandeReizigers, int aantalAchterGeblevenReizigers*/){
//        this.vtijd = vtijd;
//        this.aantalAchterGeblevenReizigers= aantalAchterGeblevenReizigers;
//        this.aantalRechtstaandeReizigers = aantalRechtstaandeReizigers;
//        this.aantalReizigersPerTrein = aantalReizigersPerTrein;
    }

    public void setVtijd(int vtijd) {
        this.vtijd = vtijd;
    }

    public void setAantalReizigersPerTrein(int aantalReizigersPerTrein) {
        this.aantalReizigersPerTrein = aantalReizigersPerTrein;
    }

    public void setAantalRechtstaandeReizigers(int aantalRechtstaandeReizigers) {
        this.aantalRechtstaandeReizigers = aantalRechtstaandeReizigers;
    }

    public void setAantalAchterGeblevenReizigers(int aantalAchterGeblevenReizigers) {
        this.aantalAchterGeblevenReizigers = aantalAchterGeblevenReizigers;
    }
    

    public int getAantalRechtstaandeReizigers() {
        return aantalRechtstaandeReizigers;
    }
    
    
}