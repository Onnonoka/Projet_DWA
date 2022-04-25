/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ws;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
/*import org.dao.DAO_Joueur;
import org.donnees.Joueur;*/

/**
 *
 * @author fred2
 */
@ServerEndpoint("/ws421")
public class ws421 {

    @OnMessage
    public String onMessage(Session peer, String message) {
        return null;
    }

    @OnOpen
    public void onOpen(Session peer) {
        /*DAO_Joueur daoJoueur = new DAO_Joueur();
        Joueur j1 = daoJoueur.find("Onno");
        System.out.println(j1.getPseudo());*/
        System.out.println("New connexion");
    }

    @OnError
    public void onError(Throwable t) {
    }

    @OnClose
    public void onClose(Session peer) {
    }
    
    
    
}
