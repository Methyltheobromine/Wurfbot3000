/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.pren.t37.service;

import ch.hslu.pren.t37.PrenStarter;
import ch.hslu.pren.t37.logic.Logic;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.MessageHandler;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @ServerEndpoint gives the relative name for the end point This will be
 * accessed via ws://localhost:8080/EchoChamber/echo Where "localhost" is the
 * address of the host, "EchoChamber" is the name of the package and "echo" is
 * the address to access this class from the server
 */
@ServerEndpoint("/wurfbot")
public class WurfbotService {

    private Logic logicController;

    enum MsgType {

        START,
        STOP,
        INITPOS,
        CONF,
        GETCONF
    }

    /**
     * @OnOpen allows us to intercept the creation of a new session. The session
     * class allows us to send data to the user. In the method onOpen, we'll let
     * the user know that the handshake was successful.
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        session.getBasicRemote().sendText("Start on Open");
        session.addMessageHandler(new MessageHandler.Whole<String>() {

            @Override
            public void onMessage(String message) {
                String msg = handleMessage(message);
                try {
                    session.getBasicRemote().sendText(msg);
                } catch (IOException ex) {

                    Logger.getLogger(WurfbotService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        session.getBasicRemote().sendText("Finsihed on Open");
//        try {
//            String feedback = getConfigProperties();
//            session.getBasicRemote().sendText("Connection aufgebaut");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
    }

    /**
     * When a user sends a message to the server, this method will intercept the
     * message and allow us to react to it. For now the message is read as a
     * String.
     */
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        String msg = handleMessage(message);
//        try {
//            session.getBasicRemote().sendText(msg);
//        } catch (IOException ex) {
//            Logger.getLogger(WurfbotService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    /**
     * The user closes the connection.
     *
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session) {
    }

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

    private Logic getLogicController() throws IOException, InterruptedException {
        if (logicController == null) {
            logicController = new Logic();
        }
        return logicController;
    }
}
