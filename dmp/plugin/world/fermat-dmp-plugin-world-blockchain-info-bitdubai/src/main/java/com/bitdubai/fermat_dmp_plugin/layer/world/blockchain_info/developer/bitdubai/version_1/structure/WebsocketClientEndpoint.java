package com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure;

/**
 * Created by natalia on 10/04/2015.
 * Reviewed by leon on 4/27/15
 */
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.drafts.Draft_17;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class WebsocketClientEndpoint extends WebSocketClient {

    public WebsocketClientEndpoint(String url, Map<String, String> headers) throws URISyntaxException {
        super(new URI(url), new Draft_17(), headers, 0);
    }

    @Override
    public void onError(Exception e){

    }

    @Override
    public void onMessage(String message){

    }

    @Override
    public void onOpen(ServerHandshake sh){

    }

    @Override
    public void onClose(int i, String s, boolean b){

    }

  /*  Session userSession = null;
    private MessageHandler messageHandler;

    public WebsocketClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
  /* @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket");
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
   /* @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
   /* @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    /**
     * register message handler
     *
     */
   /* public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message.
     *

     * @param message
     */
  /*  public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    /**
     * Message handler.
     *
     * @author Jiji_Sasidharan
     */
    public static interface MessageHandler {

        public void handleMessage(String message);
    }
}