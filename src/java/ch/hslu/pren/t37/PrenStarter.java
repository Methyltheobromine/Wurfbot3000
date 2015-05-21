package ch.hslu.pren.t37;

import ch.hslu.pren.t37.logic.Logic;

/**
 * Starter class used before the Service was written.
 *
 * @author Team 37
 */
public class PrenStarter {

    /**
     * Main method.
     *
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
