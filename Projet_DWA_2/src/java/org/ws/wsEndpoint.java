/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;

/**
 *
 * @author fred2
 */
@javax.websocket.server.ServerEndpoint("/endpoint")
public class wsEndpoint {
 
    @javax.websocket.OnMessage
    public String onMessage(String message) {
        return null;
    }

    @OnOpen
    public void onOpen() {
    }

    @OnError
    public void onError(Throwable t) {
    }

    @OnClose
    public void onClose() {
    }
   
}
