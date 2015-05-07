package ch.hslu.pren.t37.camera;

import ch.hslu.pren.t37.pythoninterop.ASignalHandler;
import java.util.ArrayList;

/**
 *
 * @author Severin
 */
public class BildVonWebcamAufnehmen extends ASignalHandler {

    public BildVonWebcamAufnehmen(String scriptPath, ArrayList<String> scriptArguments) {
        super.setPythonScriptPath(scriptPath);
        super.setScriptArguments(scriptArguments);
    }

    @Override
    public String evaluateScriptOutput() {
        return "";
    }

}
