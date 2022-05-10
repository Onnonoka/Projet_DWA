/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

import AuthPopup from "./AuthPopup.js";
import Model from "./Model.js";
import { RequestBuilder } from "./RequestBuilder.js";

class Controler {
    model;                 // the model of the app
    vue;                    // the vue of the app
    
    constructor(model, vue) {
        this.model = model;
        this.vue = vue;

        this.vue.init();
        this.model.ws.onopen = this.wsOnOpen.bind(this);
        this.model.ws.onmessage = this.wsOnMessage.bind(this);
        this.model.ws.onclose = this.wsOnClose.bind(this);

        console.log(this.model.ws);
    }
    
    wsOnOpen(event) {
        console.log("connected");
    }

    wsOnMessage(event) {
        const reply = JSON.parse(event.data);
        console.log("Message", reply);
        switch(reply.code) {
            case RequestBuilder.AUTH_LOGIN :
                this.model.userData.username = reply.data.pseudo;
                this.model.userData.data = reply.data;
                this.model.loginStatus = Model.CONNECTED;
            case RequestBuilder.INFO_GET_PROFILE :
                this.vue.main.addProfileTab(reply.data);
                break;
            case RequestBuilder.AUTH_WRONG_CREDENTIALS :
                new AuthPopup(this.model, RequestBuilder.AUTH_LOGIN, RequestBuilder.AUTH_WRONG_CREDENTIALS);
                this.model.loginStatus = Model.DISCONECTED;
                break;
            case RequestBuilder.AUTH_USERNAME_ALREADY_USED :
                new AuthPopup(this.model, RequestBuilder.AUTH_REGISTER, RequestBuilder.AUTH_USERNAME_ALREADY_USED);
                break;
            case RequestBuilder.INFO_PLAYER_LIST : 
                this.model.userConnected = reply.data.usernames;
                break;
            case RequestBuilder.GAME_NEW_GAME :
                this.vue.main.addGameTab(reply.data.id, reply.data.player.map(e => e.username));
                break;
            case RequestBuilder.GAME_STARTING :
                this.vue.main.getGameTab(reply.data.id).setStatus(reply.data.player.map(e => e.status));
                break;
            case RequestBuilder.GAME_CANCEL :
                this.vue.main.removeGameTab(reply.data.id);
                break;
            case RequestBuilder.GAME_START :
                this.vue.main.getGameTab(reply.data.id).closePopup();
                this.vue.main.getGameTab(reply.data.id).gameStarted = true;
                break;
            case RequestBuilder.GAME_DATA :
                this.vue.main.getGameTab(reply.data.id).setGameData(reply.data);
                break;

        }
        this.vue.update();

    }

    wsOnClose(event) {
        console.log("disconected");
    }
}

export default Controler;