package ch.hslu.pren.t37.logic;

import ch.hslu.pren.t37.pythoninterop.ASignalHandler;
import java.util.List;

/**
 * Script Handler for the Stepper-Turret.
 *
 * @author Team 37
 */
public class StepperTurret extends ASignalHandler {

    /**
     * Constructor.
     *
     * @param scriptPath which specifies the script location.
     * @param scriptArguments list of arguments to be passed to the script.
     */
    public StepperTurret(final String scriptPath, final List<String> scriptArguments) {
        super();
        super.setPythonScriptPath(scriptPath);
        super.setScriptArguments(scriptArguments);
    }

    /**
     * {@inheritDoc}.
     *
     * @return an Empty String => this Handler has no Output.
     */
    @Override
    public String evaluateScriptOutput() {
        return "";
    }

}
