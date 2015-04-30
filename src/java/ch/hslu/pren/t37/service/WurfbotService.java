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
        CONF
    }
    
    /**
     * @OnOpen allows us to intercept the creation of a new session. The session
     * class allows us to send data to the user. In the method onOpen, we'll let
     * the user know that the handshake was successful.
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session.getId() + " has opened a connection");
        try {
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * When a user sends a message to the server, this method will intercept the
     * message and allow us to react to it. For now the message is read as a
     * String.
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message from " + session.getId() + ": " + message);
        try {
            session.getBasicRemote().sendText("Serverin du Spasti!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * The user closes the connection.
     *
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("Session " + session.getId() + " has ended");
    }

    private String handleMessage(String message) {
        String feedback = "";
        if (message.isEmpty()) {
            return "Invalid input!";
        }

        MsgType msgType = MsgType.valueOf(message);
        switch (msgType) {
            case CONF:
                break;
            case INITPOS:
                break;
            case START:
                startMainLogic();
                break;
            case STOP:
                break;
            default:
                feedback = "Invalid input";
                break;
        }

        return feedback;
    }
    
    private String startMainLogic() {
        String feedback="";
        try {
            logicController = new Logic();
            logicController.initialRun();
            System.out.println("-- FFFFIIIINNNNIIIIISSSSHHHHHHHHH --");
        } catch (IOException | InterruptedException ex) {            
            // ToDO: start failover logic
            Logger.getLogger(PrenStarter.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }     
        
        return feedback;
    }  
    
    private Logic getLogicController() throws IOException, InterruptedException{
        if(logicController==null){
            logicController=new Logic();
        }
        return logicController;
    }
}
