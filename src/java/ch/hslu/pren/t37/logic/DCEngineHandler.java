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
