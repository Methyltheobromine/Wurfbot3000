/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.pren.t37.logic;

import ch.hslu.pren.t37.pythoninterop.ASignalHandler;
import java.util.List;

/**
 * Script Handler for the DC-Engine.
 * @author Team 37
 */
public class DCEngineHandler extends ASignalHandler {

    /**
     * Constructor.
     * @param scriptPath
     * @param scriptArguments 
     */
    public DCEngineHandler(final String scriptPath,final List<String> scriptArguments) {
        super();
        super.setPythonScriptPath(scriptPath);
        super.setScriptArguments(scriptArguments);
    }

    @Override
    public String evaluateScriptOutput() {
        return "";
    }

}
