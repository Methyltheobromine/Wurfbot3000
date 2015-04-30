/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.pren.t37.camera;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
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

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

}
