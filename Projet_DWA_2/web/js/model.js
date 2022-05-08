/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

class Model {

    static DISCONECTED = 0;
    static CONNECTED = 1;
    static CONNECTING = 2;

    loginStatus;
    userConnected;

    userData;           // The data of the user
    wsUri;
    ws;                 // the websocket connexion to the server
    
    constructor() {
        this.userData = {
            isLogged: false,
            username: "",
            data: {}
        }

        this.loginStatus = Model.DISCONECTED;
        this.userConnected = [];
        
        this.wsUri = "ws://localhost:8080/Projet_DWA_2/ws421";
        this.ws = new WebSocket(this.wsUri);
        this.ws.onopen = () => {console.log("Connected")};
    }
}

export default Model;