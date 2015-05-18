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

    private int vtijd;
    private int aantalReizigersPerTrein;
    private int aantalRechtstaandeReizigers;
    private int aantalAchterGeblevenReizigers;

    public Segmentdata(int vtijd, int aantalReizigersPerTrein, int aantalRechtstaandeReizigers, int aantalAchterGeblevenReizigers) {
        this.vtijd = vtijd;
        this.aantalAchterGeblevenReizigers = aantalAchterGeblevenReizigers;
        if (aantalRechtstaandeReizigers < 0) {
            this.aantalRechtstaandeReizigers = 0;
        } else {
            this.aantalRechtstaandeReizigers = aantalRechtstaandeReizigers;
        }
        this.aantalReizigersPerTrein = aantalReizigersPerTrein;
    }

    public int getAantalRechtstaandeReizigers() {
        return aantalRechtstaandeReizigers;
    }

    public int getVtijd() {
        return vtijd;
    }

    public int getAantalReizigersPerTrein() {
        return aantalReizigersPerTrein;
    }

    public int getAantalAchterGeblevenReizigers() {
        return aantalAchterGeblevenReizigers;
    }

    @Override
    public String toString() {
        return "Segmentdata{" + "vtijd=" + vtijd + ", aantalReizigersPerTrein=" + aantalReizigersPerTrein + ", aantalRechtstaandeReizigers=" + aantalRechtstaandeReizigers + ", aantalAchterGeblevenReizigers=" + aantalAchterGeblevenReizigers + '}';
    }

}
