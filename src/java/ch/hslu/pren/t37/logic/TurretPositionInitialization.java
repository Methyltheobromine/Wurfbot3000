/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.pren.t37.logic;

import ch.hslu.pren.t37.pythoninterop.ASignalHandler;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Severin
 */
public class TurretPositionInitialization extends ASignalHandler {

    /**
     * 
     * @param scriptPath
     * @param scriptArguments 
     */
    public TurretPositionInitialization(final String scriptPath,final List<String> scriptArguments) {
        super();
        super.setPythonScriptPath(scriptPath);
        super.setScriptArguments(scriptArguments);
    }
    
    /**
     * Gets the End-Signal from the Initialization Process
     * @return the Stop Signal
     * @throws java.io.IOException
     */
    @Override
    public String evaluateScriptOutput() throws IOException {
        return getPythonHandler().getPythonOutput();
    }

}
