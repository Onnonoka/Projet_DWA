/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.source.EndPoint;

/**
 *
 * @author fred2
 */
@javax.websocket.server.ServerEndpoint("/endpoint")
public class EndPoint {

    @javax.websocket.OnMessage
    public String onMessage(String message) {
        return null;
    }
    
}
