package ch.hslu.pren.t37.service;

import ch.hslu.pren.t37.PrenStarter;
import ch.hslu.pren.t37.logic.Logic;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.MessageHandler;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * This class contains the logic and session handling for all incoming
 * connections. The {@link ServerEndpoint} is specified to /wurfbot and can be
 * accessed via ws://192.168.37.1:8080/WurfbotService/wurfbot Where
 * "192.168.37.1" is the address of the host, "WurfbotService" is the name of
 * the package and "wurfbot" is the address to access this class from the server
 * 
* @author Team 37
 */
@ServerEndpoint("/wurfbot")
public class WurfbotService {

    private Logic logicController;

    /**
     * Enumeration of all allowed Message-Types.
     */
    enum MsgType {

        START,
        STOP,
        INITPOS,
        CONF,
        GETCONF
    }

    /**
     * {@inheritDoc}
     *
     * OnOpen allows us to intercept the creation of a new session. Which allows
     * us to send data back to the user by attaching a MessageHandler to the
     * session. In the method onOpen, we'll let the user know that the handshake
     * was successful.
     *
     * @param session {@link Session}
     * @throws IOException if an IO-Read/Write exception occurs.
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        session.getBasicRemote().sendText("Start on Open");
        session.addMessageHandler((MessageHandler.Whole<String>) (String message) -> {
            String msg = handleMessage(message);
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException ex) {
                Logger.getLogger(WurfbotService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } /**
         * When a user sends a message to the server, this method will intercept
         * the message and allow us to react to it. For now the message is read
         * as a String.
         */
        );
        session.getBasicRemote().sendText("Finsihed on Open");
    }

    /**
     * This method will be called when the onClose event fires. This happens
     * when the user closes the connection.
     *
     * Note: you can't send messages to the client from this method
     *
     * @param session {@link Session}.
     */
    @OnClose
    public void onClose(Session session) {
    }

    /**
     * Handles all the Messages which are received by the onMessage-Method.
     *
     * @param message the String which was send by the user.
     * @return the Response or Error-Message to be displayed for the User.
     */
    private String handleMessage(String message) {
        String feedback = "";
        if (message.isEmpty()) {
            return "Invalid Input!";
        }

        String[] splittedMessage = message.split(":");
        MsgType msgType = MsgType.valueOf(splittedMessage[0]);
        switch (msgType) {
            case CONF:
                PropertyConfigurationHandler propertyConfigurationHandler = new PropertyConfigurationHandler();
                feedback = propertyConfigurationHandler.setValue(splittedMessage[1]);
                break;
            case INITPOS:
                feedback = startInitalization();
                break;
            case START:
                feedback = startMainLogic();
                break;
            case STOP:
                break;
            case GETCONF:
                feedback = getConfigProperties();
                break;
            default:
                feedback = "Invalid Input";
                break;
        }
        return feedback;
    }

    /**
     * Method to start the Main-Logic.
     *
     * @return the Response or Error-Message to be displayed for the User.
     */
    private String startMainLogic() {
        String feedback = "ERROR";
        try {
            logicController = getLogicController();
            logicController.wurfbot3000Start();
            feedback = "Autonomer Ablauf erfolgreich<br/><b>Ergo Finish!!</b>";
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(PrenStarter.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return feedback;
    }

    /**
     * Method to call the Initialization-Process.
     *
     * @return the Response or Error-Message to be displayed for the User.
     */
    private String startInitalization() {
        String feedback = "ERROR";
        try {
            logicController = getLogicController();
            logicController.startInitalization();
            feedback = "Initialisierung erfolgreich";
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(PrenStarter.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return feedback;
    }

    /**
     * Reads all properties from the configuration file.
     *
     * @return the Response or Error-Message to be displayed for the User.
     */
    private String getConfigProperties() {
        String feedback = "ERROR";
        try {
            logicController = getLogicController();
            feedback = logicController.loadVariableContent();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(PrenStarter.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        return feedback;
    }

    /**
     * Gets the Logic-Controller.
     *
     * @return an instance of {@link Logic}.
     * @throws IOException if an IO-Read/Write exception occurs.
     * @throws InterruptedException if the loading is interrupted.
     */
    private Logic getLogicController() throws IOException, InterruptedException {
        if (logicController == null) {
            logicController = new Logic();
        }
        return logicController;
    }
}
