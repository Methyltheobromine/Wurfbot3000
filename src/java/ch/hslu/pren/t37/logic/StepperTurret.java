package ch.hslu.pren.t37.logic;

import ch.hslu.pren.t37.pythoninterop.ASignalHandler;
import java.util.List;

/**
 * Script Handler for the Stepper-Turret.
 * @author Team 37
 */
public class StepperTurret extends ASignalHandler {

    /**
     * Constructor.
     * @param scriptPath
     * @param scriptArguments 
     */
    public StepperTurret(final String scriptPath,final List<String> scriptArguments) {
        super();
        super.setPythonScriptPath(scriptPath);
        super.setScriptArguments(scriptArguments);
    }

    @Override
    public String evaluateScriptOutput() {
        return "";
    }

}
