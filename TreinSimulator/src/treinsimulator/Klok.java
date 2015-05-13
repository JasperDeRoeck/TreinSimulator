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
    
    public Klok(int starttijd){
            tijd = starttijd;
            //onderstaande statement normaliseert de starttijd => 666 wordt 706;
            incrementeer(0);
    }
    
    public void incrementeer(int teller){
        tijd+=teller;
        int rest =tijd%100;
        if (rest>59){
            tijd/=100;
            tijd=(tijd*100)+(rest+40);
        } 
        if (tijd/100>23){
            tijd=tijd-2400;
        }
    }
    
    public static int getTijd(){
        return tijd;
    }
}
