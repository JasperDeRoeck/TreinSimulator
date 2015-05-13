/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treinsimulator;

import javax.naming.OperationNotSupportedException;

/**
 *
 * @author Bernard
 */
public class Klok {
    
    private static int tijd;

    static void setTijd(int i) {
        tijd = i;
    }
    public static int getTijd(){
        return tijd;
    }
    public static void ticktock(){
        tijd = incrementeer(tijd, 1);
    }
    public static int incrementeer(int tijd, int teller) {
        int huidig  = valideer(tijd);
        int totaal = (huidig % 100) + teller + (huidig / 100) * 60;
        int som = 0;
        while (totaal > 59) {
            som += 100;
            totaal -= 60;
        }
        som += totaal;
        if (som % 100 > 59) {
            som += 40;
        }
        if (som > 2400) {
            som -= 2400;
        }
        return som;
    }
 
    public static int decrementeer(int tijd, int teller) {
        int huidig = valideer(tijd);
        int totaal = huidig/100;
        totaal = totaal*100;
        totaal = totaal-teller+huidig%100;
        System.out.println(totaal);
        int som = 0;
        if (totaal < 0) {
            som = 2300;
            while (totaal < -59) {
                som -= 100;
                totaal += 60;
            }
            if (totaal < 0) {
                System.out.println(totaal);
                return (som + (60 + totaal));
            } else {
                return (som + (60 - totaal));
            }
        }
        while (totaal > 59) {
            som += 100;
            totaal -= 60;
        }
        som += totaal;
        if (som % 100 > 59) {
            som += 40;
        }
 
        return som;
    }
   
    private static int valideer(int tijd){
        String a = tijd+"";
        while (a.startsWith("0")){
            a=a.substring(0);
            System.out.println(a);
        }
        return Integer.parseInt(a);
    }
}
