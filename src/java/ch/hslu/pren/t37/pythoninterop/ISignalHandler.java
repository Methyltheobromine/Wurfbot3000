package ch.hslu.pren.t37.pythoninterop;

import java.io.IOException;

/**
 * Interface for Signal Handlers.
 *
 * @author Team 37
 */
public interface ISignalHandler {

    /**
     * Runs the Python Script.
     *
     * @throws java.io.IOException
     */
    void runPythonScript() throws IOException;

    /**
     * Sets the Script Path.
     *
     * @param path
     */
    void setPythonScriptPath(String path);

    /**
     * Evaluates the Script output.
     *
     * @return Script Output
     */
    String evaluateScriptOutput() throws IOException;

    /**
     * Stops the Python Process.
     *
     * @throws java.lang.InterruptedException
     */
    void stopPythonProcess() throws InterruptedException;
}
