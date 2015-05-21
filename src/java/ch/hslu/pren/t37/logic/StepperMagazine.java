package ch.hslu.pren.t37.logic;

import ch.hslu.pren.t37.pythoninterop.ASignalHandler;
import java.io.IOException;
import java.util.List;

/**
 * Script Handler for the Ball-Magazine.
 * 
 * @author Team 37
 */
public class StepperMagazine extends ASignalHandler {

    /**
     * Constructor.
     * 
     * @param scriptPath which specifies the script location.
     * @param scriptArguments list of arguments to be passed to the script.
     */
    public StepperMagazine(final String scriptPath, final List<String> scriptArguments) {
        super();
        super.setPythonScriptPath(scriptPath);
        super.setScriptArguments(scriptArguments);
    }

    /**
     * {@inheritDoc}.
     * Gets the End-Signal from the initialisation process.
     * 
     * @return the Stop Signal
     * @throws IOException if an IO-Read/Write exception occurs.
     */
    @Override
    public String evaluateScriptOutput() throws IOException {
        return getPythonHandler().getPythonOutput();
    }

}
