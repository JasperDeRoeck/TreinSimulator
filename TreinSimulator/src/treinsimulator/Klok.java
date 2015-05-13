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
public class Klok {
    
    private static int simulatietijd =400;
    
    public static int incrementeer(int tijd ,int teller){
        tijd+=teller;
        int rest =tijd%100;
        if (rest>59){
            tijd/=100;
            tijd=(tijd*100)+(rest+40);
        } 
        if (tijd/100>23){
            tijd=tijd-2400;
        }
        return tijd;
    }

    public static int getSimulatietijd() {
        return simulatietijd;
    }

    public static void setSimulatietijd(int simulatietijd) {
        simulatietijd = simulatietijd;
    }
    
    
}
