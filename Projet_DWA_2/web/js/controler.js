/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

class controler {
    model;                 // the model of the app
    vue;                    // the vue of the app
    
    wsUri;                 // the uri of the websocket server
    ws;                     // the websocket connexion to the server
    
    constructor(model, vue) {
        this.model = model;
        this.vue = vue;
        
        this.wsUri = "ws://localhost:8080/Projet_DWA_2/ws421";
        this.ws = new WebSocket(this.wsUri);
        this.ws.onopen = this.wsOnOpen;
        console.log(this.ws);
    }
    
    wsOnOpen(event) {
        console.log("connected");
    }
}

export default controler;
