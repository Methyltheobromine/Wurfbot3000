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
     * @throws IOException if an IO-Read/Write exception occurs.
     */
    void runPythonScript() throws IOException;

    /**
     * Sets the Script Path.
     *
     * @param path to be set.
     */
    void setPythonScriptPath(String path);

    /**
     * Evaluates the Script output.
     *
     * @return Script Output
     * @throws IOException if an IO-Read/Write exception occurs.
     */
    String evaluateScriptOutput() throws IOException;

    /**
     * Stops the Python Process.
     *
     * @throws InterruptedException if the loading is interrupted.
     */
    void stopPythonProcess() throws InterruptedException;
}
