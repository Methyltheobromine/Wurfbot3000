package ch.hslu.pren.t37.logic;

import ch.hslu.pren.t37.pythoninterop.ASignalHandler;
import java.io.IOException;
import java.util.List;

/**
 * Script Handler for the Initialization of the Turret.
 *
 * @author Team 37
 */
public class TurretPositionInitialization extends ASignalHandler {

    /**
     * Constructor.
     *
     * @param scriptPath which specifies the script location.
     * @param scriptArguments list of arguments to be passed to the script.
     */
    public TurretPositionInitialization(final String scriptPath, final List<String> scriptArguments) {
        super();
        super.setPythonScriptPath(scriptPath);
        super.setScriptArguments(scriptArguments);
    }

    /**
     * Gets the End-Signal from the Initialization Process.
     *
     * @return the Stop Signal when the End-Switch is reached
     * @throws IOException if an IO-Read/Write exception occurs.
     */
    @Override
    public String evaluateScriptOutput() throws IOException {
        return getPythonHandler().getPythonOutput();
    }

}
