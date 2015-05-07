/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.pren.t37;

import ch.hslu.pren.t37.logic.Logic;

public class PrenStarter {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("-- Start --");

        Logic controllerLogic = new Logic();
        controllerLogic.wurfbot3000Start();

        System.out.println("-- Finish --");   
    }
}
