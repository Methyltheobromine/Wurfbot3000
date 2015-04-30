/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.pren.t37;

import ch.hslu.pren.t37.logic.Logic;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrenStarter {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        try {
            Logic controllerLogic = new Logic();
            controllerLogic.initialRun();
            System.out.println("-- FFFFIIIINNNNIIIIISSSSHHHHHHHHH --");
        } catch (IOException | InterruptedException ex) {
            // ToDO: start failover logic
            Logger.getLogger(PrenStarter.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
}
