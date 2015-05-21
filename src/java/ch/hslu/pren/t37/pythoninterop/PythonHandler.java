package ch.hslu.pren.t37.pythoninterop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates the Python Process and handles the output.
 *
 * @author Team 37
 */
public final class PythonHandler {

    private Process _pythonProcess;

    /**
     * Default Constructor.
     */
    public PythonHandler() {
    }

    /**
     * Overloaded Constructor starts the Scripts.
     *
     * @param scriptPath which specifies the script location.
     * @param scriptArguments list of arguments to be passed to the script.
     * @throws IOException if an IO-Read/Write exception occurs.
     */
    public PythonHandler(final String scriptPath, final List<String> scriptArguments) throws IOException {
        startPythonScript(scriptPath, scriptArguments);
    }

    /**
     * Starts the Python Script.
     *
     * @param scriptName which specifies the script location.
     * @param scriptArguments list of arguments to be passed to the script.
     * @throws IOException if an IO-Read/Write exception occurs.
     */
    public void startPythonScript(final String scriptName, final List<String> scriptArguments) throws IOException {
        final ArrayList<String> pythonCommand = new ArrayList<>();
        pythonCommand.add("python");
        pythonCommand.add(scriptName);

        for (String arg : scriptArguments) {
            pythonCommand.add(arg);
        }
        final ProcessBuilder processBuilder = new ProcessBuilder(pythonCommand);
        _pythonProcess = processBuilder.start();
    }

    /**
     * Gets the Output produced by the Python Script.
     *
     * @return Script Output
     * @throws IOException if an IO-Read/Write exception occurs.
     */
    public String getPythonOutput() throws IOException {
        if (!isScriptInitialized()) {
            throw new NullPointerException("Script must be initialized!");
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(_pythonProcess.getInputStream()));
        String pythonOutput = in.readLine();
        return pythonOutput;
    }

    /**
     * Stops the Python Process.
     *
     * @throws InterruptedException if the loading is interrupted.
     */
    public void stopPythonProcess() throws InterruptedException {
        if (isScriptInitialized()) {
            //  _pythonProcess.destroy();
            _pythonProcess.waitFor();
        }
    }

    /**
     * Checks if the Python Process is initialised.
     *
     * @return true if script is initialised
     */
    private boolean isScriptInitialized() {
        return _pythonProcess != null;
    }

}
