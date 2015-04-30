package ch.hslu.pren.t37.logic;

import ch.hslu.pren.t37.pythoninterop.ASignalHandler;
import java.io.IOException;
import java.util.List;

/**
 * Script Handler for the Ball-Magazine.
 * @author Team 37
 */
public class StepperMagazine extends ASignalHandler {

    /**
     * Constructor.
     * @param scriptPath
     * @param scriptArguments
     */
    public StepperMagazine(final String scriptPath,final List<String> scriptArguments) {
        super();
        super.setPythonScriptPath(scriptPath);
        super.setScriptArguments(scriptArguments);
    }

    @Override
    public String evaluateScriptOutput() throws IOException {
        return getPythonHandler().getPythonOutput();
    }

}
