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

// twee lijnen kunnen een gemeenschappelijk station hebben zodat overstappen van de ene lijn op de andere mogelijk is,dit mag een eindstation zijn
// deze klasse houdt relevante statistische data bij 
public class Kruising {
    // naam van een kruising is van de vorm "Lijn_n1xLijn_n2"
    String naam;
    private int overstaptijd;
    private Station[] stations;

    public Kruising (String naam ,Station [] stations){
        this.naam=naam;
        this.stations = stations;
    }
    
    public int getOverstaptijd() {
        return overstaptijd;
    }

    public Station[] getStations() {
        return stations;
    
    void voegDataToe() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
