package ch.hslu.pren.t37.camera;

import ch.hslu.pren.t37.pythoninterop.ASignalHandler;
import java.util.ArrayList;

/**
 * Script Handler for the Camera.
 * 
 * @author Team 37
 */
public class CameraPictureHandler extends ASignalHandler {

    /**
     * Constructor.
     * 
     * @param scriptPath which specifies the script location.
     * @param scriptArguments list of arguments to be passed to the script.
     */
    public CameraPictureHandler(String scriptPath, ArrayList<String> scriptArguments) {
        super();
        super.setPythonScriptPath(scriptPath);
        super.setScriptArguments(scriptArguments);
    }

    /**
     * {@inheritDoc}.
     * @return an Empty String => this Handler has no Output.
     */
    @Override
    public String evaluateScriptOutput() {
        return "";
    }

}
