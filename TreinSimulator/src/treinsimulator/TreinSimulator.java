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
public class TreinSimulator {

    // to do : readlists updaten
    public static void main(String[] args) {
        DAO.readLists();
        //DAO.schrijfStations();
        DAO.schrijfLijnen();
    }
    
}
